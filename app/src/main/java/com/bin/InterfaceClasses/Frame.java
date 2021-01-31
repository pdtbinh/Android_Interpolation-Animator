package com.bin.test_11;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Frame {

    private Bitmap bitmapBackground;

    private ArrayList<Figure> figures;

    public Frame() {
        this.figures = new ArrayList<Figure>();
    }

    public ArrayList<Figure> getFigures() {
        return figures;
    }

    public void addFigure(Figure figure) {
        this.figures.add(figure);
    }

    public void deleteFigure(Figure figure) {
        this.figures.remove(figure);
    }

    public void setBitmapBackground(Bitmap bitmapBackground) {
        this.bitmapBackground = bitmapBackground;
    }

    public Bitmap getBitmapBackground() {
        return bitmapBackground;
    }

    public ArrayList<String> getAllFiguresNames() {
        ArrayList<String> result = new ArrayList<String>();
        for (Figure figure: this.getFigures()) {
            result.add(figure.getName());
        }
        return result;
    }

    public Figure getFigureByName(Figure wantedFigure) {
        for (Figure figure : this.getFigures()) {
            if (figure.getName().equals(wantedFigure.getName())) return figure;
        }
        return wantedFigure;
    }

    public void setFigure(int position, Figure figure) {
        this.figures.set(position, figure);
    }

    public int findFigure(Figure figure) {
        for (int index=0; index < this.figures.size(); index++) {
            if (this.figures.get(index).equals(figure)) return index;
        }
        return -1;
    }

    public void clearFigure() {
        this.figures.clear();
        this.bitmapBackground = null;
    }



}
