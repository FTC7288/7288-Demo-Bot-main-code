package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class TankDrivetrain extends OpMode {
    private DcMotor frontright , backright ;
    double throttle;
    double spin;
    @Override
    public void init() {
        backright = hardwareMap.get(DcMotor.class,"DriveL");
        frontright = hardwareMap.get(DcMotor.class, "DriveR");


        frontright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backright.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


        frontright.setDirection(DcMotorSimple.Direction.REVERSE);
        backright.setDirection(DcMotorSimple.Direction.FORWARD);
        ;

        frontright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backright.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }


    @Override
    public void loop() {
        spin = gamepad1.left_stick_x;
        throttle = -gamepad1.left_stick_y;

        double leftPower = throttle + spin ;
        double rightPower = throttle - spin;
        double largest = Math.max(Math.abs(leftPower) ,Math.abs(rightPower));

        if(largest > 1.0){
            leftPower /= largest;
            rightPower /= largest;
        }
        frontright.setPower(leftPower);
        backright.setPower(rightPower);

    }


}