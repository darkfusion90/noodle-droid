package com.darkfusion.gaurav.noodledroid.utils;

import android.view.KeyEvent;

/**
 * All KeyCodes not present in android GUI (android.view.KeyEvent) will be enlisted here
 */
public class CustomKeyEvent {
    /**
     * KeyCode for END Button
     */
    public static final int CUSTOM_KEYCODE_END = -1;
    /**
     * KeyCode for HOME Button
     */
    public static final int CUSTOM_KEYCODE_HOME = -2;
    /**
     * KeyCode for PRINT-SCREEN Button
     */
    public static final int CUSTOM_KEYCODE_PRINT_SCREEN = -3;
    /**
     * KeyCode for WINDOWS Button
     */
    public static final int CUSTOM_KEYCODE_WINDOWS = -5;


    /**
     * Returns a string representation of the keyCode
     * If it is not a CUSTOM_KEYCODE_* then
     * android.view.KeyEvent is used as a fallback to transform the keyCode to String
     *
     * Since the PC uses Java's Robot API to control the Keyboard,
     * we omit the "left" special keys (Ctrl, alt, shift) and just include the key's name
     * "KEYCODE_CTRL_LEFT" turns to "KEYCODE_CTRL"
     *
     * @param keyCode The keycode of any key (For example: CUSTOM_KEYCODE_END)
     * @return The corresponding String representation of the keyCode
     */
    public static String keyCodeToString(int keyCode) {
        switch (keyCode) {
            case CUSTOM_KEYCODE_END:
                return "KEYCODE_END";
            case CUSTOM_KEYCODE_HOME:
                return "KEYCODE_HOME";
            case CUSTOM_KEYCODE_PRINT_SCREEN:
                return "KEYCODE_PRINT_SCREEN";
            case CUSTOM_KEYCODE_WINDOWS:
                return "KEYCODE_WINDOWS";
            case KeyEvent.KEYCODE_CTRL_LEFT:
                return "KEYCODE_CTRL";
            case KeyEvent.KEYCODE_ALT_LEFT:
                return "KEYCODE_ALT";
            case KeyEvent.KEYCODE_SHIFT_LEFT:
                return "KEYCODE_SHIFT";
            default:
                return KeyEvent.keyCodeToString(keyCode);
        }
    }
}

