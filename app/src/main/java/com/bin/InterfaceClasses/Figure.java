package com.bin.test_11;

import java.lang.reflect.Array;
import java.util.ArrayList;
import android.util.Pair;

public class Figure {
    /*
    Typical stick man:

           head
          |    |
           neck
        /    |   \
     arm_l   |   arm_r
     /       |      \
   hand_l  bottom  hand_r
           /    \
       knee_l   knee_r
         /        \
      foot_l     foot_r

     */

    private String type;
    private String name;

    //Components of this figure
    private ArrayList<Joint> joints = new ArrayList<Joint>();
    private ArrayList<Stick> sticks = new ArrayList<Stick>();

    //______________________________________________________________________________________________
    //The joints of this figure

    // * ====================The common part of all human stick figure=============================
    private Joint head   = new Joint("head",   true);
    private Joint neck   = new Joint("neck",   true);
    private Joint arm_l  = new Joint("arm_l",  true);
    private Joint arm_r  = new Joint("arm_r",  true);
    private Joint hand_l = new Joint("hand_l", true);
    private Joint hand_r = new Joint("hand_r", true);
    private Joint bottom = new Joint("bottom", true);
    private Joint knee_l = new Joint("knee_l", true);
    private Joint knee_r = new Joint("knee_r", true);
    private Joint foot_l = new Joint("foot_l", true);
    private Joint foot_r = new Joint("foot_r", true);

    //Additional features for figure "Ninja warrior":
    public Joint eye_l_1 = new Joint("eye_l_1", false);
    public Joint eye_l_2 = new Joint("eye_l_2", false);
    public Joint eye_l_3 = new Joint("eye_l_3", false);

    public Joint lace_1 = new Joint("lace_1", false);
    public Joint lace_2 = new Joint("lace_2", false);
    public Joint lace_3 = new Joint("lace_3", false);
    public Joint lace_4 = new Joint("lace_4", false);
    public Joint lace_5 = new Joint("lace_5", false);


    //Additional features for figure "Dark warrior"
    public Joint ear_l_1 = new Joint("ear_l_1", false);
    public Joint ear_l_2 = new Joint("ear_l_2", false);
    public Joint ear_l_3 = new Joint("ear_l_3", false);



    // * ==================The common part of all tool stick figure================================

    public Joint spinning = new Joint("spinning", true);
    public Joint movingUnit = new Joint("moving_unit", true);

    public Joint tool_1 = new Joint("tool_1", false);
    public Joint tool_2 = new Joint("tool_2", false);
    public Joint tool_3 = new Joint("tool_3", false);
    public Joint tool_4 = new Joint("tool_4", false);
    public Joint tool_5 = new Joint("tool_5", false);
    public Joint tool_6 = new Joint("tool_6", false);
    public Joint tool_7 = new Joint("tool_7", false);
    public Joint tool_8 = new Joint("tool_8", false);
    public Joint tool_9 = new Joint("tool_9", false);
    public Joint tool_10 = new Joint("tool_10", false);
    public Joint tool_11 = new Joint("tool_11", false);
    public Joint tool_12 = new Joint("tool_12", false);
    public Joint tool_13 = new Joint("tool_13", false);
    public Joint tool_14 = new Joint("tool_14", false);
    public Joint tool_15 = new Joint("tool_15", false);
    public Joint tool_16 = new Joint("tool_16", false);
    public Joint tool_17 = new Joint("tool_17", false);
    public Joint tool_18 = new Joint("tool_18", false);
    public Joint tool_19 = new Joint("tool_19", false);
    public Joint tool_20 = new Joint("tool_20", false);
    public Joint tool_21 = new Joint("tool_21", false);
    public Joint tool_22 = new Joint("tool_22", false);
    public Joint tool_23 = new Joint("tool_23", false);
    public Joint tool_24 = new Joint("tool_24", false);
    public Joint tool_25 = new Joint("tool_25", false);
    public Joint tool_26 = new Joint("tool_26", false);
    public Joint tool_27 = new Joint("tool_27", false);
    public Joint tool_28 = new Joint("tool_28", false);
    public Joint tool_29 = new Joint("tool_29", false);
    public Joint tool_30 = new Joint("tool_30", false);
    public Joint tool_31 = new Joint("tool_31", false);
    public Joint tool_32 = new Joint("tool_32", false);
    public Joint tool_33 = new Joint("tool_33", false);
    public Joint tool_34 = new Joint("tool_34", false);
    public Joint tool_35 = new Joint("tool_35", false);
    public Joint tool_36 = new Joint("tool_36", false);
    public Joint tool_37 = new Joint("tool_37", false);
    public Joint tool_38 = new Joint("tool_38", false);
    public Joint tool_39 = new Joint("tool_39", false);
    public Joint tool_40 = new Joint("tool_40", false);
    public Joint tool_41 = new Joint("tool_41", false);
    public Joint tool_42 = new Joint("tool_42", false);
    public Joint tool_43 = new Joint("tool_43", false);
    public Joint tool_44 = new Joint("tool_44", false);
    public Joint tool_45 = new Joint("tool_45", false);
    public Joint tool_46 = new Joint("tool_46", false);
    public Joint tool_47 = new Joint("tool_47", false);
    public Joint tool_48 = new Joint("tool_48", false);
    public Joint tool_49 = new Joint("tool_49", false);
    public Joint tool_50 = new Joint("tool_50", false);


