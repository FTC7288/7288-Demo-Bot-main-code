package org.firstinspires.ftc.teamcode.auto.nonRoadRunner;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Constants;
import org.firstinspires.ftc.teamcode.Intake;
import org.firstinspires.ftc.teamcode.Shooter;


/*
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an Fh * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

@Autonomous(name="BackupShootRed", group="Autonomous")
//@Disabled
public class BackupShootRed extends LinearOpMode {

    // Declare OpMode members.
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;

    @Override
    public void runOpMode() throws InterruptedException {
        frontLeftDrive  = hardwareMap.get(DcMotor.class, Constants.leftFrontDriveMotor()); //0
        frontRightDrive = hardwareMap.get(DcMotor.class, Constants.rightFrontDriveMotor()); //3
        backLeftDrive = hardwareMap.get(DcMotor.class, Constants.leftBackDriveMotor()); //1
        backRightDrive = hardwareMap.get(DcMotor.class, Constants.rightBackDriveMotor()); //2

        waitForStart();

        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);

        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        double FLposition = frontLeftDrive.getCurrentPosition();
        double FRposition = frontRightDrive.getCurrentPosition();
        double BLposition = backLeftDrive.getCurrentPosition();
        double BRposition = backRightDrive.getCurrentPosition();


        backRightDrive.setTargetPosition(-1000);
        backLeftDrive.setTargetPosition(-1000);
        frontRightDrive.setTargetPosition(-1000);
        frontLeftDrive.setTargetPosition(-1000);

        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        backRightDrive.setPower(.5);
        backLeftDrive.setPower(.5);
        frontRightDrive.setPower(.5);
        frontLeftDrive.setPower(.5);

        Shooter shooter = new Shooter(hardwareMap, telemetry);
        Intake intake = new Intake(hardwareMap);

        while(frontLeftDrive.isBusy() && frontRightDrive.isBusy() && backLeftDrive.isBusy() && backRightDrive.isBusy()) {
            telemetry.addData("Positions", "frontLeft (%d), backLeft (%d), frontRight (%d), backRight (%d)", frontLeftDrive.getCurrentPosition(), frontRightDrive.getCurrentPosition(), backLeftDrive.getCurrentPosition(), backRightDrive.getCurrentPosition());
            telemetry.update();
        }
        shooter.setMotorPower(0.45);
        moveStop();
        sleep(3000);
        intake.setCollectBalls();
        sleep(2000);
        intake.setStopCollecting();

        // Left:
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backRightDrive.setTargetPosition(-1000);
        backLeftDrive.setTargetPosition(1000);
        frontRightDrive.setTargetPosition(1000);
        frontLeftDrive.setTargetPosition(-1000);

        moveLinear(1, 3000, "Left");

        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        backRightDrive.setTargetPosition(-1000);
//        backLeftDrive.setTargetPosition(1000);
//        frontRightDrive.setTargetPosition(1000);
//        frontLeftDrive.setTargetPosition(-1000);

        sleep (2000);




    }
    public void moveLinear(double speed, int time, String direction) throws InterruptedException {
        if (direction.equalsIgnoreCase("Forward")) {
            setPower(speed, speed, speed, speed);
        }
        if (direction.equalsIgnoreCase("Backward")) {
            setPower(-speed, -speed, -speed, -speed);
        }
        if (direction.equalsIgnoreCase("Left")) {
            setPower(-speed, speed, speed, -speed);
        }
        if (direction.equalsIgnoreCase("Right")) {
            setPower(speed, -speed, -speed, speed);
        }
        Thread.sleep(time);
    }

    public void setPower(double FLspeed, double FRspeed, double BLspeed, double BRspeed) {
        frontLeftDrive.setPower(FLspeed);
        frontRightDrive.setPower(FRspeed);
        backLeftDrive.setPower(BLspeed);
        backRightDrive.setPower(BRspeed);
    }


    public void moveStop() {
        setPower(0,0,0,0);
    }

    public void moveRotation(double speed, int time, String direction) throws InterruptedException{
        if (direction.equalsIgnoreCase("Left")) {
            setPower(-speed, speed, -speed, speed);
        }
        if (direction.equalsIgnoreCase("Right")) {
            setPower(speed, -speed, speed, -speed);
        }
        Thread.sleep(time);
    }

}