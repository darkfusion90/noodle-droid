package com.company;

import java.awt.AWTException;
import java.awt.Robot;

import static java.awt.event.KeyEvent.*;

class KeyboardHandler {
	private static final int STATE_ACTIVE = 1;
	private static final int STATE_INACTIVE = 2;
	private static int controlButtonState = STATE_INACTIVE;
	private static int altButtonState = STATE_INACTIVE;
	private static int shiftButtonState = STATE_INACTIVE;
	private Robot robot;
	
	KeyboardHandler() {
		robot = getNewRobot();
	}
	
	static void cleanup() {
		Robot cleanupRobo = KeyboardHandler.getNewRobot();
		if (cleanupRobo == null) {
			return;
		}
		
		if (isControlPressed()) {
			cleanupRobo.keyRelease(VK_CONTROL);
		}
		if (isAltPressed()) {
			cleanupRobo.keyRelease(VK_ALT);
		}
		if (isShiftPressed()) {
			cleanupRobo.keyRelease(VK_SHIFT);
		}
	}
	
	private static Robot getNewRobot() {
		Robot newRobot;
		try {
			newRobot = new Robot();
		} catch (AWTException e) {
			newRobot = null;
		}
		return newRobot;
	}
	
	private static boolean isControlPressed() {
		return controlButtonState == STATE_ACTIVE;
	}
	
	private static boolean isAltPressed() {
		return altButtonState == STATE_ACTIVE;
	}
	
	private static boolean isShiftPressed() {
		return shiftButtonState == STATE_ACTIVE;
	}
	
	void type(CharSequence characters) {
		if (characters.length() == 1) {
			type(characters.charAt(0));
			return;
		}
		String stringChars = characters.toString();
		System.out.println("STRING CHARS: " + stringChars);
		
		switch (stringChars) {
			case "KEYCODE_SHIFT":
				toggleShiftButton();
				break;
			case "KEYCODE_CTRL":
				toggleControlButton();
				break;
			case "KEYCODE_ALT":
				toggleAltButton();
				break;
			case "KEYCODE_WINDOWS":
				doType(VK_WINDOWS);
				break;
			case "KEYCODE_MENU":
				doType(VK_CONTEXT_MENU);
				break;
			case "KEYCODE_PRINT_SCREEN":
				doType(VK_PRINTSCREEN);
				break;
			case "KEYCODE_ESCAPE":
				doType(VK_ESCAPE);
				break;
			case "KEYCODE_HOME":
				doType(VK_HOME);
				break;
			case "KEYCODE_END":
				doType(VK_END);
				break;
			case "KEYCODE_DEL":
				doType(VK_BACK_SPACE);
				break;
			case "KEYCODE_NUM_LOCK":
				doType(VK_NUM_LOCK);
				break;
			case "KEYCODE_CAPS_LOCK":
				doType(VK_CAPS_LOCK);
				break;
			case "KEYCODE_SCROLL_LOCK":
				doType(VK_SCROLL_LOCK);
				break;
			case "KEYCODE_ENTER":
				doType(VK_ENTER);
				break;
			case "KEYCODE_F1":
				doType(VK_F1);
				break;
			case "KEYCODE_F2":
				doType(VK_F2);
				break;
			case "KEYCODE_F3":
				doType(VK_F3);
				break;
			case "KEYCODE_F4":
				doType(VK_F4);
				break;
			case "KEYCODE_F5":
				doType(VK_F5);
				break;
			case "KEYCODE_F6":
				doType(VK_F6);
				break;
			case "KEYCODE_F7":
				doType(VK_F7);
				break;
			case "KEYCODE_F8":
				doType(VK_F8);
				break;
			case "KEYCODE_F9":
				doType(VK_F9);
				break;
			case "KEYCODE_F10":
				doType(VK_F10);
				break;
			case "KEYCODE_F11":
				doType(VK_F11);
				break;
			case "KEYCODE_F12":
				doType(VK_F12);
				break;
		}
	}
	