    //______________________________________________________________________________________________
    //The sticks of this figure

    // * The common part of all human stick figure
    private Stick body = new Stick("body", this.neck, this.bottom);

    private Stick arm_l_stick = new Stick("arm_l", this.neck, this.arm_l);
    private Stick arm_r_stick = new Stick("arm_r", this.neck, this.arm_r);

    private Stick hand_l_stick = new Stick("hand_l", this.arm_l, this.hand_l);
    private Stick hand_r_stick = new Stick("hand_r", this.arm_r, this.hand_r);

    private Stick knee_l_stick = new Stick("knee_l", this.bottom, this.knee_l);
    private Stick knee_r_stick = new Stick("knee_r", this.bottom, this.knee_r);

    private Stick foot_l_stick = new Stick("foot_l", this.knee_l, this.foot_l);
    private Stick foot_r_stick = new Stick("foot_r", this.knee_r, this.foot_r);


    ArrayList<String> humanFigureTypes = new ArrayList<>();

    ArrayList<String> toolFigureTypes  = new ArrayList<>();

    public Figure(String inputType, String inputName) {
        this.type = inputType;
        this.name = inputName;

        //Set up figure's components - for human figure ===========================================
        humanFigureTypes.add("Stick Man");
        humanFigureTypes.add("Ninja Warrior");
        humanFigureTypes.add("Dark Warrior");

        if (humanFigureTypes.contains(this.type)) {
            //Adding the joints to "joints"
            this.joints.add(bottom);

            this.joints.add(neck);

            this.joints.add(head);

            this.joints.add(arm_l);  this.joints.add(arm_r);
            this.joints.add(hand_l); this.joints.add(hand_r);

            this.joints.add(knee_l); this.joints.add(knee_r);
            this.joints.add(foot_l); this.joints.add(foot_r);

            if (this.getType().equals("Ninja Warrior") || this.getType().equals("Dark Warrior")) {
                this.joints.add(eye_l_1); this.joints.add(eye_l_2); this.joints.add(eye_l_3);
                this.eye_l_1.moveTo(470+70, 250);
                this.eye_l_2.moveTo(460+70, 245);
                this.eye_l_3.moveTo(465+70, 255);
            }

            if (this.getType().equals("Ninja Warrior")) {
                this.joints.add(lace_1); this.joints.add(lace_2); this.joints.add(lace_3);
                this.joints.add(lace_4); this.joints.add(lace_5);
                this.lace_1.moveTo(580, 210);
                this.lace_2.moveTo(420, 210);
                this.lace_3.moveTo(370, 350);
                this.lace_4.moveTo(350, 300);
                this.lace_5.moveTo(440, 210);
            }

            if (this.getType().equals("Dark Warrior")) {
                this.joints.add(ear_l_1); this.joints.add(ear_l_2); this.joints.add(ear_l_3);
                this.ear_l_1.moveTo(480, 160);
                this.ear_l_2.moveTo(455, 130);
                this.ear_l_3.moveTo(455, 255);
            }

            //Move the joints to their corresponding location
            this.head.moveTo(500, 180);
            this.neck.moveTo(500, 320);
            this.bottom.moveTo(500, 600);

            this.arm_l.moveTo(420, 480);
            this.arm_r.moveTo(580, 480);

            this.hand_l.moveTo(380, 620);
            this.hand_r.moveTo(620, 620);

            this.knee_l.moveTo(450, 770);
            this.knee_r.moveTo(550, 770);

            this.foot_l.moveTo(420, 940);
            this.foot_r.moveTo(570, 940);


            //Adding the sticks to "sticks"
            this.sticks.add(this.body);

            this.sticks.add(this.arm_l_stick);
            this.sticks.add(this.arm_r_stick);

            this.sticks.add(this.hand_l_stick);
            this.sticks.add(this.hand_r_stick);

            this.sticks.add(this.knee_l_stick);
            this.sticks.add(this.knee_r_stick);

            this.sticks.add(this.foot_l_stick);
            this.sticks.add(this.foot_r_stick);


            /*
            //Move the figure straight up
            this.moveMan(this.neck, 600, 600);
            this.moveMan(this.knee_l, 300, 550);
            this.moveMan(this.knee_r, 300, 650);

             */
        }


        //Set up figure's component - for tool figure =============================================
        toolFigureTypes.add("Sword");
        toolFigureTypes.add("Gun");
        toolFigureTypes.add("Trident");
        toolFigureTypes.add("Wooden Shield");
        toolFigureTypes.add("Metal Shield");

        if (toolFigureTypes.contains(this.getType())) {

            this.joints.add(this.movingUnit);
            this.joints.add(this.spinning);


            //Drawing the sword -------------------------------------------------
            if (this.getType().equals("Sword")) {
                this.spinning.moveTo(200, 1000);
                this.movingUnit.moveTo(200, 830);

                //The steel part
                this.tool_1.moveTo(200, 200);
                this.tool_2.moveTo(200, 800);
                this.tool_3.moveTo(150, 300);
                this.tool_4.moveTo(150, 800);
                this.tool_5.moveTo(250, 300);
                this.tool_6.moveTo(250, 800);

                //The holding part
                this.tool_7.moveTo(120, 800);
                this.tool_8.moveTo(100, 750);
                this.tool_9.moveTo(80, 850);
                this.tool_10.moveTo(320, 850);
                this.tool_11.moveTo(300, 750);
                this.tool_12.moveTo(280, 800);

                this.joints.add(this.tool_1);
                this.joints.add(this.tool_2);
                this.joints.add(this.tool_3);
                this.joints.add(this.tool_4);
                this.joints.add(this.tool_5);
                this.joints.add(this.tool_6);
                this.joints.add(this.tool_7);
                this.joints.add(this.tool_8);
                this.joints.add(this.tool_9);
                this.joints.add(this.tool_10);
                this.joints.add(this.tool_11);
                this.joints.add(this.tool_12);
            }

            //Drawing the metal shield
            else if (this.getType().equals("Metal Shield")) {

                this.movingUnit.moveTo(500, 500);
                this.spinning.moveTo(300, 500);

                this.tool_1.moveTo(650, 500);
                this.tool_2.moveTo(650, 580);
                this.tool_3.moveTo(550, 550);
                this.tool_4.moveTo(550, 650);
                this.tool_5.moveTo(500, 600);

                this.tool_6.moveTo(650, 420);
                this.tool_7.moveTo(550, 450);
                this.tool_8.moveTo(550, 350);
                this.tool_9.moveTo(500, 400);

                this.tool_10.moveTo(645, 425);
                this.tool_11.moveTo(645, 575);
                this.tool_12.moveTo(545, 645);
                this.tool_13.moveTo(545, 355);
                this.tool_14.moveTo(415, 570);
                this.tool_15.moveTo(415, 430);

                this.tool_16.moveTo(665, 390);
                this.tool_17.moveTo(550, 415);

                this.tool_18.moveTo(560, 310);
                this.tool_19.moveTo(280, 485);

                this.joints.add(this.tool_1);
                this.joints.add(this.tool_2);
                this.joints.add(this.tool_3);
                this.joints.add(this.tool_4);
                this.joints.add(this.tool_5);
                this.joints.add(this.tool_6);
                this.joints.add(this.tool_7);
                this.joints.add(this.tool_8);
                this.joints.add(this.tool_9);
                this.joints.add(this.tool_10);
                this.joints.add(this.tool_11);
                this.joints.add(this.tool_12);
                this.joints.add(this.tool_13);
                this.joints.add(this.tool_14);
                this.joints.add(this.tool_15);
                this.joints.add(this.tool_16);
                this.joints.add(this.tool_17);
                this.joints.add(this.tool_18);
                this.joints.add(this.tool_19);

                this.moveMan(this.spinning, 480, 600);

            }

            //Drawing the shield
            else if (this.getType().equals("Wooden Shield")) {
                //Outer and inner circle center
                this.movingUnit.moveTo(400, 300);

                //Spinning unit
                this.spinning.moveTo(230, 300);

                //Scratch 1
                this.tool_1.moveTo(500, 200);
                this.tool_2.moveTo(320, 200);

                //Scratch 2
                this.tool_3.moveTo(480, 250);
                this.tool_4.moveTo(300, 250);

                //Scratch 3
                this.tool_5.moveTo(500, 400);
                this.tool_6.moveTo(360, 400);

                //Scratch hole
                this.tool_7.moveTo(490, 320);
                this.tool_8.moveTo(460, 320);

                //Arrow steel
                this.tool_9.moveTo(540, 380);
                this.tool_10.moveTo(490, 360);
                this.tool_11.moveTo(470, 400);

                //The arrow body part
                this.tool_12.moveTo(580, 550);

                //Arrow scratch
                this.tool_13.moveTo(530, 320);
                this.tool_14.moveTo(420, 320);

                //Arrow tail
                this.tool_15.moveTo(630, 570);
                this.tool_16.moveTo(660, 630);
                this.tool_17.moveTo(610, 600);
                this.tool_18.moveTo(600, 655);
                this.tool_19.moveTo(570, 600);

                this.tool_20.moveTo(570, 300);
                this.tool_21.moveTo(470, 145);
                this.tool_22.moveTo(470, 455);
                this.tool_23.moveTo(290, 430);
                this.tool_24.moveTo(290, 170);


                this.joints.add(this.tool_1);
                this.joints.add(this.tool_2);
                this.joints.add(this.tool_3);
                this.joints.add(this.tool_4);
                this.joints.add(this.tool_5);
                this.joints.add(this.tool_6);
                this.joints.add(this.tool_7);
                this.joints.add(this.tool_8);
                this.joints.add(this.tool_9);
                this.joints.add(this.tool_10);
                this.joints.add(this.tool_11);
                this.joints.add(this.tool_12);
                this.joints.add(this.tool_13);
                this.joints.add(this.tool_14);
                this.joints.add(this.tool_15);
                this.joints.add(this.tool_16);
                this.joints.add(this.tool_17);
                this.joints.add(this.tool_18);
                this.joints.add(this.tool_19);
                this.joints.add(this.tool_20);
                this.joints.add(this.tool_21);
                this.joints.add(this.tool_22);
                this.joints.add(this.tool_23);
                this.joints.add(this.tool_24);

                this.moveMan(this.spinning, 410, 500);
            }

            //Drawing the trident
            else if (this.getType().equals("Trident")) {

                this.spinning.moveTo(400, 170);
                this.movingUnit.moveTo(400, 970);

                //================================ Real part ======================================
                //The body part
                this.tool_1.moveTo(400, 200);
                this.tool_2.moveTo(400, 900);



                //The top steel part
                this.tool_3.moveTo(400, 950);
                this.tool_4.moveTo(500, 1050);
                this.tool_5.moveTo(500, 1040);
                this.tool_6.moveTo(500, 1180);

                //The top stab part
                this.tool_25.moveTo(500, 1180);
                this.tool_26.moveTo(550, 1130);
                this.tool_27.moveTo(500, 1280);
                this.tool_28.moveTo(450, 1130);



                //The middle steel part
                this.tool_7.moveTo(400, 900);
                this.tool_8.moveTo(400, 1250);

                //The middle stab part
                this.tool_29.moveTo(500-100, 1180+70);
                this.tool_30.moveTo(550-100, 1130+70);
                this.tool_31.moveTo(500-100, 1280+70);
                this.tool_32.moveTo(450-100, 1130+70);



                //The bottom steel part
                this.tool_9.moveTo(400, 950);
                this.tool_10.moveTo(300, 1050);
                this.tool_11.moveTo(300, 1040);
                this.tool_12.moveTo(300, 1180);

                //The bottom stab part
                this.tool_33.moveTo(500-200, 1180);
                this.tool_34.moveTo(550-200, 1130);
                this.tool_35.moveTo(500-200, 1280);
                this.tool_36.moveTo(450-200, 1130);



                //The tail part
                this.tool_43.moveTo(450, 170);
                this.tool_44.moveTo(400, 100);
                this.tool_45.moveTo(350, 170);
                this.tool_46.moveTo(400, 230);

                //================================== Shadow =======================================
                double shadowStepBack = 15;
                //The body part
                this.tool_13.moveTo(400-shadowStepBack, 200);
                this.tool_14.moveTo(400-shadowStepBack, 900);



                //The top steel part
                this.tool_15.moveTo(400-shadowStepBack, 950);
                this.tool_16.moveTo(500-shadowStepBack, 1050);
                this.tool_17.moveTo(500-shadowStepBack, 1040);
                this.tool_18.moveTo(500-shadowStepBack, 1180);

                //The top stab part
                this.tool_37.moveTo(500-shadowStepBack, 1280);
                this.tool_38.moveTo(450-shadowStepBack, 1130);


                //The middle steel part
                this.tool_19.moveTo(400-shadowStepBack, 900);
                this.tool_20.moveTo(400-shadowStepBack, 1250);

                //The middle stab part
                this.tool_39.moveTo(500-100-shadowStepBack, 1280+70);
                this.tool_40.moveTo(450-100-shadowStepBack, 1130+70);



                //The bottom steel part
                this.tool_21.moveTo(400-shadowStepBack, 950);
                this.tool_22.moveTo(300-shadowStepBack, 1050);
                this.tool_23.moveTo(300-shadowStepBack, 1040);
                this.tool_24.moveTo(300-shadowStepBack, 1180);

                //The bottom stab part
                this.tool_41.moveTo(500-200-shadowStepBack, 1280);
                this.tool_42.moveTo(450-200-shadowStepBack, 1130);



                //The tail part
                this.tool_47.moveTo(400-shadowStepBack, 100);
                this.tool_48.moveTo(350-shadowStepBack, 170);
                this.tool_49.moveTo(400-shadowStepBack, 230);




                this.joints.add(this.tool_1);
                this.joints.add(this.tool_2);
                this.joints.add(this.tool_3);
                this.joints.add(this.tool_4);
                this.joints.add(this.tool_5);
                this.joints.add(this.tool_6);
                this.joints.add(this.tool_7);
                this.joints.add(this.tool_8);
                this.joints.add(this.tool_9);
                this.joints.add(this.tool_10);
                this.joints.add(this.tool_11);
                this.joints.add(this.tool_12);
                this.joints.add(this.tool_13);
                this.joints.add(this.tool_14);
                this.joints.add(this.tool_15);
                this.joints.add(this.tool_16);
                this.joints.add(this.tool_17);
                this.joints.add(this.tool_18);
                this.joints.add(this.tool_19);
                this.joints.add(this.tool_20);
                this.joints.add(this.tool_21);
                this.joints.add(this.tool_22);
                this.joints.add(this.tool_23);
                this.joints.add(this.tool_24);
                this.joints.add(this.tool_25);
                this.joints.add(this.tool_26);
                this.joints.add(this.tool_27);
                this.joints.add(this.tool_28);
                this.joints.add(this.tool_29);
                this.joints.add(this.tool_30);
                this.joints.add(this.tool_31);
                this.joints.add(this.tool_32);
                this.joints.add(this.tool_33);
                this.joints.add(this.tool_34);
                this.joints.add(this.tool_35);
                this.joints.add(this.tool_36);
                this.joints.add(this.tool_37);
                this.joints.add(this.tool_38);
                this.joints.add(this.tool_39);
                this.joints.add(this.tool_40);
                this.joints.add(this.tool_41);
                this.joints.add(this.tool_42);
                this.joints.add(this.tool_43);
                this.joints.add(this.tool_44);
                this.joints.add(this.tool_45);
                this.joints.add(this.tool_46);
                this.joints.add(this.tool_47);
                this.joints.add(this.tool_48);
                this.joints.add(this.tool_49);

                this.moveMan(this.movingUnit, 900, 500);
                this.moveMan(this.spinning, 100, 500);
            }

            //Drawing the gun ----------------------------------------------------
            else if (this.getType().equals("Gun")) {

                this.spinning.moveTo(665, 225);
                this.movingUnit.moveTo(665, 630);

                //The tail part
                this.tool_1.moveTo(580, 200);
                this.tool_2.moveTo(700, 200);
                this.tool_3.moveTo(700, 400);
                this.tool_4.moveTo(630, 400);
                this.tool_5.moveTo(630, 350);

                //The lower body part
                this.tool_6.moveTo(720, 400);
                this.tool_7.moveTo(720, 600);
                this.tool_8.moveTo(700, 630);
                this.tool_9.moveTo(630, 630);
                this.tool_10.moveTo(610, 600);
                this.tool_11.moveTo(580, 630);
                this.tool_12.moveTo(560, 560);
                this.tool_13.moveTo(610, 510);
                this.tool_14.moveTo(610, 450);
                this.tool_15.moveTo(630, 400);

                //The upper body part
                this.tool_16.moveTo(720, 420);
                this.tool_17.moveTo(720, 450);
                this.tool_18.moveTo(750, 470);
                this.tool_19.moveTo(750, 550);
                this.tool_20.moveTo(720, 570);
                this.tool_21.moveTo(720, 600);
                this.tool_22.moveTo(780, 570);
                this.tool_23.moveTo(780, 450);

                //The behind head part
                this.tool_24.moveTo(700, 630);
                this.tool_25.moveTo(730, 660);
                this.tool_26.moveTo(700, 950);
                this.tool_27.moveTo(630, 950);
                this.tool_28.moveTo(600, 660);
                this.tool_29.moveTo(630, 630);

                //The front head part
                this.tool_30.moveTo(665, 950);
                this.tool_31.moveTo(665, 1100);

                this.tool_32.moveTo(665, 1250);

                this.tool_33.moveTo(650, 960);
                this.tool_34.moveTo(750, 1050);
                this.tool_35.moveTo(650, 1050);

                //The amo part
                this.tool_36.moveTo(580, 590);
                this.tool_37.moveTo(520, 640);

                //The holding part
                this.tool_38.moveTo(530, 380);
                this.tool_39.moveTo(500, 460);
                this.tool_40.moveTo(530, 430);

                //The pulling part
                this.tool_41.moveTo(575, 580);
                this.tool_42.moveTo(575, 400);

                this.tool_43.moveTo(610, 460);
                this.tool_44.moveTo(585, 470);



                this.joints.add(this.tool_1);
                this.joints.add(this.tool_2);
                this.joints.add(this.tool_3);
                this.joints.add(this.tool_4);
                this.joints.add(this.tool_5);
                this.joints.add(this.tool_6);
                this.joints.add(this.tool_7);
                this.joints.add(this.tool_8);
                this.joints.add(this.tool_9);
                this.joints.add(this.tool_10);
                this.joints.add(this.tool_11);
                this.joints.add(this.tool_12);
                this.joints.add(this.tool_13);
                this.joints.add(this.tool_14);
                this.joints.add(this.tool_15);
                this.joints.add(this.tool_16);
                this.joints.add(this.tool_17);
                this.joints.add(this.tool_18);
                this.joints.add(this.tool_19);
                this.joints.add(this.tool_20);
                this.joints.add(this.tool_21);
                this.joints.add(this.tool_22);
                this.joints.add(this.tool_23);
                this.joints.add(this.tool_24);
                this.joints.add(this.tool_25);
                this.joints.add(this.tool_26);
                this.joints.add(this.tool_27);
                this.joints.add(this.tool_28);
                this.joints.add(this.tool_29);
                this.joints.add(this.tool_30);
                this.joints.add(this.tool_31);
                this.joints.add(this.tool_32);
                this.joints.add(this.tool_33);
                this.joints.add(this.tool_34);
                this.joints.add(this.tool_35);
                this.joints.add(this.tool_36);
                this.joints.add(this.tool_37);
                this.joints.add(this.tool_38);
                this.joints.add(this.tool_39);
                this.joints.add(this.tool_40);
                this.joints.add(this.tool_41);
                this.joints.add(this.tool_42);
                this.joints.add(this.tool_43);
                this.joints.add(this.tool_44);


                this.moveMan(this.spinning, 100, 620);

            }

        }

    }

