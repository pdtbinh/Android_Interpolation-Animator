package com.bin.test_11;

import java.util.ArrayList;
import android.util.Pair;

public class Window {
    private ArrayList<Frame> frames;

    public Window() {
        this.frames = new ArrayList<Frame>();
    }

    public ArrayList<Frame> getFrames() {
        return frames;
    }

    public void addFrame(Frame frame) {
        this.frames.add(frame);
    }

    public void deleteFrame(Frame frame) {
        this.frames.remove(frame);
    }

    public void setFrame(int position, Frame frame) {this.frames.set(position, frame);}

    public Frame createCopyOf(Frame frame) {
        Frame result =  new Frame();
        result.setBitmapBackground(frame.getBitmapBackground());
        for (Figure figure : frame.getFigures()) {

            //Create a new figure with the same type and name
            Figure clonedFigure = new Figure(
                    figure.getType(),
                    figure.getName());

            //Move the cloned figure to the matched location of its original version
            for (int index = 0; index < figure.getJoints().size(); index++) {
                double newX = figure.getJoints().get(index).getXCoordinate();
                double newY = figure.getJoints().get(index).getYCoordinate();
                Joint movingJoint = clonedFigure.getJoints().get(index);
                clonedFigure.moveMan(movingJoint, newX, newY);
            }
            result.addFigure(clonedFigure);
        }
        return result;
    }

    public ArrayList<Frame> interpolation(Frame start, Frame end, int number) {
        Frame startFrameHolder = this.createCopyOf(start);
        Frame endFrameHolder   = this.createCopyOf(end);

        /*
        ArrayList<String> partNames = new ArrayList<String>();

        partNames.add("bottom");
        partNames.add("neck");   partNames.add("head");
        partNames.add("arm_l");  partNames.add("arm_r");
        partNames.add("hand_l"); partNames.add("hand_r");
        partNames.add("knee_l"); partNames.add("knee_r");
        partNames.add("foot_l"); partNames.add("foot_r");

         */

        //Pairs of corresponding figures in "start" frame and "end" frame
        ArrayList<Pair<Figure, Figure>> pairs = new ArrayList<Pair<Figure, Figure>>();

        for (Figure figure : startFrameHolder.getFigures()) {
            Pair<Figure, Figure> added = new Pair<>(figure,
                    endFrameHolder.getFigureByName(figure));
            pairs.add(added);
        }

        ArrayList<
                Pair<
                     String,                            //the name of the figure
                     ArrayList< Pair<Double, Double> >  //variable named "addedValue"
                    >
                 > info = new ArrayList<>();

        for (int pairIndex = 0; pairIndex < pairs.size(); pairIndex++) {

            Pair<Figure, Figure> pair = pairs.get(pairIndex);

            ArrayList< Pair<Double, Double> > addedValue = new ArrayList<>();

            for (int jointIndex = 0; jointIndex < 11; jointIndex++) {
                Pair<Double, Double> toBeAddedInto = new Pair<Double, Double>(
                        pair.second.getJoints().get(jointIndex).getXCoordinate()
                                - pair.first.getJoints().get(jointIndex).getXCoordinate(),

                        pair.second.getJoints().get(jointIndex).getYCoordinate()
                                - pair.first.getJoints().get(jointIndex).getYCoordinate()
                );

                addedValue.add(toBeAddedInto);

                Figure consideredFigure = pair.first;  //The figure in the "start" frame
                Figure targetFigure     = pair.second; //The corresponding figure in the "end" frame
                Joint approachingJoint  = consideredFigure.getJoints().get(jointIndex);

                double x = toBeAddedInto.first + approachingJoint.getXCoordinate();
                double y = toBeAddedInto.second + approachingJoint.getYCoordinate();

                consideredFigure.moveMan(approachingJoint, x, y);

                pair = new Pair<Figure, Figure>(consideredFigure, targetFigure);
            }

            info.add(new Pair<
                              String,                            //the name of the figure
                              ArrayList< Pair<Double, Double> >  //variable named "addedValue"
                             >(pair.first.getName(), addedValue));
        }

        ArrayList<Frame> result = new ArrayList<Frame>();

        for (int frameIndex = 0; frameIndex <= number; frameIndex++) {
            Frame startCloneFrame = this.createCopyOf(start);

            for (int figureIndex = 0; figureIndex < startCloneFrame.getFigures().size(); figureIndex++) {
                Figure movingFigure = startCloneFrame.getFigures().get(figureIndex);
                ArrayList<Pair<Double, Double>> figureInfo = this.findFigureByName(info, movingFigure.getName());

                for (int pairIndex = 0; pairIndex < 11; pairIndex++) {

                    Joint movedJoint = movingFigure.getJoints().get(pairIndex);

                    double mousePosX = movedJoint.getXCoordinate()
                            + 1.0 * frameIndex * figureInfo.get(pairIndex).first/number;

                    double mousePosY = movedJoint.getYCoordinate()
                            + 1.0 * frameIndex * figureInfo.get(pairIndex).second/number;

                    movingFigure.moveMan(movedJoint, mousePosX, mousePosY);
                }
                startCloneFrame.setFigure(figureIndex, movingFigure);
            }

            result.add(startCloneFrame);

        }

        result.add(createCopyOf(end));
        return result;
    }

    private ArrayList< Pair<Double, Double> > findFigureByName(ArrayList<
                                                            Pair<
                                                                 String,
                                                                 ArrayList< Pair<Double, Double> >
                                                                >
                                                                        > info, String matchingName) {
        for (Pair<
                  String,
                  ArrayList< Pair<Double, Double> >

                 > pair : info) {
            if (pair.first.equals(matchingName)) return pair.second;
        }
        return new ArrayList<>();
    }


    public int findFrame(Frame frame) {
        for (int i=0; i < this.frames.size(); i++) {
            Frame fr = this.frames.get(i);
            if (fr.equals(frame)) return i;
        }
        return -1;
    }

}
