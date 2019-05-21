package com.darkfusion.gaurav.noodledroid.utils;

/**
 * All KeyCodes not present in android GUI (android.view.KeyEvent) will be enlisted here
 * */
public class CustomKeyEvent {
    /**KeyCode for END Button*/
    public static final int CUSTOM_KEYCODE_END = -1;
    /**KeyCode for HOME Button*/
    public static final int CUSTOM_KEYCODE_HOME = -2;
    /**KeyCode for PRINT-SCREEN Button*/
    public static final int CUSTOM_KEYCODE_PRINT_SCREEN = -3;
    /**KeyCode for WINDOWS Button*/
    public static final int CUSTOM_KEYCODE_WINDOWS = -5;

    /**KeyCode for an undefined Button (Returned if keycode doesn't match any of the custom keyCodes)*/
    private static final String CUSTOM_UNDEFINED = "UDF";

    /**
     *
     * @param keyCode The keycode of any key (For example: CUSTOM_KEYCODE_END)
     * @return The corresponding String representation of the keyCode
     */
    public static String keyCodeToString(int keyCode) {
        switch (keyCode) {
            case CUSTOM_KEYCODE_END:
                return "END";
            case CUSTOM_KEYCODE_HOME:
                return "HOME";
            case CUSTOM_KEYCODE_PRINT_SCREEN:
                return "PRINT_SCREEN";
            case CUSTOM_KEYCODE_WINDOWS:
                return "SUPER";
            default:
                return CUSTOM_UNDEFINED;
        }
    }
}

