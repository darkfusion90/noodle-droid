package com.darkfusion.gaurav.noodledroid.httpserver;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
    private ServerSocket serverSocket;
    private int port;
    private String htmlContent;

    public HttpServer() {
        try {
            serverSocket = new ServerSocket(0);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        port = serverSocket.getLocalPort();
    }

    public int getPort() {
        return this.port;
    }

    public void setHtmlContent(String htmlContent) {
        this.htmlContent = htmlContent;
    }

    public void acceptConnection() {
        Socket socket;
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            return;
        }

        System.out.println("CONTENT: \n" + htmlContent);

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
