package com.bin.test_11;

import android.util.Pair;
import java.util.ArrayList;

public class Joint {
    private String name;
    private boolean isMobile;
    private double XCoordinate = 0;
    private double YCoordinate = 0;
    private ArrayList<Joint> neighbors = new ArrayList<Joint>();

    //Helper calculator to assist calculating movement
    private Helper_Calculator calculator = new Helper_Calculator();

    public Joint(String inputName, boolean inputIsMobile) {
        this.name = inputName;
        this.isMobile = inputIsMobile;
    }

    //Manage name
    public String getName() {
        return name;
    }

    //Manage mobility
    public boolean isMobile() {
        return isMobile;
    }

    //Manage x coordinate
    public double getXCoordinate() {
        return this.XCoordinate;
    }

    public void setXCoordinate(double xCoordinate) {
        this.XCoordinate = xCoordinate;
    }

    //Manage y coordinate
    public double getYCoordinate() {
        return YCoordinate;
    }

    public void setYCoordinate(double yCoordinate) {
        this.YCoordinate = yCoordinate;
    }

    public Helper_Calculator getCalculator() {
        return calculator;
    }

    //Manage neighbors (Joints)
    public ArrayList<Joint> getNeighbors() {
        return neighbors;
    }

    public void setNeighbors(ArrayList<Joint> neighbors) {
        this.neighbors = neighbors;
    }

    public void addNeighbor(Joint another) {
        this.neighbors.add(another);
    }

    public boolean hasNeighbor(Joint another) {
        return this.neighbors.contains(another);
    }
    //This method checks if this joint is connected to
    //any of the joints in the "otherJoints" collection.
    public boolean checkForNeighbors(ArrayList<Joint> otherJoints) {
        boolean connectedToAny = false;
        for (Joint joint : otherJoints) {
            if (joint.hasNeighbor(this)) connectedToAny = true;
        }
        return connectedToAny;
    }

    //Manage distances
    public double XDifference(Joint another) {
        return another.getXCoordinate()-this.getXCoordinate();
    }

    public double YDifference(Joint another) {
        return another.getYCoordinate()-this.getYCoordinate();
    }

    public double distance(Joint another) {
        return java.lang.Math.sqrt(
                this.XDifference(another)*this.XDifference(another)
              + this.YDifference(another)*this.YDifference(another));
    }

    public void moveTo(double newX, double newY) {
        this.setXCoordinate(newX);
        this.setYCoordinate(newY);
    }

    public Pair<Double, Double> draggingMotion(Joint pivot,
                                            double mousePosX,
                                            double mousePosY) {

        double resultFirst = this.getXCoordinate();
        double resultSecond = this.getYCoordinate();

        if (!this.calculator.tooClose(mousePosX, mousePosY, pivot.getXCoordinate(), pivot.YCoordinate)) {



            double jp_x = calculator.maximum(this.getXCoordinate()-pivot.getXCoordinate(), 0.1);

            double jp_y = calculator.maximum(this.getYCoordinate()-pivot.getYCoordinate(), 0.1);

            Pair<Double, Double> jp = new Pair<Double, Double>(jp_x, jp_y);



            double mp_x = calculator.maximum(mousePosX-pivot.getXCoordinate(), 0.1);

            double mp_y = calculator.maximum(mousePosY-pivot.getYCoordinate(), 0.1);

            Pair<Double, Double> mp = new Pair<Double, Double>(mp_x, mp_y);



            if (!this.calculator.oppositeDirection(jp.first, jp.second, mp.first, mp.second)) {
                if (this.getXCoordinate() != pivot.getXCoordinate() || this.getYCoordinate() != pivot.getYCoordinate()) {



                    double vectorPivotToMouse_x = calculator.maximum(mousePosX - pivot.getXCoordinate(), 0.1);

                    double vectorPivotToMouse_y = calculator.maximum(mousePosY - pivot.getYCoordinate(), 0.1);

                    Pair<Double, Double> vectorPivotToMouse = new Pair<Double, Double>(vectorPivotToMouse_x, vectorPivotToMouse_y);



                    double length = calculator.vectorLength(vectorPivotToMouse.first, vectorPivotToMouse.second);

                    if (length != 0) {
                        double modifier = 1.0 * this.distance(pivot)/length;

                        Pair<Double, Double> newVectorPivotToThis = calculator.amplifiedVector(vectorPivotToMouse.first,
                                                                                               vectorPivotToMouse.second,
                                                                                               modifier);

                        resultFirst = pivot.getXCoordinate() + newVectorPivotToThis.first;
                        resultSecond = pivot.getYCoordinate() + newVectorPivotToThis.second;
                    }

                }
            }
        }

        return new Pair<Double, Double>(resultFirst, resultSecond);
    }

