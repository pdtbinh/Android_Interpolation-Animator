package com.bin.test_11;



import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;
import java.io.FileNotFoundException;
import java.util.ArrayList;



public class MainActivity extends AppCompatActivity {



    //_____________________________________________________________________________________________
    //List canvas component, each component represents each frame
    public class FrameListComponent extends View {

        Frame miniHostFrame;
        Paint p = new Paint();

        public FrameListComponent(Context context, Frame inputFrame) {
            super(context);
            this.miniHostFrame = inputFrame;
        }

        public Frame getMiniHostFrame() {
            return miniHostFrame;
        }

        public void setMiniHostFrame(Frame miniHostFrame) {
            this.miniHostFrame = miniHostFrame;
        }

        //Appearance on screen
        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            setMeasuredDimension((int) screenWidth * 25/100, (int) screenHeight * 25/100);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            // x coordinate: 1/4
            float xRatio = (float) (0.25);

            // y coordinate: 1/4
            float yRatio = (float) (0.25);

            float frameDistance = 0;
            float initialStepBack = 0;

            //Start drawing figures given the frame has at least 1 figure
            if (miniHostFrame.getFigures().size() > 0) {

                for (Figure figure : miniHostFrame.getFigures()) {

                    // * xRatio + 30
                    // * yRatio + frameDistance + initialStepBack


                    //Draw the human stick figure
                    if (figure.getType().equals("Stick Man")) {

                        //===================== Drawing the sticks ================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Stick stick : figure.getSticks()) {
                            float x1 = (float) stick.getStart().getXCoordinate() * xRatio;
                            float y1 = (float) stick.getStart().getYCoordinate() * yRatio;

                            float x2 = (float) stick.getEnd().getXCoordinate() * xRatio;
                            float y2 = (float) stick.getEnd().getYCoordinate() * yRatio;

                            canvas.drawLine(x1, y1, x2, y2, p);
                        }



                        //=========================== Draw the head ===============================

                        // - Setting coordinates for the head
                        float xHair = 0;
                        float yHair = 0;

                        float xNeck = 0;
                        float yNeck = 0;

                        for (Joint joint : figure.getJoints()) {

                            if (joint.getName().equals("head")) {
                                xHair = (float) joint.getXCoordinate() * xRatio;
                                yHair = (float) joint.getYCoordinate() * yRatio;
                            }

                            if (joint.getName().equals("neck")) {
                                xNeck = (float) joint.getXCoordinate() * xRatio;
                                yNeck = (float) joint.getYCoordinate() * yRatio;
                            }
                        }

                        float xHead = (xHair + xNeck)/2;
                        float yHead = (yHair + yNeck)/2;

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.STROKE);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(xHead, yHead, 15, p);



                        //===================== Drawing the joints ================================
                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Joint joint : figure.getJoints()) {
                            float x = (float) joint.getXCoordinate() * xRatio;
                            float y = (float) joint.getYCoordinate() * yRatio;
                            canvas.drawCircle(x, y, 6, p);
                        }

                    }

                    //Draw Spider-man stick figure
                    else if (figure.getType().equals("Ninja Warrior")) {

                        //==================== Drawing the sticks ============================

                        for (Stick stick : figure.getSticks()) {
                            float x1 = (float) stick.getStart().getXCoordinate() * xRatio;
                            float y1 = (float) stick.getStart().getYCoordinate() * yRatio;

                            float x2 = (float) stick.getEnd().getXCoordinate() * xRatio;
                            float y2 = (float) stick.getEnd().getYCoordinate() * yRatio;

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setStyle(Paint.Style.STROKE);
                            if (stick.getName().equals("knee_l") || stick.getName().equals("knee_r") ||
                                    stick.getName().equals("arm_l") || stick.getName().equals("arm_r") ||
                                    stick.getName().equals("body")) {
                                p.setColor(Color.BLACK);
                            } else {
                                p.setColor(Color.RED);
                            }
                            if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                            p.setStrokeWidth(6);
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            canvas.drawLine(x1, y1, x2, y2, p);
                        }

                        //======================== Draw the head ================================

                        // - Setting coordinates for the head
                        float xHair = 0;
                        float yHair = 0;

                        float xNeck = 0;
                        float yNeck = 0;

                        for (Joint joint : figure.getJoints()) {

                            if (joint.getName().equals("head")) {
                                xHair = (float) joint.getXCoordinate() * xRatio;
                                yHair = (float) joint.getYCoordinate() * yRatio;
                            }

                            if (joint.getName().equals("neck")) {
                                xNeck = (float) joint.getXCoordinate() * xRatio;
                                yNeck = (float) joint.getYCoordinate() * yRatio;
                            }
                        }

                        float xHead = (xHair + xNeck) / 2;
                        float yHead = (yHair + yNeck) / 2;


                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.lace_5.getXCoordinate() * xRatio,
                                (float) figure.lace_5.getYCoordinate() * yRatio,
                                (float) figure.lace_3.getXCoordinate() * xRatio,
                                (float) figure.lace_3.getYCoordinate() * yRatio, p);