	//#region credits: https://stackoverflow.com/a/1248709
	private void type(char character) {
		switch (character) {
			case 'a':
				doType(VK_A);
				break;
			case 'b':
				doType(VK_B);
				break;
			case 'c':
				doType(VK_C);
				break;
			case 'd':
				doType(VK_D);
				break;
			case 'e':
				doType(VK_E);
				break;
			case 'f':
				doType(VK_F);
				break;
			case 'g':
				doType(VK_G);
				break;
			case 'h':
				doType(VK_H);
				break;
			case 'i':
				doType(VK_I);
				break;
			case 'j':
				doType(VK_J);
				break;
			case 'k':
				doType(VK_K);
				break;
			case 'l':
				doType(VK_L);
				break;
			case 'm':
				doType(VK_M);
				break;
			case 'n':
				doType(VK_N);
				break;
			case 'o':
				doType(VK_O);
				break;
			case 'p':
				doType(VK_P);
				break;
			case 'q':
				doType(VK_Q);
				break;
			case 'r':
				doType(VK_R);
				break;
			case 's':
				doType(VK_S);
				break;
			case 't':
				doType(VK_T);
				break;
			case 'u':
				doType(VK_U);
				break;
			case 'v':
				doType(VK_V);
				break;
			case 'w':
				doType(VK_W);
				break;
			case 'x':
				doType(VK_X);
				break;
			case 'y':
				doType(VK_Y);
				break;
			case 'z':
				doType(VK_Z);
				break;
			case 'A':
				doType(VK_SHIFT, VK_A);
				break;
			case 'B':
				doType(VK_SHIFT, VK_B);
				break;
			case 'C':
				doType(VK_SHIFT, VK_C);
				break;
			case 'D':
				doType(VK_SHIFT, VK_D);
				break;
			case 'E':
				doType(VK_SHIFT, VK_E);
				break;
			case 'F':
				doType(VK_SHIFT, VK_F);
				break;
			case 'G':
				doType(VK_SHIFT, VK_G);
				break;
			case 'H':
				doType(VK_SHIFT, VK_H);
				break;
			case 'I':
				doType(VK_SHIFT, VK_I);
				break;
			case 'J':
				doType(VK_SHIFT, VK_J);
				break;
			case 'K':
				doType(VK_SHIFT, VK_K);
				break;
			case 'L':
				doType(VK_SHIFT, VK_L);
				break;
			case 'M':
				doType(VK_SHIFT, VK_M);
				break;
			case 'N':
				doType(VK_SHIFT, VK_N);
				break;
			case 'O':
				doType(VK_SHIFT, VK_O);
				break;
			case 'P':
				doType(VK_SHIFT, VK_P);
				break;
			case 'Q':
				doType(VK_SHIFT, VK_Q);
				break;
			case 'R':
				doType(VK_SHIFT, VK_R);
				break;
			case 'S':
				doType(VK_SHIFT, VK_S);
				break;
			case 'T':
				doType(VK_SHIFT, VK_T);
				break;
			case 'U':
				doType(VK_SHIFT, VK_U);
				break;
			case 'V':
				doType(VK_SHIFT, VK_V);
				break;
			case 'W':
				doType(VK_SHIFT, VK_W);
				break;
			case 'X':
				doType(VK_SHIFT, VK_X);
				break;
			case 'Y':
				doType(VK_SHIFT, VK_Y);
				break;
			case 'Z':
				doType(VK_SHIFT, VK_Z);
				break;
			case '`':
				doType(VK_BACK_QUOTE);
				break;
			case '0':
				doType(VK_0);
				break;
			case '1':
				doType(VK_1);
				break;
			case '2':
				doType(VK_2);
				break;
			case '3':
				doType(VK_3);
				break;
			case '4':
				doType(VK_4);
				break;
			case '5':
				doType(VK_5);
				break;
			case '6':
				doType(VK_6);
				break;
			case '7':
				doType(VK_7);
				break;
			case '8':
				doType(VK_8);
				break;
			case '9':
				doType(VK_9);
				break;
			case '-':
				doType(VK_MINUS);
				break;
			case '=':
				doType(VK_EQUALS);
				break;
			case '~':
				doType(VK_SHIFT, VK_BACK_QUOTE);
				break;
			case '!':
				doType(VK_EXCLAMATION_MARK);
				break;
			case '@':
				doType(VK_AT);
				break;
			case '#':
				doType(VK_NUMBER_SIGN);
				break;
			case '$':
				doType(VK_DOLLAR);
				break;
			case '%':
				doType(VK_SHIFT, VK_5);
				break;
			case '^':
				doType(VK_CIRCUMFLEX);
				break;
			case '&':
				doType(VK_AMPERSAND);
				break;
			case '*':
				doType(VK_ASTERISK);
				break;
			case '(':
				doType(VK_LEFT_PARENTHESIS);
				break;
			case ')':
				doType(VK_RIGHT_PARENTHESIS);
				break;
			case '_':
				doType(VK_UNDERSCORE);
				break;
			case '+':
				doType(VK_PLUS);
				break;
			case '\t':
				doType(VK_TAB);
				break;
			case '\n':
				doType(VK_ENTER);
				break;
			case '[':
				doType(VK_OPEN_BRACKET);
				break;
			case ']':
				doType(VK_CLOSE_BRACKET);
				break;
			case '\\':
				doType(VK_BACK_SLASH);
				break;
			case '{':
				doType(VK_SHIFT, VK_OPEN_BRACKET);
				break;
			case '}':
				doType(VK_SHIFT, VK_CLOSE_BRACKET);
				break;
			case '|':
				doType(VK_SHIFT, VK_BACK_SLASH);
				break;
			case ';':
				doType(VK_SEMICOLON);
				break;
			case ':':
				doType(VK_COLON);
				break;
			case '\'':
				doType(VK_QUOTE);
				break;
			case '"':
				doType(VK_QUOTEDBL);
				break;
			case ',':
				doType(VK_COMMA);
				break;
			case '<':
				doType(VK_SHIFT, VK_COMMA);
				break;
			case '.':
				doType(VK_PERIOD);
				break;
			case '>':
				doType(VK_SHIFT, VK_PERIOD);
				break;
			case '/':
				doType(VK_SLASH);
				break;
			case '?':
				doType(VK_SHIFT, VK_SLASH);
				break;
			case ' ':
				doType(VK_SPACE);
				break;
			default:
				//throw new IllegalArgumentException("Cannot type character " + character);
				System.out.println("Cannot type character " + character);
		}
	}
	
