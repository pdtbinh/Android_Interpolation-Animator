package com.bin.test_11;

import android.util.Pair;

public class Helper_Calculator {
    public double maximum(double a, double b) {
        if (a == 0) {
            return b;
        } else {
            return a;
        }
    }

    public double vectorLength(double xDir, double yDir) {
        return java.lang.Math.sqrt(xDir*xDir + yDir*yDir);
    }

    public boolean tooClose(double p1x, double p1y, double p2x, double p2y) {
        return this.vectorLength(p1x-p2x, p1y-p2y) <= 0.8;
    }

    public Pair<Double, Double> amplifiedVector(double xDir,
                                                double yDir,
                                                double modifier) {
        return new Pair<Double, Double>(xDir*modifier, yDir*modifier);
    }

    public boolean approximate(double x, double y) {
        return y-1E-10 <= x && x <= y+1E-10;
    }

    public boolean oppositeSigns(double a, double b) {
        return 1.0*a*b<=0;
    }

    public boolean oppositeDirection(double v1X, double v1Y,
                                     double v2X, double v2Y) {
        return this.approximate(v1X*v2Y, v1Y*v2X)
                && this.oppositeSigns(v1X, v2X)
                && this.oppositeSigns(v1Y, v2Y);
    }

}
