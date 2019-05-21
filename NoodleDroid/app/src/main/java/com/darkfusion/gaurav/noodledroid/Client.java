package com.darkfusion.gaurav.noodledroid;

import com.darkfusion.gaurav.noodledroid.utils.Token;
import com.darkfusion.gaurav.noodledroid.utils.TokenType;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
class Client {

    private BufferedWriter serverOutput;

    Client(String host, int port) throws IOException {
        Socket socket;
        socket = new Socket(host, port);
        registerSocketOutput(socket);
    }

    void performHandshake(int deviceWidth, int deviceHeight) {
        try {
            serverOutput.flush();
            serverOutput.write("OK");
            serverOutput.flush();
//            serverOutput.write("RES:" + deviceWidth + Token.DELIMITER + deviceHeight);
//            serverOutput.flush();
        } catch (Exception e) {
            System.err.println("EXCEPTION WHILE PERFORMING HANDSHAKE");
            e.printStackTrace();
        }
    }

    void sendMessage(String message) {
        try {
            //System.out.println("WILL SEND COORDS");
            this.serverOutput.flush();
            this.serverOutput.write(message);
            this.serverOutput.flush();
        } catch (IOException ioe) {
            System.out.println("Error Sending message to server!");
        }
    }

    private void registerSocketOutput(Socket socket) throws IOException {
        this.serverOutput = new BufferedWriter(new OutputStreamWriter(
                socket.getOutputStream()));
    }

    void sendEOF() {
        try {
            this.serverOutput.flush();
            this.serverOutput.write(Token.getTokenValue(TokenType.TYPE_EOF));
            this.serverOutput.flush();
        } catch (IOException ioe) {
            System.out.println("Error Sending message to server!");
        }
    }

    void heartbeat(){
        try{
            this.serverOutput.flush();
            this.serverOutput.write("HB");
            this.serverOutput.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

