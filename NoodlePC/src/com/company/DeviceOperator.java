package com.company;

import java.awt.Point;
import java.util.ArrayList;

class DeviceOperator {
	private DeviceType deviceType;
	private DeviceOperationType deviceOperationType;
	private ArrayList<Token> deviceOperationValues;
	
	
	private DeviceType getDeviceType() {
		return this.deviceType;
	}
	
	void setDeviceType(DeviceType type) {
		this.deviceType = type;
	}
	
	private DeviceOperationType getDeviceOperationType() {
		return this.deviceOperationType;
	}
	
	void setDeviceOperationType(DeviceOperationType deviceOperationType) {
		this.deviceOperationType = deviceOperationType;
	}
	
	private ArrayList<Token> getDeviceOperationValues() {
		return this.deviceOperationValues;
	}
	
	void setDeviceOperationValues(ArrayList<Token> deviceOperationValues) {
		this.deviceOperationValues = deviceOperationValues;
	}
	
	void operate() {
		switch (this.getDeviceType()) {
			case KEYBOARD:
				operateKeyboard();
				break;
			case MOUSE:
				operateMouse();
		}
	}
	
	private void operateMouse() {
		switch (this.getDeviceOperationType()) {
			case MOUSE_MOVE:
				int x = Integer.valueOf(deviceOperationValues.get(0).getTokenValue());
				int y = Integer.valueOf(deviceOperationValues.get(1).getTokenValue());
				//System.out.println("Mouse Move: (" + x + ", " + y + ")");
				new MouseHandler().mouseMove(new Point(x * 3, y * 2));
				break;
			case MOUSE_DRAG:
				System.out.println("Mouse drag is currently unavailable");
				//new MouseHandler().dragMouse(new Point(0, 0));
				break;
			case MOUSE_SINGLE_CLICK:
				//System.out.println("Single click");
				new MouseHandler().singleClick(MouseHandler.LEFT_BUTTON);
				break;
			case MOUSE_LEFT_BUTTON_DOWN:
				new MouseHandler().buttonDown(MouseHandler.LEFT_BUTTON);
				break;
			case MOUSE_LEFT_BUTTON_UP:
				new MouseHandler().buttonUp(MouseHandler.LEFT_BUTTON);
				break;
			case MOUSE_RIGHT_BUTTON_DOWN:
				new MouseHandler().buttonDown(MouseHandler.RIGHT_BUTTON);
				break;
			case MOUSE_RIGHT_BUTTON_UP:
				new MouseHandler().buttonUp(MouseHandler.RIGHT_BUTTON);
				break;
		}
	}
	
	private void operateKeyboard() {
		for (Token value : this.getDeviceOperationValues()) {
			KeyboardHandler keyboardHandler = new KeyboardHandler();
			keyboardHandler.type(value.toString());
		}
	}
}

