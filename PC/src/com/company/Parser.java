package com.company;

import java.util.ArrayList;

class Parser extends Thread {
	
	private static DeviceOperator deviceOperator = new DeviceOperator();
	
	private String text;
	
	Parser(String text) {
		this.text = text;
	}
	
	@Override
	public void run() {
		this.parse();
	}
	
	private void parse() {
		SemanticAnalyser.analyse(LexicalAnalyser.analyse(this.text));
	}
	
	private static class LexicalAnalyser {
		static ArrayList<Token> analyse(String text) {
			
			ArrayList<Token> tokens = new ArrayList<>();
			String[] rawTokens = text.split(Token.getTokenValue(TokenType.TYPE_DELIMITER));
			
			for (String rawToken : rawTokens) {
				tokens.add(new Token(rawToken));
			}
			return tokens;
		}
	}
	
	private static class SemanticAnalyser {
		
		static void analyse(ArrayList<Token> tokens) {
			DeviceType deviceType = resolveDeviceType(tokens.get(0).getTokenValue());
			deviceOperator.setDeviceType(deviceType);
			if (deviceType == DeviceType.KEYBOARD) {
				analyseKeyboardTokens(tokens);
			} else {
				analyseMouseTokens(tokens);
			}
		}
		
		private static void analyseKeyboardTokens(ArrayList<Token> tokens) {
			deviceOperator.setDeviceOperationType(DeviceOperationType.KEYBOARD_TYPE);
			deviceOperator.setDeviceOperationValues(new ArrayList<>(tokens.subList(1, tokens.size())));
			deviceOperator.operate();
		}
		
		private static void analyseMouseTokens(ArrayList<Token> tokens) {
			DeviceOperationType operationType = resolveMouseOperationType(tokens.get(1).getTokenValue());
			deviceOperator.setDeviceOperationType(operationType);
			
			deviceOperator.setDeviceOperationValues(new ArrayList<>(tokens.subList(2, tokens.size())));
			deviceOperator.operate();
		}
		
		static DeviceType resolveDeviceType(String text) {
			if (text.equals(Token.getTokenValue(DeviceType.KEYBOARD))) {
				return DeviceType.KEYBOARD;
			} else if (text.equals(Token.getTokenValue(DeviceType.MOUSE))) {
				return DeviceType.MOUSE;
			} else {
				return DeviceType.NONE;
			}
		}
		
		static DeviceOperationType resolveMouseOperationType(String text) {
			
			switch (Token.getTokenType(text)) {
				case TYPE_MOUSE_MOVE:
					return DeviceOperationType.MOUSE_MOVE;
				case TYPE_MOUSE_DRAG:
					return DeviceOperationType.MOUSE_DRAG;
				case TYPE_SINGLE_CLICK:
					return DeviceOperationType.MOUSE_SINGLE_CLICK;
				case TYPE_MOUSE_LEFT_BUTTON_DOWN:
					return DeviceOperationType.MOUSE_LEFT_BUTTON_DOWN;
				case TYPE_MOUSE_LEFT_BUTTON_UP:
					return DeviceOperationType.MOUSE_LEFT_BUTTON_UP;
				case TYPE_MOUSE_RIGHT_BUTTON_DOWN:
					return DeviceOperationType.MOUSE_RIGHT_BUTTON_DOWN;
				case TYPE_MOUSE_RIGHT_BUTTON_UP:
					return DeviceOperationType.MOUSE_RIGHT_BUTTON_UP;
				default:
					return DeviceOperationType.NONE;
			}
		}
	}
}

