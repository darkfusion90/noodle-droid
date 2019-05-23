package com.darkfusion.gaurav.noodledroid;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.darkfusion.gaurav.noodledroid.httpserver.HttpServer;
import com.darkfusion.gaurav.noodledroid.utils.InetAddressUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;

public class HomeFragment extends Fragment {

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        new LOL(getContext()).execute();
        super.onViewCreated(view, savedInstanceState);
    }

    static class LOL extends AsyncTask<Void, Void, Void> {
        WeakReference<Context> contextWeakReference;

        LOL(Context context) {
            this.contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            setupServer();
            return null;
        }

        private void setupServer() {
            String httpSource = getHttpSource();
            HttpServer server = new HttpServer();
            server.setHtmlContent(httpSource);

            System.out.println("Address: " + InetAddressUtil.getInetIPAddress() + "\tPort: " + server.getPort());
            server.acceptConnection();
        }

        private String getHttpSource() {
            InputStreamReader reader = getSourceReader();
            return readHttpSource(reader);
        }

        private InputStreamReader getSourceReader() {
            InputStream stream;
            try {
                stream = this.contextWeakReference.get().getResources().getAssets().open("index.html");
                return new InputStreamReader(stream);
            } catch (IOException e) {
                System.out.println("IOEXCEPTION GETSOURCEREADER");
                e.printStackTrace();
                return null;
            }
        }

        private String readHttpSource(InputStreamReader reader) {
            if (reader == null) {
                return "";
            }

            StringBuilder source = new StringBuilder();
            while (true) {
                int c = readIntFromFile(reader);
                if(isEOF(c)){
                    break;
                }
                source.append((char)c);
            }
            return source.toString();
        }

        private int readIntFromFile(InputStreamReader reader) {
            int c;
            try {
                c = reader.read();
            } catch (IOException e) {
                System.out.println("IOEXCEPTION READINTFROMFILE");
                c = -1;
            }
            return c;
        }

        private boolean isEOF(int intReadFromFile){
            return intReadFromFile == -1;
        }
    }
}

