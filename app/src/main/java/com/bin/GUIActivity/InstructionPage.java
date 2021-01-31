package com.bin.test_11;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class InstructionPage extends View {

    int page = 1;
    Paint p = new Paint();
    Bitmap instructionPage;
    Bitmap backgroundPage;
    float screenWidth;
    float screenHeight;

    public InstructionPage(Context context, float inputScreenWidth, float inputScreenHeight) {
        super(context);
        this.screenWidth = inputScreenWidth;
        this.screenHeight = inputScreenHeight;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        if (page == 1) {
            instructionPage = BitmapFactory.decodeResource(getResources(), R.drawable.instruction_p1);
            backgroundPage = BitmapFactory.decodeResource(getResources(), R.drawable.background_p1);
        } else if (page == 2) {
            instructionPage = BitmapFactory.decodeResource(getResources(), R.drawable.instruction_p2);
            backgroundPage = BitmapFactory.decodeResource(getResources(), R.drawable.background_p2);
        } else if (page == 3) {
            instructionPage = BitmapFactory.decodeResource(getResources(), R.drawable.instruction_p3);
            backgroundPage = BitmapFactory.decodeResource(getResources(), R.drawable.background_p2);
        } else if (page == 4) {
            instructionPage = BitmapFactory.decodeResource(getResources(), R.drawable.instruction_p4);
            backgroundPage = BitmapFactory.decodeResource(getResources(), R.drawable.background_p2);
        } else if (page == 5) {
            instructionPage = BitmapFactory.decodeResource(getResources(), R.drawable.instruction_p5);
            backgroundPage = BitmapFactory.decodeResource(getResources(), R.drawable.background_p2);
        } else if (page == 6) {
            instructionPage = BitmapFactory.decodeResource(getResources(), R.drawable.instruction_p6);
            backgroundPage = BitmapFactory.decodeResource(getResources(), R.drawable.background_p3);
        }

        instructionPage = Bitmap.createScaledBitmap(instructionPage, (int) screenWidth, (int) screenHeight, true);
        backgroundPage = Bitmap.createScaledBitmap(backgroundPage, (int) screenWidth, (int) screenHeight, true);

        //Used for instruction
        @SuppressLint("DrawAllocation") Rect rectSrc = new Rect(0, 0, instructionPage.getWidth(), instructionPage.getHeight());

        //Used for background
        @SuppressLint("DrawAllocation") Rect rectSrc2 = new Rect(0, 0, backgroundPage.getWidth(), backgroundPage.getHeight());

        //Used for both
        @SuppressLint("DrawAllocation") Rect rectFr = new Rect(0, 0, getWidth(), getHeight());

        //Add background
        p.setColor(Color.argb(230, 200, 200, 200));
        canvas.drawBitmap(backgroundPage, rectSrc2, rectFr, p);

        //Add instruction
        p.setColor(Color.argb(255, 0, 0, 0));
        canvas.drawBitmap(instructionPage, rectSrc, rectFr, p);
    }
}
