package org.firstinspires.ftc.team28420;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp(name = "dribbler test teleop")
public class DribblerTestTeleOp extends LinearOpMode {

    DcMotorEx dribbler;
    DcMotorEx sortMotor;
    DcMotorEx pushMotor1;
    DcMotorEx pushMotor2;

    @Override
    public void runOpMode() throws InterruptedException {
        dribbler = hardwareMap.get(DcMotorEx.class, "dribbler");
        sortMotor = hardwareMap.get(DcMotorEx.class, "sortMotor");
        pushMotor1 = hardwareMap.get(DcMotorEx.class, "pushMotor1");
        pushMotor2 = hardwareMap.get(DcMotorEx.class, "pushMotor2");

        dribbler.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dribbler.setDirection(DcMotorSimple.Direction.REVERSE);

        sortMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        pushMotor1.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pushMotor2.setDirection(DcMotorSimple.Direction.REVERSE);

        pushMotor2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        waitForStart();

        double sortSpeed = 0;

        while (opModeIsActive()) {
            dribbler.setVelocity(-gamepad1.left_trigger * 4600);
            sortMotor.setVelocity(sortSpeed);
            pushMotor1.setVelocity(gamepad1.right_trigger * 4600);
            pushMotor2.setVelocity(gamepad1.right_trigger * 4600);

            if (gamepad1.dpad_up) {
                sortSpeed += 10;
            } else if (gamepad1.dpad_down) {
                sortSpeed -= 10;
            }

            telemetry.addData("sort speed", sortSpeed);
            telemetry.update();
        }
    }
}