	private void doType(int... keyCodes) {
		doType(keyCodes, 0, keyCodes.length);
	}
	
	private void doType(int[] keyCodes, int offset, int length) {
		if (length == 0) {
			return;
		}
		
		keyPress(keyCodes[offset]);
		doType(keyCodes, offset + 1, length - 1);
		keyRelease(keyCodes[offset]);
	}
	//#endregion
	
	private void keyPress(int keyCode) {
		robot.keyPress(keyCode);
	}
	
	private void keyRelease(int keyCode) {
		robot.keyRelease(keyCode);
	}
	
	private void toggleShiftButton() {
		if (shiftButtonState == STATE_ACTIVE) {
			shiftButtonState = STATE_INACTIVE;
			robot.keyRelease(VK_SHIFT);
		} else {
			shiftButtonState = STATE_ACTIVE;
			robot.keyPress(VK_SHIFT);
		}
	}
	
	private void toggleControlButton() {
		if (controlButtonState == STATE_ACTIVE) {
			controlButtonState = STATE_INACTIVE;
			robot.keyRelease(VK_CONTROL);
		} else {
			controlButtonState = STATE_ACTIVE;
			robot.keyPress(VK_CONTROL);
		}
	}
	
	private void toggleAltButton() {
		if (altButtonState == STATE_ACTIVE) {
			altButtonState = STATE_INACTIVE;
			robot.keyRelease(VK_ALT);
		} else {
			altButtonState = STATE_ACTIVE;
			robot.keyPress(VK_ALT);
		}
	}
}