                        canvas.drawLine((float) figure.lace_5.getXCoordinate() * xRatio,
                                (float) figure.lace_5.getYCoordinate() * yRatio,
                                (float) figure.lace_4.getXCoordinate() * xRatio,
                                (float) figure.lace_4.getYCoordinate() * yRatio, p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(xHead, yHead, 20, p);



                        //========================== Draw the eyes ===============================
                        Path path_l = new Path();

                        path_l.setFillType(Path.FillType.EVEN_ODD);
                        path_l.moveTo((float) figure.eye_l_1.getXCoordinate() * xRatio,
                                (float) figure.eye_l_1.getYCoordinate() * yRatio);
                        path_l.lineTo((float) figure.eye_l_2.getXCoordinate() * xRatio,
                                (float) figure.eye_l_2.getYCoordinate() * yRatio);
                        path_l.lineTo((float) figure.eye_l_3.getXCoordinate() * xRatio,
                                (float) figure.eye_l_3.getYCoordinate() * yRatio);

                        path_l.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.WHITE);
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_l, p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.lace_1.getXCoordinate() * xRatio,
                                (float) figure.lace_1.getYCoordinate() * yRatio,
                                (float) figure.lace_2.getXCoordinate() * xRatio,
                                (float) figure.lace_2.getYCoordinate() * yRatio, p);


                        //=========================== Drawing the joints ===========================
                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Joint joint : figure.getJoints()) {
                            if (!joint.getName().equals("eye_l_1") && !joint.getName().equals("eye_l_2") && !joint.getName().equals("eye_l_3")
                                    && !joint.getName().equals("lace_1") && !joint.getName().equals("lace_2") && !joint.getName().equals("lace_3")
                                    && !joint.getName().equals("lace_4") && !joint.getName().equals("lace_5")) {
                                float x = (float) joint.getXCoordinate() * xRatio;
                                float y = (float) joint.getYCoordinate() * yRatio;
                                canvas.drawCircle(x, y, 6, p);
                            }
                        }
                    }

                    //Draw dark warrior stick figure
                    else if (figure.getType().equals("Dark Warrior")) {



                        //======================== Drawing the sticks =============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Stick stick : figure.getSticks()) {
                            float x1 = (float) stick.getStart().getXCoordinate() * xRatio;
                            float y1 = (float) stick.getStart().getYCoordinate() * yRatio;

                            float x2 = (float) stick.getEnd().getXCoordinate() * xRatio;
                            float y2 = (float) stick.getEnd().getYCoordinate() * yRatio;

                            canvas.drawLine(x1, y1, x2, y2, p);
                        }



                        //========================== Draw the head ================================

                        // - Setting coordinates for the head
                        float xHair = 0;
                        float yHair = 0;

                        float xNeck = 0;
                        float yNeck = 0;

                        for (Joint joint : figure.getJoints()) {

                            if (joint.getName().equals("head")) {
                                xHair = (float) joint.getXCoordinate() * xRatio;
                                yHair = (float) joint.getYCoordinate() * yRatio;
                            }

                            if (joint.getName().equals("neck")) {
                                xNeck = (float) joint.getXCoordinate() * xRatio;
                                yNeck = (float) joint.getYCoordinate() * yRatio;
                            }
                        }

                        float xHead = (xHair + xNeck)/2;
                        float yHead = (yHair + yNeck)/2;

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(xHead, yHead, 15, p);



                        //========================== Draw the eyes ================================
                        Path path_l = new Path();

                        path_l.setFillType(Path.FillType.EVEN_ODD);

                        path_l.moveTo((float) figure.eye_l_1.getXCoordinate() * xRatio,
                                (float) figure.eye_l_1.getYCoordinate() * yRatio);

                        path_l.lineTo((float) figure.eye_l_2.getXCoordinate() * xRatio,
                                (float) figure.eye_l_2.getYCoordinate() * yRatio);

                        path_l.lineTo((float) figure.eye_l_3.getXCoordinate() * xRatio,
                                (float) figure.eye_l_3.getYCoordinate() * yRatio);

                        path_l.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.WHITE);
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_l, p);



                        //============================= Draw the ears =============================
                        Path path_r = new Path();

                        path_r.setFillType(Path.FillType.EVEN_ODD);

                        path_r.moveTo((float) figure.ear_l_1.getXCoordinate() * xRatio,
                                (float) figure.ear_l_1.getYCoordinate() * yRatio);

                        path_r.lineTo((float) figure.ear_l_2.getXCoordinate() * xRatio,
                                (float) figure.ear_l_2.getYCoordinate() * yRatio);

                        path_r.lineTo((float) figure.ear_l_3.getXCoordinate() * xRatio,
                                (float) figure.ear_l_3.getYCoordinate() * yRatio);

                        path_r.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_r, p);



                        //========================= Drawing the joints ============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLUE);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Joint joint : figure.getJoints()) {
                            if (!joint.getName().equals("eye_l_1") && !joint.getName().equals("eye_l_2") && !joint.getName().equals("eye_l_3")
                                    && !joint.getName().equals("ear_l_1") && !joint.getName().equals("ear_l_2") && !joint.getName().equals("ear_l_3")) {
                                float x = (float) joint.getXCoordinate() * xRatio;
                                float y = (float) joint.getYCoordinate() * yRatio;
                                canvas.drawCircle(x, y, 6, p);
                            }
                        }

                    }

                    //Drawing the sword
                    else if (figure.getType().equals("Sword")) {
                        Path path_1 = new Path();

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.tool_1.getXCoordinate() * xRatio,
                                (float) figure.tool_1.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_2.getXCoordinate() * xRatio,
                                (float) figure.tool_2.getYCoordinate() * yRatio);

                        path_1.lineTo((float) figure.tool_4.getXCoordinate() * xRatio,
                                (float) figure.tool_4.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_3.getXCoordinate() * xRatio,
                                (float) figure.tool_3.getYCoordinate() * yRatio);

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);



                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.tool_1.getXCoordinate() * xRatio,
                                (float) figure.tool_1.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_2.getXCoordinate() * xRatio,
                                (float) figure.tool_2.getYCoordinate() * yRatio);

                        path_2.lineTo((float) figure.tool_6.getXCoordinate() * xRatio,
                                (float) figure.tool_6.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_5.getXCoordinate() * xRatio,
                                (float) figure.tool_5.getYCoordinate() * yRatio);

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);




                        Path path_3 = new Path();

                        path_3.setFillType(Path.FillType.EVEN_ODD);
                        path_3.moveTo((float) figure.tool_7.getXCoordinate() * xRatio,
                                (float) figure.tool_7.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_8.getXCoordinate() * xRatio,
                                (float) figure.tool_8.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_9.getXCoordinate() * xRatio,
                                (float) figure.tool_9.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_10.getXCoordinate() * xRatio,
                                (float) figure.tool_10.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_11.getXCoordinate() * xRatio,
                                (float) figure.tool_11.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_12.getXCoordinate() * xRatio,
                                (float) figure.tool_12.getYCoordinate() * yRatio);

                        path_3.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_3, p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.DKGRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(10);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio,
                                (float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio,
                                p);



                        //========================= Draw the joints ===============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio, 6, p);

                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio, 6, p);
                    }

                    //Draw the gun
                    else if (figure.getType().equals("Gun")) {

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(8);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_41.getXCoordinate() * xRatio,
                                (float) figure.tool_41.getYCoordinate() * yRatio,
                                (float) figure.tool_42.getXCoordinate() * xRatio,
                                (float) figure.tool_42.getYCoordinate() * yRatio, p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_43.getXCoordinate() * xRatio,
                                (float) figure.tool_43.getYCoordinate() * yRatio,
                                (float) figure.tool_44.getXCoordinate() * xRatio,
                                (float) figure.tool_44.getYCoordinate() * yRatio, p);



                        Path path_1 = new Path();

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.tool_1.getXCoordinate() * xRatio,
                                (float) figure.tool_1.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_2.getXCoordinate() * xRatio,
                                (float) figure.tool_2.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_3.getXCoordinate() * xRatio,
                                (float) figure.tool_3.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_4.getXCoordinate() * xRatio,
                                (float) figure.tool_4.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_5.getXCoordinate() * xRatio,
                                (float) figure.tool_5.getYCoordinate() * yRatio);

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);



                        //=========================== The amo part ================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(12);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine( (float) figure.tool_36.getXCoordinate() * xRatio,
                                (float) figure.tool_36.getYCoordinate() * yRatio,
                                (float) figure.tool_37.getXCoordinate() * xRatio,
                                (float) figure.tool_37.getYCoordinate() * yRatio, p);



                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.tool_6.getXCoordinate() * xRatio,
                                (float) figure.tool_6.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_7.getXCoordinate() * xRatio,
                                (float) figure.tool_7.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_8.getXCoordinate() * xRatio,
                                (float) figure.tool_8.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_9.getXCoordinate() * xRatio,
                                (float) figure.tool_9.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_10.getXCoordinate() * xRatio,
                                (float) figure.tool_10.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_11.getXCoordinate() * xRatio,
                                (float) figure.tool_11.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_12.getXCoordinate() * xRatio,
                                (float) figure.tool_12.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_13.getXCoordinate() * xRatio,
                                (float) figure.tool_13.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_14.getXCoordinate() * xRatio,
                                (float) figure.tool_14.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_15.getXCoordinate() * xRatio,
                                (float) figure.tool_15.getYCoordinate() * yRatio);

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);



                        Path path_3 = new Path();

                        path_3.setFillType(Path.FillType.EVEN_ODD);
                        path_3.moveTo((float) figure.tool_16.getXCoordinate() * xRatio,
                                (float) figure.tool_16.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_17.getXCoordinate() * xRatio,
                                (float) figure.tool_17.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_18.getXCoordinate() * xRatio,
                                (float) figure.tool_18.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_19.getXCoordinate() * xRatio,
                                (float) figure.tool_19.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_20.getXCoordinate() * xRatio,
                                (float) figure.tool_20.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_21.getXCoordinate() * xRatio,
                                (float) figure.tool_21.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_22.getXCoordinate() * xRatio,
                                (float) figure.tool_22.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_23.getXCoordinate() * xRatio,
                                (float) figure.tool_23.getYCoordinate() * yRatio);

                        path_3.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_3, p);



                        Path path_4 = new Path();

                        path_4.setFillType(Path.FillType.EVEN_ODD);
                        path_4.moveTo((float) figure.tool_24.getXCoordinate() * xRatio,
                                (float) figure.tool_24.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.tool_25.getXCoordinate() * xRatio,
                                (float) figure.tool_25.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.tool_26.getXCoordinate() * xRatio,
                                (float) figure.tool_26.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.tool_27.getXCoordinate() * xRatio,
                                (float) figure.tool_27.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.tool_28.getXCoordinate() * xRatio,
                                (float) figure.tool_28.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.tool_29.getXCoordinate() * xRatio,
                                (float) figure.tool_29.getYCoordinate() * yRatio);

                        path_4.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_4, p);



                        Path path_5 = new Path();

                        path_5.setFillType(Path.FillType.EVEN_ODD);
                        path_5.moveTo((float) figure.tool_14.getXCoordinate() * xRatio,
                                (float) figure.tool_14.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.tool_15.getXCoordinate() * xRatio,
                                (float) figure.tool_15.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.tool_38.getXCoordinate() * xRatio,
                                (float) figure.tool_38.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.tool_39.getXCoordinate() * xRatio,
                                (float) figure.tool_39.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.tool_40.getXCoordinate() * xRatio,
                                (float) figure.tool_40.getYCoordinate() * yRatio);

                        path_5.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_5, p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine( (float) figure.tool_33.getXCoordinate() * xRatio,
                                (float) figure.tool_33.getYCoordinate() * yRatio,
                                (float) figure.tool_34.getXCoordinate() * xRatio,
                                (float) figure.tool_34.getYCoordinate() * yRatio, p);

                        canvas.drawLine( (float) figure.tool_34.getXCoordinate() * xRatio,
                                (float) figure.tool_34.getYCoordinate() * yRatio,
                                (float) figure.tool_35.getXCoordinate() * xRatio,
                                (float) figure.tool_35.getYCoordinate() * yRatio, p);

                        p.setStrokeWidth(8);
                        canvas.drawLine( (float) figure.tool_30.getXCoordinate() * xRatio,
                                (float) figure.tool_30.getYCoordinate() * yRatio,
                                (float) figure.tool_31.getXCoordinate() * xRatio,
                                (float) figure.tool_31.getYCoordinate() * yRatio, p);

                        p.setStrokeWidth(12);
                        canvas.drawLine( (float) figure.tool_31.getXCoordinate() * xRatio,
                                (float) figure.tool_31.getYCoordinate() * yRatio,
                                (float) figure.tool_32.getXCoordinate() * xRatio,
                                (float) figure.tool_32.getYCoordinate() * yRatio, p);



                        //=========================== Draw the joints =============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLUE);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio, 6, p);
                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio, 6, p);
                    }

                    //Draw the trident
                    else if (figure.getType().equals("Trident")) {

                        //========================= Drawing shadow ====================================


                        //========================= Draw the body

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_13.getXCoordinate() * xRatio,
                                (float) figure.tool_13.getYCoordinate() * yRatio,
                                (float) figure.tool_14.getXCoordinate() * xRatio,
                                (float) figure.tool_14.getYCoordinate() * yRatio, p);

                        //========================= Draw the top steel part
                        canvas.drawLine((float) figure.tool_15.getXCoordinate() * xRatio,
                                (float) figure.tool_15.getYCoordinate() * yRatio,
                                (float) figure.tool_16.getXCoordinate() * xRatio,
                                (float) figure.tool_16.getYCoordinate() * yRatio, p);

                        canvas.drawLine((float) figure.tool_17.getXCoordinate() * xRatio,
                                (float) figure.tool_17.getYCoordinate() * yRatio,
                                (float) figure.tool_18.getXCoordinate() * xRatio,
                                (float) figure.tool_18.getYCoordinate() * yRatio, p);



                        //========================= Draw the top stab part
                        Path path_4 = new Path();

                        path_4.setFillType(Path.FillType.EVEN_ODD);
                        path_4.moveTo((float) figure.tool_27.getXCoordinate() * xRatio,
                                (float) figure.tool_27.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.tool_28.getXCoordinate() * xRatio,
                                (float) figure.tool_28.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.tool_38.getXCoordinate() * xRatio,
                                (float) figure.tool_38.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.tool_37.getXCoordinate() * xRatio,
                                (float) figure.tool_37.getYCoordinate() * yRatio);

                        path_4.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
                        canvas.drawPath(path_4, p);



                        //========================= Draw the middle steel part

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_19.getXCoordinate() * xRatio,
                                (float) figure.tool_19.getYCoordinate() * yRatio,
                                (float) figure.tool_20.getXCoordinate() * xRatio,
                                (float) figure.tool_20.getYCoordinate() * yRatio, p);



                        //========================= Draw the middle stab part
                        Path path_5 = new Path();

                        path_5.setFillType(Path.FillType.EVEN_ODD);
                        path_5.moveTo((float) figure.tool_31.getXCoordinate() * xRatio,
                                (float) figure.tool_31.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.tool_32.getXCoordinate() * xRatio,
                                (float) figure.tool_32.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.tool_40.getXCoordinate() * xRatio,
                                (float) figure.tool_40.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.tool_39.getXCoordinate() * xRatio,
                                (float) figure.tool_39.getYCoordinate() * yRatio);

                        path_5.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_5, p);



                        //========================= Draw the bottom steel part

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_21.getXCoordinate() * xRatio,
                                (float) figure.tool_21.getYCoordinate() * yRatio,
                                (float) figure.tool_22.getXCoordinate() * xRatio,
                                (float) figure.tool_22.getYCoordinate() * yRatio, p);

                        canvas.drawLine((float) figure.tool_23.getXCoordinate() * xRatio,
                                (float) figure.tool_23.getYCoordinate() * yRatio,
                                (float) figure.tool_24.getXCoordinate() * xRatio,
                                (float) figure.tool_24.getYCoordinate() * yRatio, p);



                        //========================= Draw the bottom stab part
                        Path path_6 = new Path();

                        path_6.setFillType(Path.FillType.EVEN_ODD);
                        path_6.moveTo((float) figure.tool_35.getXCoordinate() * xRatio,
                                (float) figure.tool_35.getYCoordinate() * yRatio);
                        path_6.lineTo((float) figure.tool_36.getXCoordinate() * xRatio,
                                (float) figure.tool_36.getYCoordinate() * yRatio);
                        path_6.lineTo((float) figure.tool_42.getXCoordinate() * xRatio,
                                (float) figure.tool_42.getYCoordinate() * yRatio);
                        path_6.lineTo((float) figure.tool_41.getXCoordinate() * xRatio,
                                (float) figure.tool_41.getYCoordinate() * yRatio);

                        path_6.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_6, p);



                        //========================= Draw the tail part
                        Path path_8 = new Path();

                        path_8.setFillType(Path.FillType.EVEN_ODD);
                        path_8.moveTo((float) figure.tool_46.getXCoordinate() * xRatio,
                                (float) figure.tool_46.getYCoordinate() * yRatio);
                        path_8.lineTo((float) figure.tool_45.getXCoordinate() * xRatio,
                                (float) figure.tool_45.getYCoordinate() * yRatio);
                        path_8.lineTo((float) figure.tool_44.getXCoordinate() * xRatio,
                                (float) figure.tool_44.getYCoordinate() * yRatio);
                        path_8.lineTo((float) figure.tool_47.getXCoordinate() * xRatio,
                                (float) figure.tool_47.getYCoordinate() * yRatio);
                        path_8.lineTo((float) figure.tool_48.getXCoordinate() * xRatio,
                                (float) figure.tool_48.getYCoordinate() * yRatio);
                        path_8.lineTo((float) figure.tool_49.getXCoordinate() * xRatio,
                                (float) figure.tool_49.getYCoordinate() * yRatio);

                        path_8.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_8, p);



                        //=========================== Draw real part ==============================


                        //========================= Draw the body

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_1.getXCoordinate() * xRatio,
                                (float) figure.tool_1.getYCoordinate() * yRatio,
                                (float) figure.tool_2.getXCoordinate() * xRatio,
                                (float) figure.tool_2.getYCoordinate() * yRatio, p);



                        //========================= Draw the top steel part
                        canvas.drawLine((float) figure.tool_3.getXCoordinate() * xRatio,
                                (float) figure.tool_3.getYCoordinate() * yRatio,
                                (float) figure.tool_4.getXCoordinate() * xRatio,
                                (float) figure.tool_4.getYCoordinate() * yRatio, p);

                        canvas.drawLine((float) figure.tool_5.getXCoordinate() * xRatio,
                                (float) figure.tool_5.getYCoordinate() * yRatio,
                                (float) figure.tool_6.getXCoordinate() * xRatio,
                                (float) figure.tool_6.getYCoordinate() * yRatio, p);



                        //========================= Draw the top stab part
                        Path path_1 = new Path();

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.tool_25.getXCoordinate() * xRatio,
                                (float) figure.tool_25.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_26.getXCoordinate() * xRatio,
                                (float) figure.tool_26.getYCoordinate() * yRatio);

                        path_1.lineTo((float) figure.tool_27.getXCoordinate() * xRatio,
                                (float) figure.tool_27.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_28.getXCoordinate() * xRatio,
                                (float) figure.tool_28.getYCoordinate() * yRatio);

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);



                        //========================= Draw the middle steel part

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_7.getXCoordinate() * xRatio,
                                (float) figure.tool_7.getYCoordinate() * yRatio,
                                (float) figure.tool_8.getXCoordinate() * xRatio,
                                (float) figure.tool_8.getYCoordinate() * yRatio, p);



                        //========================= Draw the middle stab part
                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.tool_29.getXCoordinate() * xRatio,
                                (float) figure.tool_29.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_30.getXCoordinate() * xRatio,
                                (float) figure.tool_30.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_31.getXCoordinate() * xRatio,
                                (float) figure.tool_31.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_32.getXCoordinate() * xRatio,
                                (float) figure.tool_32.getYCoordinate() * yRatio);

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);



                        //========================= Draw the bottom steel part

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_9.getXCoordinate() * xRatio,
                                (float) figure.tool_9.getYCoordinate() * yRatio,
                                (float) figure.tool_10.getXCoordinate() * xRatio,
                                (float) figure.tool_10.getYCoordinate() * yRatio, p);

                        canvas.drawLine((float) figure.tool_11.getXCoordinate() * xRatio,
                                (float) figure.tool_11.getYCoordinate() * yRatio,
                                (float) figure.tool_12.getXCoordinate() * xRatio,
                                (float) figure.tool_12.getYCoordinate() * yRatio, p);



                        //========================= Draw the bottom stab part
                        Path path_3 = new Path();

                        path_3.setFillType(Path.FillType.EVEN_ODD);
                        path_3.moveTo((float) figure.tool_33.getXCoordinate() * xRatio,
                                (float) figure.tool_33.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_34.getXCoordinate() * xRatio,
                                (float) figure.tool_34.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_35.getXCoordinate() * xRatio,
                                (float) figure.tool_35.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_36.getXCoordinate() * xRatio,
                                (float) figure.tool_36.getYCoordinate() * yRatio);

                        path_3.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_3, p);



                        //========================= Draw the tail part
                        Path path_7 = new Path();

                        path_7.setFillType(Path.FillType.EVEN_ODD);
                        path_7.moveTo((float) figure.tool_46.getXCoordinate() * xRatio,
                                (float) figure.tool_46.getYCoordinate() * yRatio);
                        path_7.lineTo((float) figure.tool_43.getXCoordinate() * xRatio,
                                (float) figure.tool_43.getYCoordinate() * yRatio);
                        path_7.lineTo((float) figure.tool_44.getXCoordinate() * xRatio,
                                (float) figure.tool_44.getYCoordinate() * yRatio);
                        path_7.lineTo((float) figure.tool_45.getXCoordinate() * xRatio,
                                (float) figure.tool_45.getYCoordinate() * yRatio);

                        path_7.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_7, p);


                        //=========================== Draw the joints =============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio, 6, p);
                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio, 6, p);
                    }

                    //Draw the wooden shield
                    else if (figure.getType().equals("Wooden Shield")) {
                        //========================== The outer circle =============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(20);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(
                                (float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio,
                                33, p);



                        //========================== The inner circle =============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setStyle(Paint.Style.FILL);
                        p.setColor(Color.argb(255, 236, 213, 167));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(
                                (float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio,
                                30, p);



                        //=============================== Scratches ===============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setStyle(Paint.Style.STROKE);
                        p.setColor(Color.BLACK);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStrokeWidth(1);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_1.getXCoordinate() * xRatio,
                                (float) figure.tool_1.getYCoordinate() * yRatio,
                                (float) figure.tool_2.getXCoordinate() * xRatio,
                                (float) figure.tool_2.getYCoordinate() * yRatio, p);

                        canvas.drawLine((float) figure.tool_3.getXCoordinate() * xRatio,
                                (float) figure.tool_3.getYCoordinate() * yRatio,
                                (float) figure.tool_4.getXCoordinate() * xRatio,
                                (float) figure.tool_4.getYCoordinate() * yRatio, p);

                        canvas.drawLine((float) figure.tool_5.getXCoordinate() * xRatio,
                                (float) figure.tool_5.getYCoordinate() * yRatio,
                                (float) figure.tool_6.getXCoordinate() * xRatio,
                                (float) figure.tool_6.getYCoordinate() * yRatio, p);


                        //========================== The arrow body part ==========================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 93, 67, 44));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStrokeWidth(5);
                        p.setStyle(Paint.Style.STROKE);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_10.getXCoordinate() * xRatio,
                                (float) figure.tool_10.getYCoordinate() * yRatio,
                                (float) figure.tool_12.getXCoordinate() * xRatio,
                                (float) figure.tool_12.getYCoordinate() * yRatio, p);



                        //====================== Draw the steel arrow part ========================
                        Path path_1 = new Path();

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.tool_8.getXCoordinate() * xRatio,
                                (float) figure.tool_8.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_7.getXCoordinate() * xRatio,
                                (float) figure.tool_7.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_9.getXCoordinate() * xRatio,
                                (float) figure.tool_9.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_10.getXCoordinate() * xRatio,
                                (float) figure.tool_10.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_11.getXCoordinate() * xRatio,
                                (float) figure.tool_11.getYCoordinate() * yRatio);

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 219, 228, 235));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);



                        //================ Draw the steel arrow part surrounding ==================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setStrokeWidth(2);
                        p.setColor(Color.BLACK);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);

                        canvas.drawLine((float) figure.tool_13.getXCoordinate() * xRatio,
                                (float) figure.tool_13.getYCoordinate() * yRatio,
                                (float) figure.tool_14.getXCoordinate() * xRatio,
                                (float) figure.tool_14.getYCoordinate() * yRatio, p);


                        //============================ The arrow tail =============================
                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.tool_12.getXCoordinate() * xRatio,
                                (float) figure.tool_12.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_15.getXCoordinate() * xRatio,
                                (float) figure.tool_15.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_16.getXCoordinate() * xRatio,
                                (float) figure.tool_16.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_17.getXCoordinate() * xRatio,
                                (float) figure.tool_17.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_18.getXCoordinate() * xRatio,
                                (float) figure.tool_18.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_19.getXCoordinate() * xRatio,
                                (float) figure.tool_19.getYCoordinate() * yRatio);

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);


                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setStrokeWidth(2);
                        p.setColor(Color.BLACK);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);



                        //=========================== Draw the joints =============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLUE);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio, 6, p);
                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio, 6, p);

                        //Inactive joints:

                        p.setColor(Color.LTGRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        canvas.drawCircle((float) figure.tool_20.getXCoordinate() * xRatio,
                                (float) figure.tool_20.getYCoordinate() * yRatio, 4, p);
                        canvas.drawCircle((float) figure.tool_21.getXCoordinate() * xRatio,
                                (float) figure.tool_21.getYCoordinate() * yRatio, 4, p);
                        canvas.drawCircle((float) figure.tool_22.getXCoordinate() * xRatio,
                                (float) figure.tool_22.getYCoordinate() * yRatio, 4, p);
                        canvas.drawCircle((float) figure.tool_23.getXCoordinate() * xRatio,
                                (float) figure.tool_23.getYCoordinate() * yRatio, 4, p);
                        canvas.drawCircle((float) figure.tool_24.getXCoordinate() * xRatio,
                                (float) figure.tool_24.getYCoordinate() * yRatio, 4, p);
                    }

                    //Draw metal shield
                    else if (figure.getType().equals("Metal Shield")) {

                        //============================== Shadow ===================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 190, 190, 190));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_16.getXCoordinate() * xRatio,
                                (float) figure.tool_16.getYCoordinate() * yRatio,
                                (float) figure.tool_17.getXCoordinate() * xRatio,
                                (float) figure.tool_17.getYCoordinate() * yRatio, p);

                        canvas.drawLine((float) figure.tool_18.getXCoordinate() * xRatio,
                                (float) figure.tool_18.getYCoordinate() * yRatio,
                                (float) figure.tool_19.getXCoordinate() * xRatio,
                                (float) figure.tool_19.getYCoordinate() * yRatio, p);

                        //=========================== Right - Upper ===============================
                        Path path_1 = new Path();

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_1.getXCoordinate() * xRatio,
                                (float) figure.tool_1.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_2.getXCoordinate() * xRatio,
                                (float) figure.tool_2.getYCoordinate() * yRatio);
                        path_1.quadTo((float) figure.tool_3.getXCoordinate() * xRatio,
                                (float) figure.tool_3.getYCoordinate() * yRatio,
                                (float) figure.tool_4.getXCoordinate() * xRatio,
                                (float) figure.tool_4.getYCoordinate() * yRatio);
                        path_1.lineTo((float) figure.tool_5.getXCoordinate() * xRatio,
                                (float) figure.tool_5.getYCoordinate() * yRatio);

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 190, 0, 0));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(5);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);



                        //============================ Right - Lower ==============================
                        Path path_3 = new Path();

                        path_3.setFillType(Path.FillType.EVEN_ODD);
                        path_3.moveTo((float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.tool_5.getXCoordinate() * xRatio,
                                (float) figure.tool_5.getYCoordinate() * yRatio);
                        path_3.lineTo((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio);

                        path_3.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 220, 220, 220));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(5);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_3, p);



                        //=========================== Left - Upper ================================
                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_1.getXCoordinate() * xRatio,
                                (float) figure.tool_1.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_6.getXCoordinate() * xRatio,
                                (float) figure.tool_6.getYCoordinate() * yRatio);
                        path_2.quadTo((float) figure.tool_7.getXCoordinate() * xRatio,
                                (float) figure.tool_7.getYCoordinate() * yRatio,
                                (float) figure.tool_8.getXCoordinate() * xRatio,
                                (float) figure.tool_8.getYCoordinate() * yRatio);
                        path_2.lineTo((float) figure.tool_9.getXCoordinate() * xRatio,
                                (float) figure.tool_9.getYCoordinate() * yRatio);

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 255, 255));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(5);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);



                        //============================ Left - Lower ===============================
                        Path path_4 = new Path();

                        path_4.setFillType(Path.FillType.EVEN_ODD);
                        path_4.moveTo((float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.tool_9.getXCoordinate() * xRatio,
                                (float) figure.tool_9.getYCoordinate() * yRatio);
                        path_4.lineTo((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio);

                        path_4.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 0, 0));
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(5);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_4, p);



                        //=========================== Surrounding frame ===========================
                        Path path_5 = new Path();

                        path_5.setFillType(Path.FillType.EVEN_ODD);
                        path_5.moveTo((float) figure.tool_1.getXCoordinate() * xRatio,
                                (float) figure.tool_1.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.tool_2.getXCoordinate() * xRatio,
                                (float) figure.tool_2.getYCoordinate() * yRatio);
                        path_5.quadTo((float) figure.tool_3.getXCoordinate() * xRatio,
                                (float) figure.tool_3.getYCoordinate() * yRatio,
                                (float) figure.tool_4.getXCoordinate() * xRatio,
                                (float) figure.tool_4.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio);
                        path_5.lineTo((float) figure.tool_8.getXCoordinate() * xRatio,
                                (float) figure.tool_8.getYCoordinate() * yRatio);
                        path_5.quadTo((float) figure.tool_7.getXCoordinate() * xRatio,
                                (float) figure.tool_7.getYCoordinate() * yRatio,
                                (float) figure.tool_6.getXCoordinate() * xRatio,
                                (float) figure.tool_6.getYCoordinate() * yRatio);

                        path_5.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(6);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_5, p);



                        //=========================== Draw the joints =============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLUE);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio, 6, p);
                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate() * xRatio,
                                (float) figure.movingUnit.getYCoordinate() * yRatio, 6, p);

                        //Inactive joints:
                        p.setColor(Color.LTGRAY);
                        if (figure.equals(activeFigure)) p.setColor(Color.argb(180, 60, 174, 163));
                        canvas.drawCircle((float) figure.tool_10.getXCoordinate() * xRatio,
                                (float) figure.tool_10.getYCoordinate() * yRatio, 4, p);
                        canvas.drawCircle((float) figure.tool_11.getXCoordinate() * xRatio,
                                (float) figure.tool_11.getYCoordinate() * yRatio, 4, p);
                        canvas.drawCircle((float) figure.tool_12.getXCoordinate() * xRatio,
                                (float) figure.tool_12.getYCoordinate() * yRatio, 4, p);
                        canvas.drawCircle((float) figure.tool_13.getXCoordinate() * xRatio,
                                (float) figure.tool_13.getYCoordinate() * yRatio, 4, p);
                        canvas.drawCircle((float) figure.tool_14.getXCoordinate() * xRatio,
                                (float) figure.tool_14.getYCoordinate() * yRatio, 4, p);
                        canvas.drawCircle((float) figure.tool_15.getXCoordinate() * xRatio,
                                (float) figure.tool_15.getYCoordinate() * yRatio, 4, p);

                        //=============================== Shadow ==================================
                        Path path_7 = new Path();

                        path_7.setFillType(Path.FillType.EVEN_ODD);
                        path_7.moveTo((float) figure.tool_1.getXCoordinate() * xRatio,
                                (float) figure.tool_1.getYCoordinate() * yRatio);
                        path_7.lineTo((float) figure.tool_2.getXCoordinate() * xRatio,
                                (float) figure.tool_2.getYCoordinate() * yRatio);
                        path_7.quadTo((float) figure.tool_3.getXCoordinate() * xRatio,
                                (float) figure.tool_3.getYCoordinate() * yRatio,
                                (float) figure.tool_4.getXCoordinate() * xRatio,
                                (float) figure.tool_4.getYCoordinate() * yRatio);
                        path_7.lineTo((float) figure.spinning.getXCoordinate() * xRatio,
                                (float) figure.spinning.getYCoordinate() * yRatio);

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(60, 20, 20, 20));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(5);
                        canvas.drawPath(path_7, p);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                    }
                }
            }

            p.setStyle(Paint.Style.STROKE);
            p.setStrokeWidth(20);
            p.setColor(Color.GRAY);
            if (this.miniHostFrame.equals(activeFrame)) {
                p.setColor(Color.GREEN);
            }
            canvas.drawRect(0, 0, screenWidth * 25/100, screenHeight * 25/100, p);




        } //Closing of the onDraw method

        @Override
        public boolean onTouchEvent(MotionEvent event) {

            if (!animationRunning) {
                //If one of the FrameListComponent(s) is touched, set the active frame to its mini host frame
                activeFrame = this.miniHostFrame;

                //Reset the active figure (so that at this exact moment, no figure of the window is active)
                activeFigure = new Figure("Default", "Default");

                //Then redraw the current active FrameListComponent so that it is no longer highlighted in green
                activeFrameListComponent.invalidate();

                //Then set the active FrameListComponent to this component
                activeFrameListComponent = this;

                //Redraw this component so that it is highlighted in green
                this.invalidate();

                //Set the main screen's host frame to the new active frame
                drawingCanvas.setHostFrame(activeFrame);

                //Redraw the main screen to draw the selected frame (active frame)
                drawingCanvas.invalidate();

                //Default return

            }
            return true;
        }

    }
    //FrameListComponent ends here



    //_____________________________________________________________________________________________
    //The main screen where user interacts with figures
    public class DrawingCanvas extends View {

        //Initialize variable state of instance DrawingCanvas
        Frame hostFrame;
        boolean figureNameShowed;
        Paint p = new Paint();

        public DrawingCanvas(Context context, Frame inputFrame) {
            super(context);
            this.hostFrame = inputFrame;
            figureNameShowed = false;
        }

        //Setters and Getters
        public void setHostFrame(Frame hostFrame) {
            this.hostFrame = hostFrame;
        }

        public Frame getHostFrame() {
            return hostFrame;
        }

        public boolean isFigureNameShowed() {
            return figureNameShowed;
        }

        public void setFigureNameShowed(boolean figureNameShowed) {
            this.figureNameShowed = figureNameShowed;
        }


        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
            if (this.hostFrame.equals(emptyFrameForTheme)) {
                setMeasuredDimension((int) screenWidth * 71 / 100, (int) screenHeight * 71 / 100);
            } else {
                setMeasuredDimension((int) screenWidth, (int) screenHeight);
            }
        }

        //__________________________________________________________________________________________
        //Drawing method


        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            Bitmap bitmapBackground = this.hostFrame.getBitmapBackground();
            if (bitmapBackground != null) {
                @SuppressLint("DrawAllocation") Rect rectSrc = new Rect(0, 0, bitmapBackground.getWidth(), bitmapBackground.getHeight());
                @SuppressLint("DrawAllocation") Rect rectFr = new Rect(0, 0, getWidth(), getHeight());
                p.setColor(Color.argb(255, 0, 0, 0));
                canvas.drawBitmap(bitmapBackground, rectSrc, rectFr, p);
            }

            //Start drawing if this host frame has at least 1 figure
            if (hostFrame.getFigures().size() > 0) {
                for (Figure figure : this.hostFrame.getFigures()) {

                    //Draw human stick figure
                    if (figure.getType().equals("Stick Man")) {

                        //================== Drawing the sticks ==========================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Stick stick : figure.getSticks()) {
                            float x1 = (float) stick.getStart().getXCoordinate();
                            float y1 = (float) stick.getStart().getYCoordinate();

                            float x2 = (float) stick.getEnd().getXCoordinate();
                            float y2 = (float) stick.getEnd().getYCoordinate();

                            canvas.drawLine(x1, y1, x2, y2, p);
                        }

                        //================== Draw the head ================================

                        // - Setting coordinates for the head
                        float xHair = 0;
                        float yHair = 0;

                        float xNeck = 0;
                        float yNeck = 0;

                        for (Joint joint : figure.getJoints()) {
                            if (joint.getName().equals("head")) {
                                xHair = (float) joint.getXCoordinate();
                                yHair = (float) joint.getYCoordinate();
                            }
                            if (joint.getName().equals("neck")) {
                                xNeck = (float) joint.getXCoordinate();
                                yNeck = (float) joint.getYCoordinate();
                            }
                        }

                        float xHead = (xHair + xNeck)/2;
                        float yHead = (yHair + yNeck)/2;

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        // Same
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(xHead, yHead, 70, p);

                        //================== Drawing the joints ==============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Joint joint : figure.getJoints()) {
                            float x = (float) joint.getXCoordinate();
                            float y = (float) joint.getYCoordinate();
                            canvas.drawCircle(x, y, 20, p);
                        }

                        //================= Write the name of the figure ======================
                        if (this.figureNameShowed) {

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setColor(Color.RED);
                            p.setStyle(Paint.Style.FILL);
                            p.setStrokeWidth(0);
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            p.setTextSize(70);

                            canvas.drawText(figure.getName(), xHead+100, yHead, p);
                        }

                    }

                    //Draw Ninja Warrior stick figure
                    else if (figure.getType().equals("Ninja Warrior")) {
                        //==================== Drawing the sticks ============================

                        for (Stick stick : figure.getSticks()) {
                            float x1 = (float) stick.getStart().getXCoordinate();
                            float y1 = (float) stick.getStart().getYCoordinate();

                            float x2 = (float) stick.getEnd().getXCoordinate();
                            float y2 = (float) stick.getEnd().getYCoordinate();

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setStrokeWidth(30);
                            p.setStyle(Paint.Style.STROKE);
                            if (stick.getName().equals("knee_l") || stick.getName().equals("knee_r") ||
                                    stick.getName().equals("arm_l") || stick.getName().equals("arm_r") ||
                                    stick.getName().equals("body")) {
                                p.setColor(Color.BLACK);
                            } else {
                                p.setColor(Color.RED);
                            }
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            canvas.drawLine(x1, y1, x2, y2, p);
                        }

                        //======================== Draw the head ================================

                        // - Setting coordinates for the head
                        float xHair = 0;
                        float yHair = 0;

                        float xNeck = 0;
                        float yNeck = 0;

                        for (Joint joint : figure.getJoints()) {

                            if (joint.getName().equals("head")) {
                                xHair = (float) joint.getXCoordinate();
                                yHair = (float) joint.getYCoordinate();
                            }

                            if (joint.getName().equals("neck")) {
                                xNeck = (float) joint.getXCoordinate();
                                yNeck = (float) joint.getYCoordinate();
                            }
                        }

                        float xHead = (xHair + xNeck)/2;
                        float yHead = (yHair + yNeck)/2;

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(20);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.lace_5.getXCoordinate(), (float) figure.lace_5.getYCoordinate(),
                                (float) figure.lace_3.getXCoordinate(), (float) figure.lace_3.getYCoordinate(), p);

                        canvas.drawLine((float) figure.lace_5.getXCoordinate(), (float) figure.lace_5.getYCoordinate(),
                                (float) figure.lace_4.getXCoordinate(), (float) figure.lace_4.getYCoordinate(), p);


                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(xHead, yHead, 70, p);


                        //========================== Draw the eyes ===============================
                        Path path_l = new Path();

                        path_l.setFillType(Path.FillType.EVEN_ODD);
                        path_l.moveTo((float) figure.eye_l_1.getXCoordinate(), (float) figure.eye_l_1.getYCoordinate());
                        path_l.lineTo((float) figure.eye_l_2.getXCoordinate(), (float) figure.eye_l_2.getYCoordinate());
                        path_l.lineTo((float) figure.eye_l_3.getXCoordinate(), (float) figure.eye_l_3.getYCoordinate());

                        path_l.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.WHITE);
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_l, p);


                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.lace_1.getXCoordinate(), (float) figure.lace_1.getYCoordinate(),
                                (float) figure.lace_2.getXCoordinate(), (float) figure.lace_2.getYCoordinate(), p);


                        //=========================== Drawing the joints ==============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Joint joint : figure.getJoints()) {
                            if (!joint.getName().equals("eye_l_1") && !joint.getName().equals("eye_l_2") && !joint.getName().equals("eye_l_3")
                                    && !joint.getName().equals("lace_1") && !joint.getName().equals("lace_2") && !joint.getName().equals("lace_3")
                                    && !joint.getName().equals("lace_4") && !joint.getName().equals("lace_5")) {
                                float x = (float) joint.getXCoordinate();
                                float y = (float) joint.getYCoordinate();
                                canvas.drawCircle(x, y, 20, p);
                            }
                        }


                        //==================== Write the name of the figure ==========================
                        if (this.figureNameShowed) {

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setColor(Color.BLACK);
                            p.setStyle(Paint.Style.FILL);
                            p.setStrokeWidth(0);
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            p.setTextSize(70);

                            canvas.drawText(figure.getName(), xHead+100, yHead, p);
                        }
                    }

                    //Draw the Dark Warrior stick figure
                    else if (figure.getType().equals("Dark Warrior")) {

                        //====================== Drawing the sticks ==================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Stick stick : figure.getSticks()) {
                            float x1 = (float) stick.getStart().getXCoordinate();
                            float y1 = (float) stick.getStart().getYCoordinate();

                            float x2 = (float) stick.getEnd().getXCoordinate();
                            float y2 = (float) stick.getEnd().getYCoordinate();

                            canvas.drawLine(x1, y1, x2, y2, p);
                        }



                        //=========================== Draw the head ==================================

                        // - Setting coordinates for the head
                        float xHair = 0;
                        float yHair = 0;

                        float xNeck = 0;
                        float yNeck = 0;

                        for (Joint joint : figure.getJoints()) {

                            if (joint.getName().equals("head")) {
                                xHair = (float) joint.getXCoordinate();
                                yHair = (float) joint.getYCoordinate();
                            }

                            if (joint.getName().equals("neck")) {
                                xNeck = (float) joint.getXCoordinate();
                                yNeck = (float) joint.getYCoordinate();
                            }
                        }

                        float xHead = (xHair + xNeck)/2;
                        float yHead = (yHair + yNeck)/2;

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(xHead, yHead, 70, p);




                        //============================= Draw the eyes =================================
                        Path path_l = new Path();

                        path_l.setFillType(Path.FillType.EVEN_ODD);
                        path_l.moveTo((float) figure.eye_l_1.getXCoordinate(), (float) figure.eye_l_1.getYCoordinate());
                        path_l.lineTo((float) figure.eye_l_2.getXCoordinate(), (float) figure.eye_l_2.getYCoordinate());
                        path_l.lineTo((float) figure.eye_l_3.getXCoordinate(), (float) figure.eye_l_3.getYCoordinate());

                        path_l.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.WHITE);
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_l, p);


                        //=============================== Draw the ears ===============================
                        Path path_r = new Path();

                        path_r.setFillType(Path.FillType.EVEN_ODD);
                        path_r.moveTo((float) figure.ear_l_1.getXCoordinate(), (float) figure.ear_l_1.getYCoordinate());
                        path_r.lineTo((float) figure.ear_l_2.getXCoordinate(), (float) figure.ear_l_2.getYCoordinate());
                        path_r.lineTo((float) figure.ear_l_3.getXCoordinate(), (float) figure.ear_l_3.getYCoordinate());

                        path_r.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_r, p);


                        //============================= Drawing the joints ============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLUE);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        for (Joint joint : figure.getJoints()) {
                            if (!joint.getName().equals("eye_l_1") && !joint.getName().equals("eye_l_2") && !joint.getName().equals("eye_l_3")
                                    && !joint.getName().equals("ear_l_1") && !joint.getName().equals("ear_l_2") && !joint.getName().equals("ear_l_3")
                                    && !joint.getName().equals("sting_l_1") && !joint.getName().equals("sting_l_2") && !joint.getName().equals("sting_l_3")
                                    && !joint.getName().equals("sting_l_4") && !joint.getName().equals("sting_l_5") && !joint.getName().equals("sting_l_6")) {
                                float x = (float) joint.getXCoordinate();
                                float y = (float) joint.getYCoordinate();
                                canvas.drawCircle(x, y, 20, p);
                            }
                        }


                        //=========================== Write the name of the figure ====================
                        if (this.figureNameShowed) {

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setColor(Color.BLUE);
                            p.setStyle(Paint.Style.FILL);
                            p.setStrokeWidth(0);
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            p.setTextSize(70);

                            canvas.drawText(figure.getName(), xHead+100, yHead, p);
                        }
                    }

                    //Drawing the sword
                    else if (figure.getType().equals("Sword")) {
                        Path path_1 = new Path();

                        //========================== Draw the metal part ==============================

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.tool_1.getXCoordinate(), (float) figure.tool_1.getYCoordinate());
                        path_1.lineTo((float) figure.tool_2.getXCoordinate(), (float) figure.tool_2.getYCoordinate());

                        path_1.lineTo((float) figure.tool_4.getXCoordinate(), (float) figure.tool_4.getYCoordinate());
                        path_1.lineTo((float) figure.tool_3.getXCoordinate(), (float) figure.tool_3.getYCoordinate());

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);



                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.tool_1.getXCoordinate(), (float) figure.tool_1.getYCoordinate());
                        path_2.lineTo((float) figure.tool_2.getXCoordinate(), (float) figure.tool_2.getYCoordinate());

                        path_2.lineTo((float) figure.tool_6.getXCoordinate(), (float) figure.tool_6.getYCoordinate());
                        path_2.lineTo((float) figure.tool_5.getXCoordinate(), (float) figure.tool_5.getYCoordinate());

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);


                        //=========================== The holding part ================================

                        Path path_3 = new Path();

                        path_3.setFillType(Path.FillType.EVEN_ODD);
                        path_3.moveTo((float) figure.tool_7.getXCoordinate(), (float) figure.tool_7.getYCoordinate());
                        path_3.lineTo((float) figure.tool_8.getXCoordinate(), (float) figure.tool_8.getYCoordinate());
                        path_3.lineTo((float) figure.tool_9.getXCoordinate(), (float) figure.tool_9.getYCoordinate());
                        path_3.lineTo((float) figure.tool_10.getXCoordinate(), (float) figure.tool_10.getYCoordinate());
                        path_3.lineTo((float) figure.tool_11.getXCoordinate(), (float) figure.tool_11.getYCoordinate());
                        path_3.lineTo((float) figure.tool_12.getXCoordinate(), (float) figure.tool_12.getYCoordinate());

                        path_3.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_3, p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.DKGRAY);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(40);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.spinning.getXCoordinate(),
                                (float) figure.spinning.getYCoordinate(),
                                (float) figure.movingUnit.getXCoordinate(),
                                (float) figure.movingUnit.getYCoordinate(),
                                p);



                        //============================ Draw the joints ================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate(),
                                (float) figure.spinning.getYCoordinate(), 20, p);

                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate(),
                                (float) figure.movingUnit.getYCoordinate(), 20, p);

                        //=========================== Write the name of the figure ====================
                        if (this.figureNameShowed) {

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setColor(Color.RED);
                            p.setStyle(Paint.Style.FILL);
                            p.setStrokeWidth(0);
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            p.setTextSize(70);

                            canvas.drawText(figure.getName(),
                                    (float) figure.movingUnit.getXCoordinate() + 150,
                                    (float) figure.movingUnit.getYCoordinate(), p);
                        }

                    }

                    //Draw the gun
                    else if (figure.getType().equals("Gun")) {

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(15);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_41.getXCoordinate(), (float) figure.tool_41.getYCoordinate(),
                                (float) figure.tool_42.getXCoordinate(), (float) figure.tool_42.getYCoordinate(), p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(10);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_43.getXCoordinate(), (float) figure.tool_43.getYCoordinate(),
                                (float) figure.tool_44.getXCoordinate(), (float) figure.tool_44.getYCoordinate(), p);



                        Path path_1 = new Path();

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.tool_1.getXCoordinate(), (float) figure.tool_1.getYCoordinate());
                        path_1.lineTo((float) figure.tool_2.getXCoordinate(), (float) figure.tool_2.getYCoordinate());
                        path_1.lineTo((float) figure.tool_3.getXCoordinate(), (float) figure.tool_3.getYCoordinate());
                        path_1.lineTo((float) figure.tool_4.getXCoordinate(), (float) figure.tool_4.getYCoordinate());
                        path_1.lineTo((float) figure.tool_5.getXCoordinate(), (float) figure.tool_5.getYCoordinate());

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);


                        //================================ The amo part ===============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(45);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine( (float) figure.tool_36.getXCoordinate(), (float) figure.tool_36.getYCoordinate(),
                                (float) figure.tool_37.getXCoordinate(), (float) figure.tool_37.getYCoordinate(), p);



                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.tool_6.getXCoordinate(), (float) figure.tool_6.getYCoordinate());
                        path_2.lineTo((float) figure.tool_7.getXCoordinate(), (float) figure.tool_7.getYCoordinate());
                        path_2.lineTo((float) figure.tool_8.getXCoordinate(), (float) figure.tool_8.getYCoordinate());
                        path_2.lineTo((float) figure.tool_9.getXCoordinate(), (float) figure.tool_9.getYCoordinate());
                        path_2.lineTo((float) figure.tool_10.getXCoordinate(), (float) figure.tool_10.getYCoordinate());
                        path_2.lineTo((float) figure.tool_11.getXCoordinate(), (float) figure.tool_11.getYCoordinate());
                        path_2.lineTo((float) figure.tool_12.getXCoordinate(), (float) figure.tool_12.getYCoordinate());
                        path_2.lineTo((float) figure.tool_13.getXCoordinate(), (float) figure.tool_13.getYCoordinate());
                        path_2.lineTo((float) figure.tool_14.getXCoordinate(), (float) figure.tool_14.getYCoordinate());
                        path_2.lineTo((float) figure.tool_15.getXCoordinate(), (float) figure.tool_15.getYCoordinate());

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);



                        Path path_3 = new Path();

                        path_3.setFillType(Path.FillType.EVEN_ODD);
                        path_3.moveTo((float) figure.tool_16.getXCoordinate(), (float) figure.tool_16.getYCoordinate());
                        path_3.lineTo((float) figure.tool_17.getXCoordinate(), (float) figure.tool_17.getYCoordinate());
                        path_3.lineTo((float) figure.tool_18.getXCoordinate(), (float) figure.tool_18.getYCoordinate());
                        path_3.lineTo((float) figure.tool_19.getXCoordinate(), (float) figure.tool_19.getYCoordinate());
                        path_3.lineTo((float) figure.tool_20.getXCoordinate(), (float) figure.tool_20.getYCoordinate());
                        path_3.lineTo((float) figure.tool_21.getXCoordinate(), (float) figure.tool_21.getYCoordinate());
                        path_3.lineTo((float) figure.tool_22.getXCoordinate(), (float) figure.tool_22.getYCoordinate());
                        path_3.lineTo((float) figure.tool_23.getXCoordinate(), (float) figure.tool_23.getYCoordinate());

                        path_3.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_3, p);



                        Path path_4 = new Path();

                        path_4.setFillType(Path.FillType.EVEN_ODD);
                        path_4.moveTo((float) figure.tool_24.getXCoordinate(), (float) figure.tool_24.getYCoordinate());
                        path_4.lineTo((float) figure.tool_25.getXCoordinate(), (float) figure.tool_25.getYCoordinate());
                        path_4.lineTo((float) figure.tool_26.getXCoordinate(), (float) figure.tool_26.getYCoordinate());
                        path_4.lineTo((float) figure.tool_27.getXCoordinate(), (float) figure.tool_27.getYCoordinate());
                        path_4.lineTo((float) figure.tool_28.getXCoordinate(), (float) figure.tool_28.getYCoordinate());
                        path_4.lineTo((float) figure.tool_29.getXCoordinate(), (float) figure.tool_29.getYCoordinate());

                        path_4.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_4, p);




                        Path path_5 = new Path();

                        path_5.setFillType(Path.FillType.EVEN_ODD);
                        path_5.moveTo((float) figure.tool_14.getXCoordinate(), (float) figure.tool_14.getYCoordinate());
                        path_5.lineTo((float) figure.tool_15.getXCoordinate(), (float) figure.tool_15.getYCoordinate());
                        path_5.lineTo((float) figure.tool_38.getXCoordinate(), (float) figure.tool_38.getYCoordinate());
                        path_5.lineTo((float) figure.tool_39.getXCoordinate(), (float) figure.tool_39.getYCoordinate());
                        path_5.lineTo((float) figure.tool_40.getXCoordinate(), (float) figure.tool_40.getYCoordinate());

                        path_5.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_5, p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(20);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine( (float) figure.tool_33.getXCoordinate(), (float) figure.tool_33.getYCoordinate(),
                                (float) figure.tool_34.getXCoordinate(), (float) figure.tool_34.getYCoordinate(), p);

                        canvas.drawLine( (float) figure.tool_34.getXCoordinate(), (float) figure.tool_34.getYCoordinate(),
                                (float) figure.tool_35.getXCoordinate(), (float) figure.tool_35.getYCoordinate(), p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(35);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine( (float) figure.tool_30.getXCoordinate(), (float) figure.tool_30.getYCoordinate(),
                                (float) figure.tool_31.getXCoordinate(), (float) figure.tool_31.getYCoordinate(), p);

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(55);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine( (float) figure.tool_31.getXCoordinate(), (float) figure.tool_31.getYCoordinate(),
                                (float) figure.tool_32.getXCoordinate(), (float) figure.tool_32.getYCoordinate(), p);



                        //============================ Draw the joints ================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLUE);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate(), (float) figure.spinning.getYCoordinate(), 20, p);
                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate(), (float) figure.movingUnit.getYCoordinate(), 20, p);



                        //=========================== Write the name of the figure ===================
                        if (this.figureNameShowed) {

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setColor(Color.BLUE);
                            p.setStyle(Paint.Style.FILL);
                            p.setStrokeWidth(0);
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            p.setTextSize(70);

                            canvas.drawText(figure.getName(),
                                    (float) figure.movingUnit.getXCoordinate(),
                                    (float) figure.movingUnit.getYCoordinate() - 100, p);
                        }
                    }

                    //Draw the trident
                    else if (figure.getType().equals("Trident")) {

                        //========================= Drawing shadow ====================================

                        //========================= Draw the body

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(35);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_13.getXCoordinate(), (float) figure.tool_13.getYCoordinate(),
                                (float) figure.tool_14.getXCoordinate(), (float) figure.tool_14.getYCoordinate(), p);



                        //========================= Draw the top steel part
                        canvas.drawLine((float) figure.tool_15.getXCoordinate(), (float) figure.tool_15.getYCoordinate(),
                                (float) figure.tool_16.getXCoordinate(), (float) figure.tool_16.getYCoordinate(), p);

                        canvas.drawLine((float) figure.tool_17.getXCoordinate(), (float) figure.tool_17.getYCoordinate(),
                                (float) figure.tool_18.getXCoordinate(), (float) figure.tool_18.getYCoordinate(), p);

                        //========================= Draw the top stab part
                        Path path_4 = new Path();

                        path_4.setFillType(Path.FillType.EVEN_ODD);
                        path_4.moveTo((float) figure.tool_27.getXCoordinate(), (float) figure.tool_27.getYCoordinate());
                        path_4.lineTo((float) figure.tool_28.getXCoordinate(), (float) figure.tool_28.getYCoordinate());
                        path_4.lineTo((float) figure.tool_38.getXCoordinate(), (float) figure.tool_38.getYCoordinate());
                        path_4.lineTo((float) figure.tool_37.getXCoordinate(), (float) figure.tool_37.getYCoordinate());

                        path_4.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_4, p);



                        //=========================== Draw the middle steel part

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(35);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_19.getXCoordinate(), (float) figure.tool_19.getYCoordinate(),
                                (float) figure.tool_20.getXCoordinate(), (float) figure.tool_20.getYCoordinate(), p);

                        //=========================== Draw the middle stab part

                        Path path_5 = new Path();

                        path_5.setFillType(Path.FillType.EVEN_ODD);
                        path_5.moveTo((float) figure.tool_31.getXCoordinate(), (float) figure.tool_31.getYCoordinate());
                        path_5.lineTo((float) figure.tool_32.getXCoordinate(), (float) figure.tool_32.getYCoordinate());
                        path_5.lineTo((float) figure.tool_40.getXCoordinate(), (float) figure.tool_40.getYCoordinate());
                        path_5.lineTo((float) figure.tool_39.getXCoordinate(), (float) figure.tool_39.getYCoordinate());

                        path_5.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_5, p);



                        //======================== Draw the bottom steel part

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(35);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_21.getXCoordinate(), (float) figure.tool_21.getYCoordinate(),
                                (float) figure.tool_22.getXCoordinate(), (float) figure.tool_22.getYCoordinate(), p);

                        canvas.drawLine((float) figure.tool_23.getXCoordinate(), (float) figure.tool_23.getYCoordinate(),
                                (float) figure.tool_24.getXCoordinate(), (float) figure.tool_24.getYCoordinate(), p);

                        //========================= Draw the bottom stab part
                        Path path_6 = new Path();

                        path_6.setFillType(Path.FillType.EVEN_ODD);
                        path_6.moveTo((float) figure.tool_35.getXCoordinate(), (float) figure.tool_35.getYCoordinate());
                        path_6.lineTo((float) figure.tool_36.getXCoordinate(), (float) figure.tool_36.getYCoordinate());
                        path_6.lineTo((float) figure.tool_42.getXCoordinate(), (float) figure.tool_42.getYCoordinate());
                        path_6.lineTo((float) figure.tool_41.getXCoordinate(), (float) figure.tool_41.getYCoordinate());

                        path_6.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_6, p);



                        //============================= Draw the tail part
                        Path path_8 = new Path();

                        path_8.setFillType(Path.FillType.EVEN_ODD);
                        path_8.moveTo((float) figure.tool_46.getXCoordinate(), (float) figure.tool_46.getYCoordinate());
                        path_8.lineTo((float) figure.tool_45.getXCoordinate(), (float) figure.tool_45.getYCoordinate());
                        path_8.lineTo((float) figure.tool_44.getXCoordinate(), (float) figure.tool_44.getYCoordinate());
                        path_8.lineTo((float) figure.tool_47.getXCoordinate(), (float) figure.tool_47.getYCoordinate());
                        path_8.lineTo((float) figure.tool_48.getXCoordinate(), (float) figure.tool_48.getYCoordinate());
                        path_8.lineTo((float) figure.tool_49.getXCoordinate(), (float) figure.tool_49.getYCoordinate());

                        path_8.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 207, 181, 59));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_8, p);



                        //=========================== Draw real part ==================================

                        //=========================== Draw the body

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(25);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_1.getXCoordinate(), (float) figure.tool_1.getYCoordinate(),
                                (float) figure.tool_2.getXCoordinate(), (float) figure.tool_2.getYCoordinate(), p);



                        //=========================== Draw the top steel part

                        canvas.drawLine((float) figure.tool_3.getXCoordinate(), (float) figure.tool_3.getYCoordinate(),
                                (float) figure.tool_4.getXCoordinate(), (float) figure.tool_4.getYCoordinate(), p);

                        canvas.drawLine((float) figure.tool_5.getXCoordinate(), (float) figure.tool_5.getYCoordinate(),
                                (float) figure.tool_6.getXCoordinate(), (float) figure.tool_6.getYCoordinate(), p);

                        //=========================== Draw the top stab part
                        Path path_1 = new Path();

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.tool_25.getXCoordinate(), (float) figure.tool_25.getYCoordinate());
                        path_1.lineTo((float) figure.tool_26.getXCoordinate(), (float) figure.tool_26.getYCoordinate());

                        path_1.lineTo((float) figure.tool_27.getXCoordinate(), (float) figure.tool_27.getYCoordinate());
                        path_1.lineTo((float) figure.tool_28.getXCoordinate(), (float) figure.tool_28.getYCoordinate());

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);



                        //=========================== Draw the middle steel part

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(25);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_7.getXCoordinate(), (float) figure.tool_7.getYCoordinate(),
                                (float) figure.tool_8.getXCoordinate(), (float) figure.tool_8.getYCoordinate(), p);

                        //=========================== Draw the middle stab part
                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.tool_29.getXCoordinate(), (float) figure.tool_29.getYCoordinate());
                        path_2.lineTo((float) figure.tool_30.getXCoordinate(), (float) figure.tool_30.getYCoordinate());
                        path_2.lineTo((float) figure.tool_31.getXCoordinate(), (float) figure.tool_31.getYCoordinate());
                        path_2.lineTo((float) figure.tool_32.getXCoordinate(), (float) figure.tool_32.getYCoordinate());

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);



                        //=========================== Draw the bottom steel part

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(25);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_9.getXCoordinate(), (float) figure.tool_9.getYCoordinate(),
                                (float) figure.tool_10.getXCoordinate(), (float) figure.tool_10.getYCoordinate(), p);

                        canvas.drawLine((float) figure.tool_11.getXCoordinate(), (float) figure.tool_11.getYCoordinate(),
                                (float) figure.tool_12.getXCoordinate(), (float) figure.tool_12.getYCoordinate(), p);

                        //=========================== Draw the bottom stab part
                        Path path_3 = new Path();

                        path_3.setFillType(Path.FillType.EVEN_ODD);
                        path_3.moveTo((float) figure.tool_33.getXCoordinate(), (float) figure.tool_33.getYCoordinate());
                        path_3.lineTo((float) figure.tool_34.getXCoordinate(), (float) figure.tool_34.getYCoordinate());
                        path_3.lineTo((float) figure.tool_35.getXCoordinate(), (float) figure.tool_35.getYCoordinate());
                        path_3.lineTo((float) figure.tool_36.getXCoordinate(), (float) figure.tool_36.getYCoordinate());

                        path_3.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 223, 100));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_3, p);


                        //=========================== Draw the tail part
                        Path path_7 = new Path();

                        path_7.setFillType(Path.FillType.EVEN_ODD);
                        path_7.moveTo((float) figure.tool_46.getXCoordinate(), (float) figure.tool_46.getYCoordinate());
                        path_7.lineTo((float) figure.tool_43.getXCoordinate(), (float) figure.tool_43.getYCoordinate());
                        path_7.lineTo((float) figure.tool_44.getXCoordinate(), (float) figure.tool_44.getYCoordinate());
                        path_7.lineTo((float) figure.tool_45.getXCoordinate(), (float) figure.tool_45.getYCoordinate());

                        path_7.close();
                        canvas.drawPath(path_7, p);


                        //=========================== Draw the joints =================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate(), (float) figure.spinning.getYCoordinate(), 20, p);
                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate(), (float) figure.movingUnit.getYCoordinate(), 20, p);



                        //=========================== Write the name of the figure ===================
                        if (this.figureNameShowed) {

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setColor(Color.RED);
                            p.setStyle(Paint.Style.FILL);
                            p.setStrokeWidth(0);
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            p.setTextSize(70);

                            canvas.drawText(figure.getName(),
                                    (float) figure.movingUnit.getXCoordinate(),
                                    (float) figure.movingUnit.getYCoordinate() + 180, p);
                        }
                    }

                    //Draw the wooden shield
                    else if (figure.getType().equals("Wooden Shield")) {
                        //============================== The outer circle =============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(65);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(
                                (float) figure.movingUnit.getXCoordinate(),
                                (float) figure.movingUnit.getYCoordinate(),
                                160, p);

                        //============================= The inner circle ==============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 236, 213, 167));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle(
                                (float) figure.movingUnit.getXCoordinate(),
                                (float) figure.movingUnit.getYCoordinate(),
                                150, p);

                        //================================= Scratches =================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(3);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_1.getXCoordinate(), (float) figure.tool_1.getYCoordinate(),
                                (float) figure.tool_2.getXCoordinate(), (float) figure.tool_2.getYCoordinate(), p);

                        canvas.drawLine((float) figure.tool_3.getXCoordinate(), (float) figure.tool_3.getYCoordinate(),
                                (float) figure.tool_4.getXCoordinate(), (float) figure.tool_4.getYCoordinate(), p);

                        canvas.drawLine((float) figure.tool_5.getXCoordinate(), (float) figure.tool_5.getYCoordinate(),
                                (float) figure.tool_6.getXCoordinate(), (float) figure.tool_6.getYCoordinate(), p);



                        //============================ The arrow body part ============================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 93, 67, 44));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(20);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_10.getXCoordinate(), (float) figure.tool_10.getYCoordinate(),
                                (float) figure.tool_12.getXCoordinate(), (float) figure.tool_12.getYCoordinate(), p);



                        //============================ Draw the steel arrow part ======================
                        Path path_1 = new Path();

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.tool_8.getXCoordinate(), (float) figure.tool_8.getYCoordinate());
                        path_1.lineTo((float) figure.tool_7.getXCoordinate(), (float) figure.tool_7.getYCoordinate());
                        path_1.lineTo((float) figure.tool_9.getXCoordinate(), (float) figure.tool_9.getYCoordinate());
                        path_1.lineTo((float) figure.tool_10.getXCoordinate(), (float) figure.tool_10.getYCoordinate());
                        path_1.lineTo((float) figure.tool_11.getXCoordinate(), (float) figure.tool_11.getYCoordinate());

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 219, 228, 235));
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);



                        //================== Draw the steel arrow part surrounding ====================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(4);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);

                        canvas.drawLine((float) figure.tool_13.getXCoordinate(), (float) figure.tool_13.getYCoordinate(),
                                (float) figure.tool_14.getXCoordinate(), (float) figure.tool_14.getYCoordinate(), p);


                        //============================ The arrow tail =================================
                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.tool_12.getXCoordinate(), (float) figure.tool_12.getYCoordinate());
                        path_2.lineTo((float) figure.tool_15.getXCoordinate(), (float) figure.tool_15.getYCoordinate());
                        path_2.lineTo((float) figure.tool_16.getXCoordinate(), (float) figure.tool_16.getYCoordinate());
                        path_2.lineTo((float) figure.tool_17.getXCoordinate(), (float) figure.tool_17.getYCoordinate());
                        path_2.lineTo((float) figure.tool_18.getXCoordinate(), (float) figure.tool_18.getYCoordinate());
                        path_2.lineTo((float) figure.tool_19.getXCoordinate(), (float) figure.tool_19.getYCoordinate());

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.RED);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);



                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLACK);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(4);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);


                        //=========================== Draw the joints =================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLUE);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate(), (float) figure.spinning.getYCoordinate(), 20, p);
                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate(), (float) figure.movingUnit.getYCoordinate(), 20, p);



                        //Inactive joints:

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.tool_20.getXCoordinate(), (float) figure.tool_20.getYCoordinate(), 10, p);
                        canvas.drawCircle((float) figure.tool_21.getXCoordinate(), (float) figure.tool_21.getYCoordinate(), 10, p);
                        canvas.drawCircle((float) figure.tool_22.getXCoordinate(), (float) figure.tool_22.getYCoordinate(), 10, p);
                        canvas.drawCircle((float) figure.tool_23.getXCoordinate(), (float) figure.tool_23.getYCoordinate(), 10, p);
                        canvas.drawCircle((float) figure.tool_24.getXCoordinate(), (float) figure.tool_24.getYCoordinate(), 10, p);



                        //=========================== Write the name of the figure ===================
                        if (this.figureNameShowed) {

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setColor(Color.BLUE);
                            p.setStyle(Paint.Style.FILL);
                            p.setStrokeWidth(0);
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            p.setTextSize(70);

                            canvas.drawText(figure.getName(),
                                    (float) figure.spinning.getXCoordinate(),
                                    (float) figure.spinning.getYCoordinate()+100, p);
                        }
                    }

                    //Draw metal shield
                    else if (figure.getType().equals("Metal Shield")) {

                        //================================= Shadow ====================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 190, 190, 190));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(35);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawLine((float) figure.tool_16.getXCoordinate(), (float) figure.tool_16.getYCoordinate(),
                                (float) figure.tool_17.getXCoordinate(), (float) figure.tool_17.getYCoordinate(), p);

                        canvas.drawLine((float) figure.tool_18.getXCoordinate(), (float) figure.tool_18.getYCoordinate(),
                                (float) figure.tool_19.getXCoordinate(), (float) figure.tool_19.getYCoordinate(), p);

                        //=============================== Right - Upper ===============================
                        Path path_1 = new Path();

                        path_1.setFillType(Path.FillType.EVEN_ODD);
                        path_1.moveTo((float) figure.movingUnit.getXCoordinate(), (float) figure.movingUnit.getYCoordinate());
                        path_1.lineTo((float) figure.tool_1.getXCoordinate(), (float) figure.tool_1.getYCoordinate());
                        path_1.lineTo((float) figure.tool_2.getXCoordinate(), (float) figure.tool_2.getYCoordinate());
                        path_1.quadTo((float) figure.tool_3.getXCoordinate(), (float) figure.tool_3.getYCoordinate(),
                                (float) figure.tool_4.getXCoordinate(), (float) figure.tool_4.getYCoordinate());
                        path_1.lineTo((float) figure.tool_5.getXCoordinate(), (float) figure.tool_5.getYCoordinate());

                        path_1.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 190, 0, 0));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(20);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_1, p);



                        //================================= Right - Lower =============================
                        Path path_3 = new Path();

                        path_3.setFillType(Path.FillType.EVEN_ODD);
                        path_3.moveTo((float) figure.movingUnit.getXCoordinate(), (float) figure.movingUnit.getYCoordinate());
                        path_3.lineTo((float) figure.tool_5.getXCoordinate(), (float) figure.tool_5.getYCoordinate());
                        path_3.lineTo((float) figure.spinning.getXCoordinate(), (float) figure.spinning.getYCoordinate());

                        path_3.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 220, 220, 220));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(20);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_3, p);



                        //================================= Left - Upper ==============================
                        Path path_2 = new Path();

                        path_2.setFillType(Path.FillType.EVEN_ODD);
                        path_2.moveTo((float) figure.movingUnit.getXCoordinate(), (float) figure.movingUnit.getYCoordinate());
                        path_2.lineTo((float) figure.tool_1.getXCoordinate(), (float) figure.tool_1.getYCoordinate());
                        path_2.lineTo((float) figure.tool_6.getXCoordinate(), (float) figure.tool_6.getYCoordinate());
                        path_2.quadTo((float) figure.tool_7.getXCoordinate(), (float) figure.tool_7.getYCoordinate(),
                                (float) figure.tool_8.getXCoordinate(), (float) figure.tool_8.getYCoordinate());
                        path_2.lineTo((float) figure.tool_9.getXCoordinate(), (float) figure.tool_9.getYCoordinate());

                        path_2.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 255, 255));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(20);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_2, p);



                        //================================ Left - Lower ===============================
                        Path path_4 = new Path();

                        path_4.setFillType(Path.FillType.EVEN_ODD);
                        path_4.moveTo((float) figure.movingUnit.getXCoordinate(), (float) figure.movingUnit.getYCoordinate());
                        path_4.lineTo((float) figure.tool_9.getXCoordinate(), (float) figure.tool_9.getYCoordinate());
                        path_4.lineTo((float) figure.spinning.getXCoordinate(), (float) figure.spinning.getYCoordinate());

                        path_4.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(255, 255, 0, 0));
                        p.setStyle(Paint.Style.FILL_AND_STROKE);
                        p.setStrokeWidth(20);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_4, p);



                        //============================== Surrounding frame ============================
                        Path path_5 = new Path();

                        path_5.setFillType(Path.FillType.EVEN_ODD);
                        path_5.moveTo((float) figure.tool_1.getXCoordinate(), (float) figure.tool_1.getYCoordinate());
                        path_5.lineTo((float) figure.tool_2.getXCoordinate(), (float) figure.tool_2.getYCoordinate());
                        path_5.quadTo((float) figure.tool_3.getXCoordinate(), (float) figure.tool_3.getYCoordinate(),
                                (float) figure.tool_4.getXCoordinate(), (float) figure.tool_4.getYCoordinate());
                        path_5.lineTo((float) figure.spinning.getXCoordinate(), (float) figure.spinning.getYCoordinate());
                        path_5.lineTo((float) figure.tool_8.getXCoordinate(), (float) figure.tool_8.getYCoordinate());
                        path_5.quadTo((float) figure.tool_7.getXCoordinate(), (float) figure.tool_7.getYCoordinate(),
                                (float) figure.tool_6.getXCoordinate(), (float) figure.tool_6.getYCoordinate());

                        path_5.close();

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.GRAY);
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_5, p);



                        //=========================== Draw the joints =================================

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.BLUE);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.spinning.getXCoordinate(), (float) figure.spinning.getYCoordinate(), 20, p);
                        canvas.drawCircle((float) figure.movingUnit.getXCoordinate(), (float) figure.movingUnit.getYCoordinate(), 20, p);



                        //Inactive joints:

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.LTGRAY);
                        p.setStyle(Paint.Style.FILL);
                        p.setStrokeWidth(0);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawCircle((float) figure.tool_10.getXCoordinate(), (float) figure.tool_10.getYCoordinate(), 10, p);
                        canvas.drawCircle((float) figure.tool_11.getXCoordinate(), (float) figure.tool_11.getYCoordinate(), 10, p);
                        canvas.drawCircle((float) figure.tool_12.getXCoordinate(), (float) figure.tool_12.getYCoordinate(), 10, p);
                        canvas.drawCircle((float) figure.tool_13.getXCoordinate(), (float) figure.tool_13.getYCoordinate(), 10, p);
                        canvas.drawCircle((float) figure.tool_14.getXCoordinate(), (float) figure.tool_14.getYCoordinate(), 10, p);
                        canvas.drawCircle((float) figure.tool_15.getXCoordinate(), (float) figure.tool_15.getYCoordinate(), 10, p);



                        //================================== Shadow ===================================
                        Path path_7 = new Path();

                        path_7.setFillType(Path.FillType.EVEN_ODD);
                        path_7.moveTo((float) figure.tool_1.getXCoordinate(), (float) figure.tool_1.getYCoordinate());
                        path_7.lineTo((float) figure.tool_2.getXCoordinate(), (float) figure.tool_2.getYCoordinate());
                        path_7.quadTo((float) figure.tool_3.getXCoordinate(), (float) figure.tool_3.getYCoordinate(),
                                (float) figure.tool_4.getXCoordinate(), (float) figure.tool_4.getYCoordinate());
                        path_7.lineTo((float) figure.spinning.getXCoordinate(), (float) figure.spinning.getYCoordinate());

                        //$$$$$$$$$  Drawing  $$$$$$$$$$
                        p.setColor(Color.argb(60, 20, 20, 20));
                        p.setStyle(Paint.Style.STROKE);
                        p.setStrokeWidth(30);
                        //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                        canvas.drawPath(path_7, p);



                        //=========================== Write the name of the figure ===================
                        if (this.figureNameShowed) {

                            //$$$$$$$$$  Drawing  $$$$$$$$$$
                            p.setColor(Color.BLUE);
                            p.setStyle(Paint.Style.FILL);
                            p.setStrokeWidth(0);
                            //$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$

                            p.setTextSize(70);

                            canvas.drawText(figure.getName(),
                                    (float) figure.spinning.getXCoordinate(),
                                    (float) figure.spinning.getYCoordinate()+100, p);
                        }
                    }

                } //Closing of for-loop through all figures

            } //Closing of if this window has any frame

        } //Closing of the onDraw method


        @Override
        public boolean onTouchEvent(MotionEvent event) {

            //Track location of the user's mouse (touch)
            double mouseAtX = event.getX();
            double mouseAtY = event.getY();

            switch(event.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    //Check if user clicked (touched) on any of the joint on the active frame
                    if (!dragActivated) {
                        for (Figure fig : activeFrame.getFigures()) {
                            for (Joint j : fig.getJoints()) {
                                if (isInside(j, mouseAtX, mouseAtY) && j.isMobile()) {
                                    //If found, set active figure and joint to the desired elements
                                    activeFigure = fig;
                                    activeJoint = j;
                                    dragActivated = true;
                                }
                            }
                        }
                    }

                    break;


                case MotionEvent.ACTION_MOVE:
                    //If active figure is not default (meaning that user touched on some figure of the frame, execute as below:
                    if (!activeFigure.getName().equals("Default") && dragActivated
                            && mouseAtX < screenWidth-2 && mouseAtY < screenHeight-2
                            && mouseAtX > 2 && mouseAtY > 2) {

                        //Move the figure to the front first
                        activeFrame.deleteFigure(activeFigure);
                        activeFrame.addFigure(activeFigure);

                        //Move the active figure as user directs
                        activeFigure.moveMan(activeJoint, mouseAtX, mouseAtY);

                        //Set the moved figure back to the active frame
                        activeFrame.setFigure(activeFrame.findFigure(activeFigure), activeFigure);

                        activeFrameListComponent.setMiniHostFrame(activeFrame);

                        //Set the adjusted frame back to this host window
                        window.setFrame(window.findFrame(activeFrame), activeFrame);
                    }
                    break;

                case MotionEvent.ACTION_UP:

                    //If current frame is interpolated, tell user to choose one of the original frames
                    if (animationRunning) {
                        Toast.makeText(MainActivity.this, "Animation is still running", Toast.LENGTH_SHORT)
                                .show();
                    } else if (!window.getFrames().contains(activeFrame) ) {
                        reportDragDeactivate();
                    }

                    //Reset the active joint
                    activeJoint = new Joint("Default", false);

                    //Reset the state "dragActivated" to mark that the program is not dragging
                    dragActivated = false;
                    break;

                default: break;
            }

            //Update the active frame being presented
            drawingCanvas.invalidate();

            //Update the active FrameListComponent
            activeFrameListComponent.invalidate();

            return true;
        }
    }
    //DrawingCanvas class ends here.



    //_____________________________________________________________________________________________
    //SET UP ALL LOGIC COMPONENTS HERE:


    //##################################### Window elements #######################################

    //Main window: the one and only window that contains and regulates everything happens "from the inside".
    private Window window = new Window();


    //##################################### Frame elements ########################################

    //Initial frame:
    private Frame frame = new Frame();

    //Active frame: the frame being presented
    private Frame activeFrame  = frame;

    //An empty window for the surrounding frame. Nothing should happen here.
    private Frame emptyFrameForTheme = new Frame();


    //##################################### Figure elements #######################################

    //Initial figure
    private Figure human = new Figure("Ninja Warrior", "Mike");

    //Active figure: the figure being moved. When done moving, this is immediately set to "default".
    //This figure has to belong to the active frame.
    private Figure activeFigure = new Figure("Default", "Default");


    //###################################### Joint elements #######################################

    //Active joint: the joint being moved. When done moving, this is immediately set to "default".
    //This joint has to belong to the active figure.
    private Joint activeJoint = new Joint("Default", false);


    //############################## Dragging and dropping elements ###############################

    //This is to keep track of whether user is dragging some figure.
    private boolean dragActivated = false;


    //################################## Animation elements #######################################

    //The thread responsible for playing interpolated animation
    private Thread t;

    //The collection of all the interpolated-created frames
    private ArrayList<Frame> interpolation;

    //Variable to check whether the animation is running.
    private boolean animationRunning = false;

    //The index of the currently showed frame. Only used in animation.
    int animationIndex = 0;

    //Animation speed
    int animationSpeed = 20;

    //No. of interpolated frames
    int no_interpolatedFrames = 80;

    //Screen size information: Width and Height
    float screenWidth;
    float screenHeight;



    //_____________________________________________________________________________________________
    //SET UP ALL UI COMPONENTS HERE:


    //################################### Frame control panel #####################################

    //Frame pop-up control panel
    private LinearLayout frameControlLayout;

    //Checkbox whether figures' names is being showed
    private CheckBox figureNameCheckBox;


    //################################## Figure control panel #####################################

    //Figure pop-up control panel
    private LinearLayout figureControlLayout;

    //Where user chooses figure type of the added figure
    private Spinner figureTypesGroup;

    //Text of figure name:
    private EditText figureNameText;


    //################################ Animation control panel ####################################

    //Animation pop-up control panel
    private LinearLayout animationControlLayout;

    //Text where user inputs the desired animation speed
    private EditText animationSpeedInput;

    //Text where user inputs the desired animation number of interpolated frames
    private EditText animationFrames;


    //################################ Background control panel ###################################

    //Background pop-up control panel
    private LinearLayout backgroundControlLayout;

    //The background of a frame
    private Bitmap backgroundBitmap;


    //################################ Instruction presentation ###################################

    //Instruction page display
    private InstructionPage page;


    //################################# Main interactive screen ###################################

    //The layout hosts the gray them frame
    private LinearLayout mainScreenLayout;

    //The surrounding frame for the main frame.
    private DrawingCanvas theme;

    //The main frame for activities of all the figures of the active frame.
    private DrawingCanvas drawingCanvas;


    //#################################### List of all frames #####################################

    //Linear layout to store all FrameListComponent(s)
    private LinearLayout frameListComponentContainer;

    //Active frame list component
    /* Should be adjusted when:
    DrawingCanvas touch, FrameListComponent layout touch,
    Add frame, Delete frame,
    Add figure, Delete figure,
    Animation start, Animation stop
     */
    private FrameListComponent activeFrameListComponent;


    //################################### General UI elements #####################################

    //Layout parameters for pop-up control panel
    private LinearLayout.LayoutParams params;

    //The window host those control panels
    private PopupWindow popupWindow;

    //Zero-sized layout that contains all the pop-up layouts control panel
    private LinearLayout container;



    //_____________________________________________________________________________________________
    //HELPER METHODS:

    //Checks if user's click is inside the joint. "x" and "y" are coordinates of the mouse (touch).
    private boolean isInside(Joint joint, double x, double y) {
        double xDiff = joint.getXCoordinate() - x;
        double yDiff = joint.getYCoordinate() - y;
        double distance = java.lang.Math.sqrt(xDiff * xDiff + yDiff * yDiff);
        return distance <= 60;
    }

    //List contains all digits - to support the below method
    private ArrayList<Character> numberChar = new ArrayList<Character>();

    //Checks if the input string can be converted to a number
    private boolean checkForPureNumbersString(String string) {
        if (string.equals("")) return false;
        for (Character c : string.toCharArray()) {
            if (!numberChar.contains(c)) return false;
        }
        return true;
    }

    //To be used in on touch event method inside DrawingCanvas
    public void reportDragDeactivate() {
        //If "Add figure" fails, display a warning toast about the problem
        Toast.makeText(MainActivity.this, "Please choose a frame first", Toast.LENGTH_SHORT)
                .show();
    }



    //_____________________________________________________________________________________________
    //BUTTON METHODS:


    //################################### Frame control panel #####################################

    public void popUpFrameControlPanel(View v) {
        if (!animationRunning) {
            popupWindow.setContentView(frameControlLayout);
            popupWindow.showAtLocation(frameControlLayout, Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            Toast.makeText(MainActivity.this, "Please stop animation first", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    //Adding new frame to the current window
    public void addFrame(View v) {
        if (!animationRunning && this.window.getFrames().contains(activeFrame)) {
            Frame addedFrame;
            if (this.window.getFrames().size() == 0) {
                addedFrame = new Frame();
            } else {
                addedFrame = this.window.createCopyOf(activeFrame);
            }

            //Add this new frame to the window
            this.window.addFrame(addedFrame);

            //Set the newly added frame to the active frame
            this.activeFrame = addedFrame;

            //And show it on the screen
            this.drawingCanvas.setHostFrame(activeFrame);

            //Redraw the main screen to fit the active frame, though it would be no difference.
            this.drawingCanvas.invalidate();

            //Reset active figure
            activeFigure = new Figure("Default", "Default");

            //Redraw the active frame list component so that it is no longer highlighted in green
            activeFrameListComponent.invalidate();

            //Then set the currently active frame list component to a new one that hosts the newly added frame
            activeFrameListComponent = new FrameListComponent(this, activeFrame);

            //Then add it to the layout
            frameListComponentContainer.addView(activeFrameListComponent);

        } else {
            if (animationRunning) {
                Toast.makeText(MainActivity.this, "Animation is still running", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this, "Please choose a frame to duplicate", Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }

    //Deleting new frame from the current window
    public void deleteFrame(View v) {
        if (!animationRunning && this.window.getFrames().size()>1 && window.getFrames().contains(activeFrame)) {
            int initialWindowLength = this.window.getFrames().size();
            int currentIndex = this.window.findFrame(activeFrame);
            this.window.deleteFrame(activeFrame);

            if (currentIndex < initialWindowLength-1) { //If the deleted frame is not the last frame, the active frame is set to its next frame
                activeFrame = this.window.getFrames().get(currentIndex);
            } else {                                    //Else, set the active frame to the one before the deleted frame
                activeFrame = this.window.getFrames().get(currentIndex-1);
            }

            //Remove the current active component out of the frame list
            frameListComponentContainer.removeView(activeFrameListComponent);

            //Set the new active component to the component that hosts the active frame
            for (int componentIndex = 0; componentIndex < initialWindowLength-1; componentIndex++) {
                if ( ((FrameListComponent) frameListComponentContainer
                        .getChildAt(componentIndex))
                        .getMiniHostFrame()
                        .equals(activeFrame)) {
                    activeFrameListComponent = (FrameListComponent) frameListComponentContainer.getChildAt(componentIndex);
                }
            }

            //Then redraw that component to be highlighted in green
            activeFrameListComponent.invalidate();

            //Redraw active frame on main screen
            this.drawingCanvas.setHostFrame(activeFrame);
            this.drawingCanvas.invalidate();

        } else {
            //If "Delete frame" fails, display a warning toast about the problem

            if (this.window.getFrames().size() == 1) {
                this.activeFrame.clearFigure();
                this.drawingCanvas.invalidate();
                this.activeFrameListComponent.invalidate();
                Toast.makeText(MainActivity.this, "Window content is empty", Toast.LENGTH_SHORT)
                        .show();
            } else if (animationRunning) {
                Toast.makeText(MainActivity.this, "Animation is still running", Toast.LENGTH_SHORT)
                        .show();
            } else if (!window.getFrames().contains(activeFrame)) {
                Toast.makeText(MainActivity.this, "Please choose a frame to delete", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    //To check whether user wants to show name of the figures
    public void showName(View v) {
        if (figureNameCheckBox.isChecked()) {
            this.drawingCanvas.setFigureNameShowed(true);
        } else {
            this.drawingCanvas.setFigureNameShowed(false);
        }
        drawingCanvas.invalidate();
    }


    //################################## Figure control panel #####################################

    public void popUpFigureControlPanel(View v) {
        if (!animationRunning) {
            popupWindow.setContentView(figureControlLayout);
            popupWindow.showAtLocation(figureControlLayout, Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            Toast.makeText(MainActivity.this, "Please stop animation first", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    //Add new figure to the current active frame
    public void addFigure(View v) {
        String figureName = figureNameText.getText().toString();

        if (!animationRunning
                && !figureName.equals("")
                && !figureName.equals("Default")
                && !activeFrame.getAllFiguresNames().contains(figureName)
                && this.window.getFrames().contains(activeFrame)) {

            String figureType = figureTypesGroup.getSelectedItem().toString();

            Figure addedFigure = new Figure(figureType, figureName);

            //Set active figure to the newly added figure
            activeFigure = addedFigure;

            this.activeFrame.addFigure(addedFigure);

            //Redraw main screen and current active FrameListComponent
            this.drawingCanvas.invalidate();
            this.activeFrameListComponent.invalidate();

        } else {
            //If "Add figure" fails, display a warning toast about the problem

            if (animationRunning) {
                Toast.makeText(MainActivity.this, "Animation is still running", Toast.LENGTH_SHORT)
                        .show();
            } else if (figureName.equals("")) {
                Toast.makeText(MainActivity.this, "Figure's name cannot be empty", Toast.LENGTH_SHORT)
                        .show();
            } else if (figureName.equals("Default")) {
                Toast.makeText(MainActivity.this, "Please use other names", Toast.LENGTH_SHORT)
                        .show();
            } else if (activeFrame.getAllFiguresNames().contains(figureName)) {
                Toast.makeText(MainActivity.this, "Figure's name already exists", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this, "Please choose a frame first", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    //Delete the chosen figure from the current frame
    public void deleteFigure(View v) {
        if (!animationRunning
                && this.window.getFrames().contains(activeFrame)
                && this.activeFrame.getFigures().contains(activeFigure)) {

            this.activeFrame.deleteFigure(this.activeFigure);

            //Redraw main screen and current active FrameListComponent
            this.drawingCanvas.invalidate();
            this.activeFrameListComponent.invalidate();


        } else {
            //If "Delete figure" fails, display a warning toast about the problem

            if (animationRunning) {
                Toast.makeText(MainActivity.this, "Animation is still running", Toast.LENGTH_SHORT)
                        .show();
            } else if (!this.window.getFrames().contains(activeFrame)) {
                Toast.makeText(MainActivity.this, "Please choose a frame first", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this, "Please choose a figure to delete", Toast.LENGTH_SHORT)
                        .show();
            }

        }
    }


    //################################ Animation control panel ####################################

    public void popUpAnimationControlPanel(View v) {
        popupWindow.setContentView(animationControlLayout);
        popupWindow.showAtLocation(animationControlLayout, Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    //Start the animation between frames with button "START".
    public void startAnimation(View v) {
        if (!animationRunning && this.window.getFrames().size() > 1) {

            //Get animation information from user's input
            if (checkForPureNumbersString(animationSpeedInput.getText().toString())) {
                animationSpeed = (int) Integer.parseInt(animationSpeedInput.getText().toString());
            }

            if (checkForPureNumbersString(animationFrames.getText().toString())) {
                no_interpolatedFrames = (int) Integer.parseInt(animationFrames.getText().toString());
            }

            //Reset this variable so that when animation is started, it runs from the beginning.
            animationIndex = 0;

            interpolation = new ArrayList<>();

            //Add up all the interpolated frames between the official frames to the "interpolation" collection
            for (int frameIndex_onlyForStartAnimation = 0;
                 frameIndex_onlyForStartAnimation < window.getFrames().size() - 1;
                 frameIndex_onlyForStartAnimation++) {
                interpolation.addAll(window.interpolation(window.getFrames().get(frameIndex_onlyForStartAnimation),
                        window.getFrames().get(frameIndex_onlyForStartAnimation+1), no_interpolatedFrames));
            }

            //When user starts animation, no frame should be active
            activeFrame = new Frame();

            //The frames list is redraw to show that no frame is active
            activeFrameListComponent.invalidate();
            activeFrameListComponent = new FrameListComponent(this, activeFrame);


            //Start the thread "t"
            t = new Thread() {

                @Override
                public void run() {
                    while (!isInterrupted()) {

                        try {
                            Thread.sleep(animationSpeed);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Frame frame = interpolation.get(animationIndex);
                                    drawingCanvas.setHostFrame(frame);
                                    drawingCanvas.invalidate();
                                    animationIndex = (animationIndex + 1) % interpolation.size();

                                }
                            });
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            };
            t.start();

            //The state of the program is now "running"
            animationRunning = true;
        } else {
            //If "Starting animation" fails, display a warning toast about the problem

            if (this.animationRunning) {
                Toast.makeText(MainActivity.this, "Animation is already running", Toast.LENGTH_SHORT)
                        .show();
            } else if (this.window.getFrames().size() <= 1) {
                Toast.makeText(MainActivity.this, "There is only one frame", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    //Stop the animation with button "STOP".
    public void stopAnimation(View v) {

        if (animationRunning) {

            t.interrupt();

            //The program's state is now "not running"
            animationRunning = false;

        } else {
            //If "Stopping animation" fails, display a warning toast about the problem
            Toast.makeText(MainActivity.this, "Animation is not running", Toast.LENGTH_SHORT)
                    .show();
        }


    }


    //################################ Background control panel ###################################

    public void popUpBackgroundControlPanel(View v) {
        if (!animationRunning) {
            popupWindow.setContentView(backgroundControlLayout);
            popupWindow.showAtLocation(backgroundControlLayout, Gravity.CENTER_HORIZONTAL, 0, 0);
        } else {
            Toast.makeText(MainActivity.this, "Please stop animation first", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    //Clear background of the active frame
    public void clearBackground(View v) {
        if (!animationRunning && this.window.getFrames().contains(activeFrame)) {
            this.activeFrame.setBitmapBackground(null);
            this.drawingCanvas.invalidate();
        } else {
            //If "Clear background" fails, display a warning toast about the problem

            if (animationRunning) {
                Toast.makeText(MainActivity.this, "Animation is still running", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this, "Please choose a frame first", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }

    //Choose photo for the active frame
    public void choosePhoto(View v) {
        if (!animationRunning && window.getFrames().contains(activeFrame)) {
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {

                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);

            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        } else {
            if (animationRunning) {
                Toast.makeText(MainActivity.this, "Animation is still running", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(MainActivity.this, "Please choose a frame first", Toast.LENGTH_SHORT)
                        .show();
            }
        }

    }


    //################################ Instruction presentation ###################################

    public void popUpInstruction(View v) {
        page = new InstructionPage(this, screenWidth, screenHeight);
        ViewGroup.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        addContentView(page, params1);
        page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPage = page.getPage();
                if (currentPage == 6) {
                    page.setPage(1);
                    ((ViewGroup) page.getParent()).removeView(page);
                } else {
                    page.setPage(currentPage+1);
                    page.invalidate();
                }

            }
        });
    }





    ///////////////////////////////////////////////////////////////////////////////////////////////
    //MAIN ACTIVITIES START HERE:
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);





        //#################################### LOGIC PART #########################################

        //_________________________________________________________________________________________
        //SET UP HELPER VARIABLES

        //Initial logic components set up
        frame.addFigure(human);
        window.addFrame(frame);

        //List to check if a string can be converted to a number
        numberChar.add('0'); numberChar.add('1'); numberChar.add('2'); numberChar.add('3'); numberChar.add('4');
        numberChar.add('5'); numberChar.add('6'); numberChar.add('7'); numberChar.add('8'); numberChar.add('9');

        //Set up to set screen size information: Width and Height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        //Layout parameters for pop-up control panel
        params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        //Pop-up control panel initialization
        popupWindow = new PopupWindow(this);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);



        //_________________________________________________________________________________________
        //Set the frame control panel and its elements:
        frameControlLayout = findViewById(R.id.frame_control_layout);
        frameControlLayout.setLayoutParams(params);

        //Figure name check box
        figureNameCheckBox = findViewById(R.id.figure_name_checkbox);


        //_________________________________________________________________________________________
        //Set the figure control panel and its elements:
        figureControlLayout = findViewById(R.id.figure_control_layout);
        figureControlLayout.setLayoutParams(params);

        //Figure type spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.figure_types_spinner_text, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        figureTypesGroup = findViewById(R.id.figures_type_spinner);
        figureTypesGroup.setAdapter(adapter);

        //Figure name edit text
        figureNameText = findViewById(R.id.figure_name_input_text);



        //_________________________________________________________________________________________
        //Set the animation control panel and its elements:
        animationControlLayout = findViewById(R.id.animation_control_layout);
        animationControlLayout.setLayoutParams(params);

        //Animation speed input text
        animationSpeedInput = findViewById(R.id.animation_speed_edit_text);

        //Number of interpolated frames input text
        animationFrames = findViewById(R.id.interpolated_frames_edit_text);



        //_________________________________________________________________________________________
        //Set the background control panel and its elements:
        backgroundControlLayout = findViewById(R.id.background_control_layout);
        backgroundControlLayout.setLayoutParams(params);



        //_________________________________________________________________________________________
        //Set up and remove control panels from zero-sized container to use them for pop-up
        container = findViewById(R.id.container);
        container.removeView(frameControlLayout);
        container.removeView(figureControlLayout);
        container.removeView(animationControlLayout);
        container.removeView(backgroundControlLayout);





        //################################### DRAWING PART ########################################

        //_________________________________________________________________________________________
        //SET UP SURROUNDING FRAME
        mainScreenLayout = findViewById(R.id.main_screen_layout);
        theme = new DrawingCanvas(this, emptyFrameForTheme);
        theme.setBackgroundColor(Color.GRAY);
        mainScreenLayout.addView(theme);



        //_________________________________________________________________________________________
        //SET UP MAIN UI SCREEN
        drawingCanvas = new DrawingCanvas(this, frame);
        drawingCanvas.setBackgroundColor(Color.WHITE);
        drawingCanvas.setPivotX((float) (screenWidth * 1/100));
        drawingCanvas.setPivotY(screenHeight * 1/100);
        drawingCanvas.setScaleX((float) 0.7);
        drawingCanvas.setScaleY((float) 0.7);
        addContentView(drawingCanvas, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT));



        //_________________________________________________________________________________________
        //SET UP FRAME LIST CANVAS

        //A layout stores all FrameListComponent(s)
        frameListComponentContainer = findViewById(R.id.frame_component_layout);

        //Initial active FrameListComponent
        activeFrameListComponent = new FrameListComponent(this, frame);

        frameListComponentContainer.addView(activeFrameListComponent);

    }
    //Method "OnCreate" ends here.
    ///////////////////////////////////////////////////////////////////////////////////////////////





    ///////////////////////////////////////////////////////////////////////////////////////////////
    //CHOOSING PHOTO ACTIVITY START HERE:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if (resultCode == RESULT_OK && !animationRunning && intent != null) {
            Uri targetUri = intent.getData();
            try {
                backgroundBitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                activeFrame.setBitmapBackground(backgroundBitmap);
                drawingCanvas.invalidate();
            } catch (FileNotFoundException e) {
                //If "Set background" fails, display a warning toast about the problem
                Toast.makeText(MainActivity.this, "Image not found", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            if (animationRunning) {
                Toast.makeText(MainActivity.this, "Animation is still running", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    //Method "OnActivityResult" ends here.
    ///////////////////////////////////////////////////////////////////////////////////////////////

}