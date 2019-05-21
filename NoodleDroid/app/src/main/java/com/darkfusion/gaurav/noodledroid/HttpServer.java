package com.darkfusion.gaurav.noodledroid;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class HttpServer {
    private ServerSocket serverSocket;
    private int port;

    HttpServer() {
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        port = serverSocket.getLocalPort();
    }

    int getPort() {
        return this.port;
    }

    void acceptConnection() {
        Socket socket;
        try {
            socket = serverSocket.accept();
            System.out.println("CONNECTED: " + socket);
        } catch (IOException e) {
            System.out.println("IO EXCEPTION");
            return;
        }


        String htmlContent = //#region
                "<html>\n" +
                        "<head><title>Index of /serial/FRIENDS/S3/</title></head>\n" +
                        "<body>\n" +
                        "<h1>Index of /serial/FRIENDS/S3/</h1><hr><pre><a href=\"../\">../</a>\n" +
                        "<a href=\"Friends_S03E01_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E01_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:19           295435125\n" +
                        "<a href=\"Friends_S03E02_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E02_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:20           296486413\n" +
                        "<a href=\"Friends_S03E03_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E03_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:23           296581328\n" +
                        "<a href=\"Friends_S03E04_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E04_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:23           297016156\n" +
                        "<a href=\"Friends_S03E05_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E05_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:24           296559377\n" +
                        "<a href=\"Friends_S03E06_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E06_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:24           296309170\n" +
                        "<a href=\"Friends_S03E07_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E07_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:25           296543796\n" +
                        "<a href=\"Friends_S03E08_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E08_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:26           296716169\n" +
                        "<a href=\"Friends_S03E09_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E09_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:26           296613219\n" +
                        "<a href=\"Friends_S03E10_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E10_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:27           296785277\n" +
                        "<a href=\"Friends_S03E11_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E11_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:27           296620173\n" +
                        "<a href=\"Friends_S03E12_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E12_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:27           296801573\n" +
                        "<a href=\"Friends_S03E13_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E13_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:30           296772463\n" +
                        "<a href=\"Friends_S03E14_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E14_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:31           296583665\n" +
                        "<a href=\"Friends_S03E15_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E15_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:32           295445146\n" +
                        "<a href=\"Friends_S03E16_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E16_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:31           300456044\n" +
                        "<a href=\"Friends_S03E17_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E17_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:35           296582174\n" +
                        "<a href=\"Friends_S03E18_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E18_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:34           296187001\n" +
                        "<a href=\"Friends_S03E19_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E19_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:33           295957016\n" +
                        "<a href=\"Friends_S03E20_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E20_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 25-Mar-2017 19:30           296730086\n" +
                        "<a href=\"Friends_S03E21_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E21_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:34           296553176\n" +
                        "<a href=\"Friends_S03E22_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E22_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:40           295870466\n" +
                        "<a href=\"Friends_S03E23_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E23_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:39           296621195\n" +
                        "<a href=\"Friends_S03E24_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E24_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:39           296393832\n" +
                        "<a href=\"Friends_S03E25_1080p_BluRay_DeeJayAhmed_SITEMOVIE.mkv\">Friends_S03E25_1080p_BluRay_DeeJayAhmed_SITEMOV..&gt;</a> 23-Mar-2017 01:37           295669051\n" +
                        "</pre><hr></body>\n" +
                        "</html>\n";
        //#endregion
        int contentLength = htmlContent.length();
        String response = "HTTP/1.1 200 OK\n" +
                "Content-type: text/html;charset:UTF-8\n" +
                "Content-length: " + contentLength + "\n\n" +
                htmlContent;

        DataOutputStream outputStream;
        try {
            outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("Got output stream");
        } catch (IOException e) {
            return;
        }

        try {
            outputStream.writeBytes(response);
            System.out.println("Wrote stuffs");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
