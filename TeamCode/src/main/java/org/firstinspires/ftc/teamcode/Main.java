package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;



@TeleOp
public class Main extends OpMode {

    static {System.loadLibrary("lib_loader");}
    LynxModule controlHub;
    DcMotor left, right, lift;
    double leftPow, rightPow, liftPow;
    boolean toggleFreakoutMode = false;

    int a = 10 , b = 10;





    @Override
    public void init() {
        left = hardwareMap.get(DcMotor.class, "left");
        right = hardwareMap.get(DcMotor.class, "right");
        lift = hardwareMap.get(DcMotor.class, "lift");
    }

    @Override
    public void loop() {

        if (gamepad2.yWasPressed() && !toggleFreakoutMode) {
            toggleFreakoutMode = true;
        }

        leftPow = -gamepad1.right_stick_x + gamepad1.left_stick_y;
        left.setPower((toggleFreakoutMode) ? Math.random() : leftPow );

        rightPow = -gamepad1.right_stick_x - gamepad1.left_stick_y ;
        right.setPower((toggleFreakoutMode) ? Math.random() : rightPow );



        if (gamepad1.a) {
            liftPow = 1;
        } else if (gamepad1.b) {
            liftPow = -1;
        } else {
            liftPow = 0;}

        lift.setPower((toggleFreakoutMode) ? Math.random() : liftPow / 2);

        if (gamepad1.dpadUpWasPressed()) {
            ++a;
        } else if (gamepad1.dpadDownWasPressed()) {
            --a;
        }





    }


}

