package com.darkfusion.gaurav.noodledroid.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.darkfusion.gaurav.noodledroid.MainActivity;
import com.darkfusion.gaurav.noodledroid.TouchpadFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Tokenizer {
    private List<Token> tokens;
    private static final DecimalFormat decimalFormat = new DecimalFormat("000");

    public Tokenizer() {
        this.tokens = new ArrayList<>();
    }

    private String formatDecimal(int value) {
        String result = decimalFormat.format(value);
        if (result.startsWith("-")) {
            result = result.replaceFirst("0", "");
        }
        return result;
    }

    private void addMouseActionToken(TokenType mouseActionType) {
        this.tokens.add(Token.getToken(mouseActionType));
        this.tokens.add(Token.getToken(TokenType.TYPE_DELIMITER));
    }

    private void addCoordinates(String x, String y) {
        this.tokens.add(Token.getCoordinateToken(x));
        this.tokens.add(Token.getToken(TokenType.TYPE_DELIMITER));
        this.tokens.add(Token.getCoordinateToken(y));
    }

    public void tokenizeMouseEventMessage(Coordinate coordinate, TokenType mouseActionType) {
        if (coordinate == null) {
            coordinate = new Coordinate(0, 0);
        }

        this.tokens.clear();
        this.tokens.add(Token.getToken(DeviceType.MOUSE));
        addMouseActionToken(mouseActionType);

        String x = formatDecimal(coordinate.x);
        String y = formatDecimal(coordinate.y);
        addCoordinates(x, y);

        Log.d("DARKFUSION", "MESSAGE: " + this.toString());
    }

    public void tokenizeKeyboardEventMessage(String... keys) {
        tokens.clear();
        for (String c : keys) {
            tokens.add(new Token(c));
            tokens.add(new Token(TokenType.TYPE_DELIMITER));
        }

        int messageLength = this.toString().length();
        String formattedMessageLength = new DecimalFormat("00").format(messageLength);

        tokens.add(0, Token.getToken(DeviceType.KEYBOARD));
        tokens.add(1, new Token(formattedMessageLength));

        Log.d(MainActivity.LOG_TAG, toString());
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Token token : this.tokens) {
            builder.append(token.value);
        }
        return builder.toString();
    }
}
