package com.company;

import java.util.ArrayList;

class Parser extends Thread {
	
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
		static DeviceOperator deviceOperator = new DeviceOperator();
		
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
			DeviceOperationType operationType = resolveDeviceOperationType(DeviceType.MOUSE, tokens.get(1).getTokenValue());
			deviceOperator.setDeviceOperationType(operationType);
			
			updateLeftButtonLock(tokens.get(2).getTokenValue());
			
			//everything following the device operation type are the operation values
			//E.g: For mouse, the operation code is:
			//          MS:MMV:LBU:12345:54321
			//Here, MS is device type(Mouse), MMV is operation type(Mouse Move), LBU/LBD is status of Left Button (Up/Down)
			//      The rest following are operation values
			deviceOperator.setDeviceOperationValues(new ArrayList<>(tokens.subList(3, tokens.size())));
			deviceOperator.operate();
		}
		
		
		static void updateLeftButtonLock(String token) {
			MouseHandler.isLeftButtonPress = token.equals("LBD");
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
		
		static DeviceOperationType resolveDeviceOperationType(DeviceType deviceType, String text) {
			switch (deviceType) {
				case KEYBOARD:
					return DeviceOperationType.KEYBOARD_TYPE;
				case MOUSE:
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
				default:
					return DeviceOperationType.NONE;
			}
		}
	}
}

