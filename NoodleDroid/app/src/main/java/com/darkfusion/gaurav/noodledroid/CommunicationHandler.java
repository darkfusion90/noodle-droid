package com.darkfusion.gaurav.noodledroid;

import com.darkfusion.gaurav.noodledroid.utils.Coordinate;
import com.darkfusion.gaurav.noodledroid.utils.TokenType;
import com.darkfusion.gaurav.noodledroid.utils.Tokenizer;

import java.io.IOException;

class CommunicationHandler {
    private static Tokenizer tokenizer = new Tokenizer();

    private Client client;

    CommunicationHandler(int i){
        client = null;
    }

    CommunicationHandler() throws IOException {
        this.client = new Client(MainActivity.ipAddress, MainActivity.port);
        //this.client.performHandshake(MainActivity.SCREEN_WIDTH, MainActivity.SCREEN_HEIGHT);
    }

    void sendMouseMove(Coordinate coordinate) {
        tokenizer.tokenizeMouseEventMessage(coordinate, TokenType.TYPE_MOUSE_MOVE);
        //this.client.sendMessage(tokenizer.toString());
    }

    void sendMouseClick() {
        tokenizer.tokenizeMouseEventMessage(null, TokenType.TYPE_MOUSE_LEFT_BUTTON_CLICK);
       // this.client.sendMessage(tokenizer.toString());
    }

    void sendLeftButtonDown() {
        tokenizer.tokenizeMouseEventMessage(null, TokenType.TYPE_MOUSE_LEFT_BUTTON_DOWN);
        //this.client.sendMessage(tokenizer.toString());
    }

    void sendLeftButtonUp() {
        tokenizer.tokenizeMouseEventMessage(null, TokenType.TYPE_MOUSE_LEFT_BUTTON_UP);
        //this.client.sendMessage(tokenizer.toString());
    }

    void sendRightButtonDown() {
        tokenizer.tokenizeMouseEventMessage(null, TokenType.TYPE_MOUSE_RIGHT_BUTTON_DOWN);
        this.client.sendMessage(tokenizer.toString());
    }

    void sendRightButtonUp() {
        tokenizer.tokenizeMouseEventMessage(null, TokenType.TYPE_MOUSE_RIGHT_BUTTON_UP);
        this.client.sendMessage(tokenizer.toString());
    }

    void sendKeyStroke(String... keys) {
        tokenizer.tokenizeKeyboardEventMessage(keys);
        this.client.sendMessage(tokenizer.toString());
    }

    void sendEOF() {
        this.client.sendEOF();
    }

    void sendHeartbeat(){
        this.client.heartbeat();
    }
}