    //Setters and Getters
    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Joint> getJoints() {
        return joints;
    }

    public ArrayList<Stick> getSticks() {
        return sticks;
    }

    //Method to move human type figures
    public void moveMan(Joint joint, double mousePosX, double mousePosY) {
        ArrayList<Joint> movingJoints = new ArrayList<Joint>();
        Joint pivot = new Joint("Default", false);

        if (joint.getName().equals("head")) {
            if (this.getType().equals("Ninja Warrior")) {
                movingJoints.add(eye_l_1);
                movingJoints.add(eye_l_2);
                movingJoints.add(eye_l_3);
                movingJoints.add(lace_1);
                movingJoints.add(lace_2);
                movingJoints.add(lace_3);
                movingJoints.add(lace_4);
                movingJoints.add(lace_5);

            } else if (this.getType().equals("Dark Warrior")) {
                movingJoints.add(eye_l_1);
                movingJoints.add(eye_l_2);
                movingJoints.add(eye_l_3);
                movingJoints.add(ear_l_1);
                movingJoints.add(ear_l_2);
                movingJoints.add(ear_l_3);
            }
            pivot = neck;
        }

        else if (joint.getName().equals("neck")) {
            if (this.getType().equals("Ninja Warrior")) {
                movingJoints.add(eye_l_1);
                movingJoints.add(eye_l_2);
                movingJoints.add(eye_l_3);
                movingJoints.add(lace_1);
                movingJoints.add(lace_2);
                movingJoints.add(lace_3);
                movingJoints.add(lace_4);
                movingJoints.add(lace_5);

            } else if (this.getType().equals("Dark Warrior")) {
                movingJoints.add(eye_l_1);
                movingJoints.add(eye_l_2);
                movingJoints.add(eye_l_3);
                movingJoints.add(ear_l_1);
                movingJoints.add(ear_l_2);
                movingJoints.add(ear_l_3);

            }
            movingJoints.add(head);
            movingJoints.add(arm_l);
            movingJoints.add(arm_r);
            movingJoints.add(hand_r);
            movingJoints.add(hand_l);

            pivot = bottom;
        }

        else if (joint.getName().equals("arm_l")) {
            movingJoints.add(hand_l);
            pivot = neck;
        }

        else if (joint.getName().equals("arm_r")) {
            movingJoints.add(hand_r);
            pivot = neck;
        }

        else if (joint.getName().equals("hand_l")) {
            pivot = arm_l;
        }

        else if (joint.getName().equals("hand_r")) {
            pivot = arm_r;
        }

        else if (joint.getName().equals("knee_l")) {
            movingJoints.add(foot_l);
            pivot = bottom;
        }

        else if (joint.getName().equals("knee_r")) {
            movingJoints.add(foot_r);
            pivot = bottom;
        }

        else if (joint.getName().equals("foot_l")) {
            pivot = knee_l;
        }

        else if (joint.getName().equals("foot_r")) {
            pivot = knee_r;
        }

        else if (joint.getName().equals("spinning")) {
            pivot = movingUnit;

            if (this.getType().equals("Sword")) {
                movingJoints.add(tool_1);
                movingJoints.add(tool_2);
                movingJoints.add(tool_3);
                movingJoints.add(tool_4);
                movingJoints.add(tool_5);
                movingJoints.add(tool_6);
                movingJoints.add(tool_7);
                movingJoints.add(tool_8);
                movingJoints.add(tool_9);
                movingJoints.add(tool_10);
                movingJoints.add(tool_11);
                movingJoints.add(tool_12);
            }

            else if (this.getType().equals("Gun")) {
                movingJoints.add(tool_1);
                movingJoints.add(tool_2);
                movingJoints.add(tool_3);
                movingJoints.add(tool_4);
                movingJoints.add(tool_5);
                movingJoints.add(tool_6);
                movingJoints.add(tool_7);
                movingJoints.add(tool_8);
                movingJoints.add(tool_9);
                movingJoints.add(tool_10);
                movingJoints.add(tool_11);
                movingJoints.add(tool_12);
                movingJoints.add(tool_13);
                movingJoints.add(tool_14);
                movingJoints.add(tool_15);
                movingJoints.add(tool_16);
                movingJoints.add(tool_17);
                movingJoints.add(tool_18);
                movingJoints.add(tool_19);
                movingJoints.add(tool_20);
                movingJoints.add(tool_21);
                movingJoints.add(tool_22);
                movingJoints.add(tool_23);
                movingJoints.add(tool_24);
                movingJoints.add(tool_25);
                movingJoints.add(tool_26);
                movingJoints.add(tool_27);
                movingJoints.add(tool_28);
                movingJoints.add(tool_29);
                movingJoints.add(tool_30);
                movingJoints.add(tool_31);
                movingJoints.add(tool_32);
                movingJoints.add(tool_33);
                movingJoints.add(tool_34);
                movingJoints.add(tool_35);
                movingJoints.add(tool_36);
                movingJoints.add(tool_37);
                movingJoints.add(tool_38);
                movingJoints.add(tool_39);
                movingJoints.add(tool_40);
                movingJoints.add(tool_41);
                movingJoints.add(tool_42);
                movingJoints.add(tool_43);
                movingJoints.add(tool_44);

            }

            else if (this.getType().equals("Trident")) {
                movingJoints.add(tool_1);
                movingJoints.add(tool_2);
                movingJoints.add(tool_3);
                movingJoints.add(tool_4);
                movingJoints.add(tool_5);
                movingJoints.add(tool_6);
                movingJoints.add(tool_7);
                movingJoints.add(tool_8);
                movingJoints.add(tool_9);
                movingJoints.add(tool_10);
                movingJoints.add(tool_11);
                movingJoints.add(tool_12);
                movingJoints.add(tool_13);
                movingJoints.add(tool_14);
                movingJoints.add(tool_15);
                movingJoints.add(tool_16);
                movingJoints.add(tool_17);
                movingJoints.add(tool_18);
                movingJoints.add(tool_19);
                movingJoints.add(tool_20);
                movingJoints.add(tool_21);
                movingJoints.add(tool_22);
                movingJoints.add(tool_23);
                movingJoints.add(tool_24);
                movingJoints.add(tool_25);
                movingJoints.add(tool_26);
                movingJoints.add(tool_27);
                movingJoints.add(tool_28);
                movingJoints.add(tool_29);
                movingJoints.add(tool_30);
                movingJoints.add(tool_31);
                movingJoints.add(tool_32);
                movingJoints.add(tool_33);
                movingJoints.add(tool_34);
                movingJoints.add(tool_35);
                movingJoints.add(tool_36);
                movingJoints.add(tool_37);
                movingJoints.add(tool_38);
                movingJoints.add(tool_39);
                movingJoints.add(tool_40);
                movingJoints.add(tool_41);
                movingJoints.add(tool_42);
                movingJoints.add(tool_43);
                movingJoints.add(tool_44);
                movingJoints.add(tool_45);
                movingJoints.add(tool_46);
                movingJoints.add(tool_47);
                movingJoints.add(tool_48);
                movingJoints.add(tool_49);
            }

            else if (this.getType().equals("Wooden Shield")) {
                movingJoints.add(tool_1);
                movingJoints.add(tool_2);
                movingJoints.add(tool_3);
                movingJoints.add(tool_4);
                movingJoints.add(tool_5);
                movingJoints.add(tool_6);
                movingJoints.add(tool_7);
                movingJoints.add(tool_8);
                movingJoints.add(tool_9);
                movingJoints.add(tool_10);
                movingJoints.add(tool_11);
                movingJoints.add(tool_12);
                movingJoints.add(tool_13);
                movingJoints.add(tool_14);
                movingJoints.add(tool_15);
                movingJoints.add(tool_16);
                movingJoints.add(tool_17);
                movingJoints.add(tool_18);
                movingJoints.add(tool_19);
                movingJoints.add(tool_20);
                movingJoints.add(tool_21);
                movingJoints.add(tool_22);
                movingJoints.add(tool_23);
                movingJoints.add(tool_24);
            }

            else if (this.getType().equals("Metal Shield")) {
                movingJoints.add(tool_1);
                movingJoints.add(tool_2);
                movingJoints.add(tool_3);
                movingJoints.add(tool_4);
                movingJoints.add(tool_5);
                movingJoints.add(tool_6);
                movingJoints.add(tool_7);
                movingJoints.add(tool_8);
                movingJoints.add(tool_9);
                movingJoints.add(tool_10);
                movingJoints.add(tool_11);
                movingJoints.add(tool_12);
                movingJoints.add(tool_13);
                movingJoints.add(tool_14);
                movingJoints.add(tool_15);
                movingJoints.add(tool_16);
                movingJoints.add(tool_17);
                movingJoints.add(tool_18);
                movingJoints.add(tool_19);
            }

        }

        else if (joint.getName().equals("bottom") || joint.getName().equals("moving_unit")) {
            double xVector = mousePosX - joint.getXCoordinate();
            double yVector = mousePosY - joint.getYCoordinate();

            for (int jointIndex = 0;  jointIndex < this.joints.size(); jointIndex++) {

                //Create a holder for the considered joint
                Joint temporaryJointHolder = this.joints.get(jointIndex);

                //Calculate new coordinates of the joints
                double newX = temporaryJointHolder.getXCoordinate() + xVector;
                double newY = temporaryJointHolder.getYCoordinate() + yVector;

                //Move each joint to the matched location of its original version
                temporaryJointHolder.moveTo(newX, newY);
                this.joints.set(jointIndex, temporaryJointHolder);
            }
        }

        if (pivot.getName() != "Default" &&
            !head.getCalculator().tooClose(mousePosX, mousePosY,
                                           pivot.getXCoordinate(),
                                           pivot.getYCoordinate())) {

            double initialJointX = joint.getXCoordinate();
            double initialJointY = joint.getYCoordinate();

            Pair<Double, Double> thisDraggingMotion = joint.draggingMotion(pivot,
                                                                           mousePosX,
                                                                           mousePosY);
            joint.moveTo(thisDraggingMotion.first, thisDraggingMotion.second);

            int inputJointIndex = this.joints.indexOf(joint);
            this.joints.set(inputJointIndex, joint);

            for (Joint j : movingJoints) {

                Pair<Double, Double> affectedMotion = j.betaMove(pivot,
                        initialJointX, initialJointY,
                        thisDraggingMotion.first, thisDraggingMotion.second);

                j.moveTo(affectedMotion.first, affectedMotion.second);

                int correspondingIndex = this.joints.indexOf(j);

                this.joints.set(correspondingIndex, j);
            }

        }

    }

}
