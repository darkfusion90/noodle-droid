package com.darkfusion.gaurav.noodledroid;

import com.darkfusion.gaurav.noodledroid.utils.Coordinate;

import java.text.DecimalFormat;

class MouseInstructionMessageBuilder {
    private static final String MOUSE_DEVICE_PREFIX = "MS";
    private static final DecimalFormat decimalFormat = new DecimalFormat("000");

    private MouseMessageType messageType;
    private Coordinate mouseCoordinate;
    private StringBuilder messageBuilder;

    MouseInstructionMessageBuilder(MouseMessageType messageType) {
        this.messageBuilder = new StringBuilder(MOUSE_DEVICE_PREFIX);
        this.messageType = messageType;
    }

    public void setMouseCoordinate(Coordinate mouseCoordinate) {
        this.mouseCoordinate = mouseCoordinate;
    }

    private String formatNumberToThreeDigits(int value) {
        String result = decimalFormat.format(value);
        if (result.startsWith("-")) {
            result = result.replaceFirst("0", "");
        }
        return result;
    }

    void createMouseMoveMessage() {
        appendOperationCode();
        appendCoordinates();
    }

    private String getOperationCode(){
        return this.messageType.typeToString();
    }
    
    private void appendOperationCode() {
        String code = getOperationCode();
        this.messageBuilder.append(code);
        this.messageBuilder.append(":");
    }

    private void appendCoordinates() {
        if(this.mouseCoordinate == null){
            return;
        }
        appendCoordinateX();
        appendCoordinateY();
    }

    private void appendCoordinateX() {
        String x = formatNumberToThreeDigits(this.mouseCoordinate.x);
        this.messageBuilder.append(x);
        this.messageBuilder.append(":");
    }

    private void appendCoordinateY() {
        String y = formatNumberToThreeDigits(this.mouseCoordinate.y);
        this.messageBuilder.append(y);
        this.messageBuilder.append(":");
    }
}

