package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain {
    private final DcMotor frontLeftMotor;
    private final DcMotor frontRightMotor;
    private final DcMotor backLeftMotor;
    private final DcMotor backRightMotor;
    public double frontLeftPower;
    public double frontRightPower;
    public double backLeftPower;
    public double backRightPower;
    public double getFrontLeftPower() {
        return this.frontLeftPower;
    }
    public double getFrontRightPower() {
        return this.frontRightPower;
    }

    public double getBackLeftPower() {
        return this.backLeftPower;
    }
    public double getBackRightPower() {
        return this.backRightPower;
    }
    public DriveTrain(HardwareMap hardwareMap, Telemetry telemetry) {
        this.frontLeftMotor = hardwareMap.dcMotor.get(Constants.leftFrontDriveMotor());
        this.frontRightMotor = hardwareMap.dcMotor.get(Constants.rightFrontDriveMotor());
        this.backLeftMotor = hardwareMap.dcMotor.get(Constants.leftBackDriveMotor());
        this.backRightMotor = hardwareMap.dcMotor.get(Constants.rightBackDriveMotor());

        //this.frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        this.frontRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        this.backRightMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive(Gamepad gamepad, Telemetry telemetry){
        double y = gamepad.left_stick_y;
        double x = -gamepad.left_stick_x * 1.1;
        double rx = -gamepad.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        this.frontLeftPower = (y + x + rx) / denominator;
        this.backLeftPower = (y - x + rx) / denominator;
        this.frontRightPower = (y - x - rx) / denominator;
        this.backRightPower = (y + x - rx) / denominator;

        frontLeftMotor.setPower(frontLeftPower);
        backLeftMotor.setPower(backLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backRightMotor.setPower(backRightPower);
    }
}
