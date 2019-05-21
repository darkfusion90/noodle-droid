package com.darkfusion.gaurav.noodledroid.utils;

import android.graphics.Point;
import android.support.annotation.NonNull;

public class Coordinate extends Point {
    int x, y;

    public Coordinate(int x, int y) {
        super(x, y);
    }

    public void setCoordinate(int x, int y) {
        super.set(x, y);
    }

    public Coordinate subtract(Coordinate other) {
        return new Coordinate(this.x - other.x, this.y - other.y);
    }

    public Coordinate copy() {
        return new Coordinate(this.x, this.y);
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }

    public int eulerDistance(Coordinate other) {
        int dx = this.x - other.x;
        int dy = this.y - other.y;

        int dxSquared = (int) Math.pow(dx, 2);
        int dySquared = (int) Math.pow(dy, 2);

        int distance = dxSquared + dySquared;
        return (int) Math.sqrt(distance);
    }
}
