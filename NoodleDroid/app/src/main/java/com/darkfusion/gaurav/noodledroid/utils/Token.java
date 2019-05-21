package com.darkfusion.gaurav.noodledroid.utils;

import static com.darkfusion.gaurav.noodledroid.utils.TokenType.*;

/**
 * Represents the smallest unit in the message/instruction sent to the Server
 * Example: Message -> "MSMMV:LBU:000:000"
 * Tokens: MS, MMV, :, LBU, 000, 000
 * Further clarified in: Tokenizer Class
 */
public class Token {
    /**
     * A prefix that indicates that the following message is a Keyboard instruction
     **/
    private static final String DEV_KEYBOARD = "KB";
    /**
     * A prefix that indicates that the following message is a Mouse instruction
     **/
    private static final String DEV_MOUSE = "MS";
    /**
     * Represents an event of the mouse moving
     **/
    private static final String MOUSE_MOVE = "MMV";
    /**
     * Represents a Single Click event of any of the two buttons of a Mouse
     **/
    private static final String SINGLE_CLICK = "MSC";
    /**
     * Represents an event of the left mouse mutton being pressed
     **/
    private static final String MOUSE_LEFT_BUTTON_DOWN = "LBD";
    /**
     * Represents an event of the left mouse mutton being released
     **/
    private static final String MOUSE_LEFT_BUTTON_UP = "LBU";
    /**
     * Represents an event of the right mouse mutton being pressed
     **/
    private static final String MOUSE_RIGHT_BUTTON_DOWN = "RBD";
    /**
     * Represents an event of the right mouse mutton being released
     **/
    private static final String MOUSE_RIGHT_BUTTON_UP = "RBU";
    /**
     * Represents the standard delimiter. Separates two non-prefix tokens
     **/
    private static final String DELIMITER = ":";
    /**
     * Represents an End-of-File event. Usually used when the connection to the server needs to be aborted
     **/
    private static final String EOF = "#EXIT#";

    private static final String NONE = "";

    String value;

    private Token(TokenType type, String value) {
        this.value = value;
    }

    private Token(DeviceType deviceType) {
        this.value = getTokenValue(deviceType);
    }

    Token(TokenType type) {
        this.value = getTokenValue(type);
    }

    Token(String value) {
        this.value = value;
    }

    private static String getTokenValue(DeviceType deviceType) {
        switch (deviceType) {
            case KEYBOARD:
                return Token.DEV_KEYBOARD;
            case MOUSE:
                return Token.DEV_MOUSE;
            default:
                return Token.NONE;
        }
    }

    public static String getTokenValue(TokenType tokenType) {
        switch (tokenType) {
            case TYPE_MOUSE_MOVE:
                return MOUSE_MOVE;
            case TYPE_MOUSE_LEFT_BUTTON_CLICK:
                return SINGLE_CLICK;
            case TYPE_DELIMITER:
                return DELIMITER;
            case TYPE_MOUSE_LEFT_BUTTON_DOWN:
                return MOUSE_LEFT_BUTTON_DOWN;
            case TYPE_MOUSE_LEFT_BUTTON_UP:
                return MOUSE_LEFT_BUTTON_UP;
            case TYPE_MOUSE_RIGHT_BUTTON_DOWN:
                return MOUSE_RIGHT_BUTTON_DOWN;
            case TYPE_MOUSE_RIGHT_BUTTON_UP:
                return MOUSE_RIGHT_BUTTON_UP;
            default:
                return "";
        }
    }

    static Token getToken(TokenType tokenType) {
        return new Token(tokenType);
    }

    static Token getCoordinateToken(String value) {
        return new Token(COORDINATE, value);
    }

    static Token getToken(DeviceType deviceType) {
        return new Token(deviceType);
    }
}




