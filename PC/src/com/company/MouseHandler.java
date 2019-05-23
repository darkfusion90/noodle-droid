package com.company;

import java.awt.AWTException;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;


class MouseHandler {
	static final int LEFT_BUTTON = 1;
	static final int RIGHT_BUTTON = 2;
	private Robot robot;
	
	
	MouseHandler() {
		try {
			robot = new Robot();
		} catch (AWTException awtex) {
			System.out.println("Error creating Mouse Robot");
		}
	}
	
	static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			System.out.println("Error while sleeping :(");
		}
		
	}
	
	void singleClick(int buttonType) {
		if (buttonType == LEFT_BUTTON) {
			robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
			
		} else {//if(buttonType == MouseHandler.RIGHT_BUTTON) {
			robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
			robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
		}
	}
	
	void mouseMove(Point point) {
		Point currentMousePosition = MouseInfo.getPointerInfo().getLocation();
		int dx = currentMousePosition.x + point.x;
		int dy = currentMousePosition.y + point.y;
		
		robot.mouseMove(dx, dy);
	}
	
	void buttonDown(int buttonType) {
		switch (buttonType) {
			case LEFT_BUTTON:
				System.out.println("LEFT DOWN");
				robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
				break;
			case RIGHT_BUTTON:
				System.out.println("RIGHT DOWN");
				robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
				break;
		}
	}
	
	void buttonUp(int buttonType) {
		switch (buttonType) {
			case LEFT_BUTTON:
				System.out.println("LEFT UP");
				robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				break;
			case RIGHT_BUTTON:
				System.out.println("RIGHT UP");
				robot.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
				break;
		}
	}
}