    public Pair<Double, Double> betaMove(Joint pivot, double initialX, double initialY,
                                         double mouseX, double mouseY) {

        double resultFirst = this.getXCoordinate();
        double resultSecond = this.getYCoordinate();

        if (initialX != pivot.getXCoordinate() || initialY != pivot.getYCoordinate()) {

            //Pivot - Dragged
            Pair<Double, Double> vecPvDr = new Pair<Double, Double>(initialX - pivot.getXCoordinate(),
                                                                    initialY - pivot.getYCoordinate());

            double lenPvDr = calculator.vectorLength(vecPvDr.first, vecPvDr.second);

            Pair<Double, Double> mulPvDr = calculator.amplifiedVector(vecPvDr.first, vecPvDr.second, 1.0/lenPvDr);



            //Pivot - Mouse
            Pair<Double, Double> vecPvMs = new Pair<Double, Double>(mouseX - pivot.getXCoordinate(),
                                                                    mouseY - pivot.getYCoordinate());

            double lenPvMs = calculator.vectorLength(vecPvMs.first, vecPvMs.second);

            Pair<Double, Double> mulPvMs = calculator.amplifiedVector(vecPvMs.first, vecPvMs.second, 1.0/lenPvMs);



            //Pivot - Affected
            Pair<Double, Double> vecPvAf = new Pair<Double, Double>(this.getXCoordinate() - pivot.getXCoordinate(),
                                                                    this.getYCoordinate() - pivot.getYCoordinate());

            double lenPvAf = calculator.vectorLength(vecPvAf.first, vecPvAf.second);

            Pair<Double, Double> mulPvAf = calculator.amplifiedVector(vecPvAf.first, vecPvAf.second, 1.0/lenPvAf);



            //Arc: Mouse - Dragged
            double arcMsDr = mulPvDr.first * mulPvMs.first + mulPvDr.second * mulPvMs.second;

            //Arc: Affected - Dragged
            double arcAfDr = mulPvDr.first * mulPvAf.first + mulPvDr.second * mulPvAf.second;

            double a = mulPvAf.first;
            double b = mulPvAf.second;
            if (b == 0) b = 1E-8;

            double c = mulPvMs.first;
            double d = mulPvMs.second;

            double m = arcMsDr;
            double n = arcAfDr;

            double divider = (d*a-b*c);

            if (divider != 0) {
                double x = 1.0*(d*m - b*n)/divider;
                double y = 1.0*(m - a*x)/b;

                Pair<Double, Double> middleVar1 = this.draggingMotion(pivot,
                        pivot.getXCoordinate() + x,
                        pivot.getYCoordinate() + y);

                resultFirst = middleVar1.first;
                resultSecond = middleVar1.second;
            } else {
                if (calculator.oppositeSigns(a,c) && calculator.oppositeSigns(b,d)) {
                    Pair<Double, Double> middleVar2 = this.draggingMotion(pivot,
                            2*pivot.getXCoordinate() - mouseX,
                            2*pivot.getYCoordinate() - mouseY);

                    resultFirst = middleVar2.first;
                    resultSecond = middleVar2.second;
                } else {
                    Pair<Double, Double> middleVar3 = this.draggingMotion(pivot,
                            mouseX,
                            mouseY);

                    resultFirst = middleVar3.first;
                    resultSecond = middleVar3.second;
                }
            }

        } else {
            resultFirst = this.getXCoordinate();
            resultSecond = this.getYCoordinate();
        }

        return new Pair<Double, Double>(resultFirst, resultSecond);
    }
}
