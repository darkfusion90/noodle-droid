package com.darkfusion.gaurav.noodledroid.utils;

import android.support.annotation.NonNull;

public class Coordinate {
    public int x, y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate subtract(Coordinate other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;
        return new Coordinate(dx, dy);
    }

    public Coordinate copy() {
        return new Coordinate(this.x, this.y);
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public double eulerDistance(Coordinate other) {
        System.out.println("EULER DISTANCE:");
        int dx = this.x - other.x;
        int dy = this.y - other.y;

        System.out.println("\t\tdx: " + dx + " dy: " + dy);

        int dxSquared = (int) Math.pow(dx, 2);
        int dySquared = (int) Math.pow(dy, 2);

        System.out.println("\t\tdxSq: " + dxSquared + " dySq: " + dySquared);

        int distance = dxSquared + dySquared;
        System.out.println("\t\tdist: " + distance);
        System.out.println("\t\tdistSqRt (FINAL): " + Math.sqrt(distance));

        return Math.sqrt(distance);
    }
}
