package com.company;

import static com.company.TokenType.TYPE_DELIMITER;
import static com.company.TokenType.TYPE_EOF;
import static com.company.TokenType.TYPE_MOUSE_DRAG;
import static com.company.TokenType.TYPE_MOUSE_LEFT_BUTTON_DOWN;
import static com.company.TokenType.TYPE_MOUSE_LEFT_BUTTON_UP;
import static com.company.TokenType.TYPE_MOUSE_MOVE;
import static com.company.TokenType.TYPE_MOUSE_RIGHT_BUTTON_DOWN;
import static com.company.TokenType.TYPE_MOUSE_RIGHT_BUTTON_UP;
import static com.company.TokenType.TYPE_NONE;
import static com.company.TokenType.TYPE_SINGLE_CLICK;

//import static com.company.TokenType.TYPE_DOUBLE_CLICK;

public class Token {
	//private static final String DOUBLE_CLICK = "MDC";
	static final String DELIMITER = ":";
	private static final String DEV_KEYBOARD = "KB";
	private static final String DEV_MOUSE = "MS";
	private static final String MOUSE_MOVE = "MMV";
	private static final String MOUSE_DRAG = "MDR";
	private static final String MOUSE_LEFT_BUTTON_DOWN = "LBD";
	private static final String MOUSE_LEFT_BUTTON_UP = "LBU";
	private static final String MOUSE_RIGHT_BUTTON_DOWN = "RBD";
	private static final String MOUSE_RIGHT_BUTTON_UP = "RBU";
	private static final String SINGLE_CLICK = "MSC";
	private static final String EOF = "#EXIT#";
	private static final String NONE = "";
	
	
	private TokenType type;
	private String value;
	
	Token(String value) {
		this.value = value;
		this.type = getTokenType(value);
	}
	
	static TokenType getTokenType(String tokenValue) {
		switch (tokenValue) {
			case MOUSE_MOVE:
				return TYPE_MOUSE_MOVE;
			case MOUSE_DRAG:
				return TYPE_MOUSE_DRAG;
			case SINGLE_CLICK:
				return TYPE_SINGLE_CLICK;
			case DELIMITER:
				return TYPE_DELIMITER;
			case EOF:
				return TYPE_EOF;
			case MOUSE_LEFT_BUTTON_DOWN:
				return TYPE_MOUSE_LEFT_BUTTON_DOWN;
			case MOUSE_LEFT_BUTTON_UP:
				return TYPE_MOUSE_LEFT_BUTTON_UP;
			case MOUSE_RIGHT_BUTTON_DOWN:
				return TYPE_MOUSE_RIGHT_BUTTON_DOWN;
			case MOUSE_RIGHT_BUTTON_UP:
				return TYPE_MOUSE_RIGHT_BUTTON_UP;
			default:
				return TYPE_NONE;
		}
	}
	
	static String getTokenValue(TokenType tokenType) {
		switch (tokenType) {
			case TYPE_MOUSE_MOVE:
				return MOUSE_MOVE;
			case TYPE_MOUSE_DRAG:
				return MOUSE_DRAG;
			case TYPE_SINGLE_CLICK:
				return SINGLE_CLICK;
			//case TYPE_DOUBLE_CLICK:
			//return DOUBLE_CLICK;
			case TYPE_DELIMITER:
				return DELIMITER;
			case TYPE_EOF:
				return EOF;
			default:
				return "";
		}
	}
	
	static String getTokenValue(DeviceType deviceType) {
		switch (deviceType) {
			case KEYBOARD:
				return Token.DEV_KEYBOARD;
			case MOUSE:
				return Token.DEV_MOUSE;
			default:
				return Token.NONE;
		}
	}
	
	String getTokenValue() {
		return this.value;
	}
	
	TokenType getTokenType() {
		return this.type;
	}
	
	@Override
	public String toString() {
		return this.value;
	}
}
