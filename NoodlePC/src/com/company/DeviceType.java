package com.company;

public enum DeviceType {
	KEYBOARD,
	MOUSE,
	NONE;
	
	static DeviceType getDeviceTypeFromDeviceString(String deviceString) {
		switch (deviceString) {
			case "KB":
				return KEYBOARD;
			case "MS":
				return MOUSE;
			default:
				return NONE;
		}
	}
}
