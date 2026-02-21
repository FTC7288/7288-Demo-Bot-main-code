package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class IndependentBlocker {
    Servo leftServo = null;
    Servo rightServo = null;
    double leftBlockerClosePosition = 0.78;
    double leftBlockerOpenPosition = 0.6;
    double rightBlockerClosePosition = 0.5;
    double rightBlockerOpenPosition = 0.72;

    // Fix twitches
    boolean previousLeftState = false;
    boolean previousRightState = false;
    boolean isOpen = false;


    public IndependentBlocker(HardwareMap hardwareMap, Telemetry telemetry) {
        leftServo = hardwareMap.get(Servo.class,"left");
        rightServo = hardwareMap.get(Servo.class,"right");
    }

    public void powerServo(Gamepad gamepad, Telemetry telemetry) {
        controlServo(gamepad, telemetry);
    }

    public void controlServo(Gamepad gamepad, Telemetry telemetry) {
        if (gamepad.left_bumper && !previousLeftState) {
            isOpen = !isOpen;
            if (isOpen) {
                open();
                telemetry.addData("Servo", "Opening blocker");
            } else {
                close();
                telemetry.addData("Servo", "Closing blocker");
            }
        }
        previousLeftState = gamepad.left_bumper;
        previousRightState = gamepad.right_bumper;
    }

    public double getLeftServoPosition() {
        return leftServo.getPosition();
    }

    public double getRightServoPosition() {
        return rightServo.getPosition();
    }

    public void open() {
        leftServo.setPosition(leftBlockerOpenPosition);
        rightServo.setPosition(rightBlockerOpenPosition);
    }

    public void close() {
        leftServo.setPosition(leftBlockerClosePosition);
        rightServo.setPosition(rightBlockerClosePosition);
    }
}
