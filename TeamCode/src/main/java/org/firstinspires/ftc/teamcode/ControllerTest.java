package org.firstinspires.ftc.teamcode;

import com.pedropathing.follower.Follower;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagsWebcam;
import org.firstinspires.ftc.teamcode.mechanisms.Intake;
import org.firstinspires.ftc.teamcode.mechanisms.LEDIndicator;
import org.firstinspires.ftc.teamcode.mechanisms.Launcher;
import org.firstinspires.ftc.teamcode.mechanisms.MecanumDrive;
import org.firstinspires.ftc.teamcode.mechanisms.TurretServo;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
@TeleOp
public class ControllerTest extends OpMode {




    @Override
    public void init() {

    }

    @Override
    public void loop() {

        if(gamepad1.a){
            telemetry.addData("Pressed:" , "a");
        }

        if(gamepad1.b){
            telemetry.addData("Pressed:" , "b");
        }

        if(gamepad1.x){
            telemetry.addData("Pressed:" , "x");
        }

        if(gamepad1.y){
            telemetry.addData("Pressed:" , "y");
        }

        if(gamepad1.dpad_up){
            telemetry.addData("Pressed:" , "D-Pad Up");
        }

        if(gamepad1.dpad_down){
            telemetry.addData("Pressed:" , "D-Pad Down");
        }

        if(gamepad1.dpad_left){
            telemetry.addData("Pressed:" , "D-Pad Left");
        }

        if(gamepad1.dpad_right){
            telemetry.addData("Pressed:" , "D-Pad Right");
        }

        if(gamepad1.left_bumper){
            telemetry.addData("Pressed:" , "Left Bumper");
        }

        if(gamepad1.right_bumper){
            telemetry.addData("Pressed:" , "Right Bumper");
        }

        if(gamepad1.left_trigger != 0){
            telemetry.addData("Left Trigger:" , "Left Trigger");
        }

        if(gamepad1.right_trigger != 0){
            telemetry.addData("Right Trigger:" , "Right Trigger");
        }

        if(gamepad1.left_stick_x != 0 || gamepad1.left_stick_y != 0){
            telemetry.addLine("Left Stick X:" + gamepad1.left_stick_x);
            telemetry.addLine("Left Stick Y:" + gamepad1.left_stick_x);
        }

        if(gamepad1.right_stick_x != 0 || gamepad1.right_stick_y != 0){
            telemetry.addLine("Right Stick X:" + gamepad1.right_stick_x);
            telemetry.addLine("Right Stick y:" + gamepad1.right_stick_y);
        }

    }

}