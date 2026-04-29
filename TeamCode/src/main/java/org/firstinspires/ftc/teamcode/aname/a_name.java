package org.firstinspires.ftc.teamcode.aname;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "a6robotcode")
public class a_name extends OpMode {
    private DcMotor FL_wheel, BL_wheel, FR_wheel, BR_wheel, Front_feeder, Middle_feeder, Shooter_2, Shooter_1;
    private CRServo Servo;
    @Override
    public void init() {
        FL_wheel = hardwareMap.get(DcMotor.class, "FLwheel");
        BL_wheel = hardwareMap.get(DcMotor.class, "BLwheel");
        FR_wheel = hardwareMap.get(DcMotor.class, "FRwheel");
        BR_wheel = hardwareMap.get(DcMotor.class, "BRwheel");
        Middle_feeder = hardwareMap.get(DcMotor.class, "Middlefeeder");
        Front_feeder = hardwareMap.get(DcMotor.class, "Frontfeeder");
        Shooter_1 = hardwareMap.get(DcMotor.class, "Shooter1");
        Shooter_2 = hardwareMap.get(DcMotor.class, "Shooter2");
        Servo = hardwareMap.get(CRServo.class, "Backfeeder");

        //.setDirection(DcMotorSimple.Direction.REVERSE);
        FL_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
        BL_wheel.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void loop() {
        if (gamepad1.a) {
            Front_feeder.setPower(-1);
            Middle_feeder.setPower(1);
        } else {
            Front_feeder.setPower(0);
            Middle_feeder.setPower(0);
        }


        if (gamepad1.b) {
            Servo.setPower(-1);
        } else {
            Servo.setPower(0);
        }

        if (gamepad1.dpad_up) {
            Shooter_1.setPower(1);
            Shooter_2.setPower(1);
        } else if (gamepad1.dpad_down){
            Shooter_1.setPower(0);
            Shooter_2.setPower(0);
        }

        double forward = gamepad1.left_stick_y;
        double turn = gamepad1.right_stick_x;
        double side = gamepad1.left_stick_x;


        FR_wheel.setPower(forward + turn - side);
        BR_wheel.setPower(forward + turn + side);
        FL_wheel.setPower(forward - turn + side);
        BL_wheel.setPower(forward - turn - side);



    }

}

