package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.Telemetry;

//Shooter class
public class Shooter {
    private final DcMotor shooterMotor;
    //Shooters starting power constants
    public double shooterPower = Constants.shooterPower;

    public Shooter(HardwareMap hardwareMap, Telemetry telemetry) {
        this.shooterMotor = hardwareMap.dcMotor.get(Constants.shooterMotor());
        this.shooterMotor.setDirection(DcMotor.Direction.REVERSE);
    }
    //Shooter power increase function
    public void increasePower() {
        this.shooterPower = shooterPower + 0.1;
        this.shooterPower = Math.min(shooterPower, 1.0);
    }

    //Shooter power decrease function
    public void decreasePower() {
        this.shooterPower = shooterPower - 0.1;
        this.shooterPower = Math.max(shooterPower, 0);
    }

    //REFACTOR
    //I have no idea why you need to inject a motor here, potentially refactor this to use the local motor within the class
    //we can also potentially use reverse power and PID to stop the motor faster to make it safer in case of extreme cases
    //Constant for shooter motors stop
    public void setZero() {
        shooterMotor.setPower(0);
    }

    public void setMotorPower(double power) {
        this.shooterMotor.setPower(power);
    }

   //Return shooter motors and powers
    public DcMotor getShooterMotor() {
        return this.shooterMotor;
    }
    public double getShooterPower() {
        return this.shooterPower;
    }

    public void shooter45() {
        this.shooterPower = 0.45;
        setMotorPower(this.shooterPower);
    }

    public void shooter30() {
        this.shooterPower = 0.3;
        setMotorPower(this.shooterPower);
    }

    public void shooter55() {
        this.shooterPower = 0.55;
        setMotorPower(this.shooterPower);
    }

    public void shooter60() {
        this.shooterPower = 0.6;
        setMotorPower(this.shooterPower);
    }

    public void reverseMotor() {
        this.shooterPower = -1;
        setMotorPower(this.shooterPower);
    }

    public double shooterEncoderPosition() {
        return this.shooterMotor.getCurrentPosition();
    }
}
