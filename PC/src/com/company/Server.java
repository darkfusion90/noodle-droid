package com.company;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

class Server {
	private static final String HANDSHAKE_STRING = "OK";
	private static final int MAX_MESSAGE_LENGTH = 11;
	Socket socket;
	ServerSocket serverSocket;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;
	
	Server(int port) {
		try {
			serverSocket = new ServerSocket(port);
			System.out.println(serverSocket);
			socket = serverSocket.accept();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (this.socket == null) {
			System.out.println("NULL SOCKET");
		}
		this.outputStream = getServerOutput();
		if (this.outputStream == null) {
			System.out.println("NULL SERVER OUTPUT");
			System.exit(1);
		}
		this.inputStream = getServerInput();
	}
	
	private DataOutputStream getServerOutput() {
		DataOutputStream outputStream = null;
		try {
			outputStream = new DataOutputStream(this.socket.getOutputStream());
		} catch (IOException io) {
			System.err.println("IOException creating output writer for socket.");
		}
		return outputStream;
	}
	
	private DataInputStream getServerInput() {
		DataInputStream inputStream = null;
		try {
			inputStream = new DataInputStream(this.socket.getInputStream());
		} catch (IOException ignored) {
			//TODO Handle socketInput exception
		}
		return inputStream;
	}
	
	void performHandshake() {
		try {
			this.outputStream.writeUTF(HANDSHAKE_STRING);
			this.outputStream.flush();
		} catch (IOException e) {
			System.err.println("Exception in handshake");
		}
	}
	
	String receiveMessage() {
		String messageReceived = "";
		try {
			//First two bytes indicate device type to be operated (Keyboard/Mouse)
			String deviceType = readDeviceType();
			String actualMessage = readMessageAccordingToDeviceType(deviceType);
			messageReceived = deviceType + Token.DELIMITER + actualMessage;
			
		} catch (IOException e) {
			System.out.println("Error reading message");
		}
		return messageReceived;
	}
	
	
	private String readDeviceType() throws IOException {
		byte[] deviceTypeBytes = new byte[2];
		inputStream.readFully(deviceTypeBytes, 0, 2);
		return new String(deviceTypeBytes, StandardCharsets.UTF_8);
	}
	
	private int readMessageLength() throws IOException {
		byte[] messageLengthBytes = new byte[2];
		inputStream.readFully(messageLengthBytes, 0, 2);
		String lengthString = new String(messageLengthBytes, StandardCharsets.UTF_8);
		return Integer.parseInt(lengthString);
	}
	
	private String readActualMessageOfLength(int length) throws IOException {
		byte[] bytes = new byte[length];
		inputStream.readFully(bytes, 0, length);
		
		return new String(bytes, StandardCharsets.UTF_8);
	}
	
	private String readMessageAccordingToDeviceType(String deviceTypeString) throws IOException {
		DeviceType deviceType = DeviceType.getDeviceTypeFromDeviceString(deviceTypeString);
		
		switch (deviceType) {
			case MOUSE:
				return readActualMessageOfLength(MAX_MESSAGE_LENGTH);
			case KEYBOARD:
				int messageLength = readMessageLength();
				return readActualMessageOfLength(messageLength);
			default:
				return "";
		}
	}
}


