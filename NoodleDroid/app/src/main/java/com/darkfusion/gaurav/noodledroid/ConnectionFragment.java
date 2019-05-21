package com.darkfusion.gaurav.noodledroid;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.darkfusion.gaurav.noodledroid.utils.SingleToast;

import java.io.IOException;
import java.lang.ref.WeakReference;

public class ConnectionFragment extends Fragment {
    static final String TOAST_INVALID_IP_OR_PORT = "Invalid Port or IP Address";
    //icon credit <div>Icons made by <a href="https://www.flaticon.com/authors/photo3idea-studio" title="photo3idea_studio">photo3idea_studio</a> from <a href="https://www.flaticon.com/" 			    title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" 			    title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>

    private ConnectionViewModel mViewModel;

    public static ConnectionFragment newInstance() {
        return new ConnectionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.connection_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ConnectionViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        Button connectButton = view.findViewById(R.id.connectButton);
        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connectToServer(view);
            }
        });
        super.onViewCreated(view, savedInstanceState);
    }

    void setIPAddress(@NonNull final View view) {
        EditText ipAddressText = view.findViewById(R.id.ipAddressEditText);
        MainActivity.setIpAddress(ipAddressText.getText().toString());
    }

    void setPort(@NonNull final View view) {
        EditText portText = view.findViewById(R.id.portEditText);
        String portString = portText.getText().toString();

        int port;
        try {
            port = Integer.parseInt(portString);
        } catch (NumberFormatException nfe) {
            port = MainActivity.INVALID_PORT;
        }

        MainActivity.setPort(port);
    }

    void connectToServer(@NonNull final View view) {
        setIPAddress(view);
        setPort(view);

        if (invalidIPAddress() || invalidPort()) {
            SingleToast.show(getContext(), TOAST_INVALID_IP_OR_PORT, Toast.LENGTH_LONG);
        } else {
            new NewConnectionAsyncTask(getContext()).execute();
        }
    }

    private static boolean invalidPort() {
        return MainActivity.port == MainActivity.INVALID_PORT;
    }

    private static boolean invalidIPAddress() {
        return MainActivity.ipAddress.equals(MainActivity.INVALID_IP_ADDRESS);
    }

    static class NewConnectionAsyncTask extends AsyncTask<Void, Void, Boolean> {
        WeakReference<Context> contextWeakReference;

        NewConnectionAsyncTask(Context context) {
            this.contextWeakReference = new WeakReference<>(context);
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                MainActivity.communicationHandler = new CommunicationHandler();
            } catch (IOException ioe) {
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean connectionSuccess) {
            if (connectionSuccess) {
                showConnectionSuccessToast();
            } else {
                showConnectionFailedToast();
            }

            super.onPostExecute(connectionSuccess);
        }

        private void showConnectionFailedToast() {
            Context context = contextWeakReference.get().getApplicationContext();
            SingleToast.show(context,
                    context.getResources().getString(R.string.connectionFailed),
                    Toast.LENGTH_LONG);
        }

        private void showConnectionSuccessToast() {
            Context context = contextWeakReference.get().getApplicationContext();
            SingleToast.show(context,
                    context.getResources().getString(R.string.connectionSuccessful),
                    Toast.LENGTH_LONG);
        }

    }
}
