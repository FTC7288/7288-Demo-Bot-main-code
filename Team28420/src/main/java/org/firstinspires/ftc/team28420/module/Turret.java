package org.firstinspires.ftc.team28420.module;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.team28420.module.AS5600;

public class Turret {
    private final AS5600 encoder;
    private final CRServo servo;

    private double previousRawAngle = 0;
    private int rotations = 0;

    public Turret(HardwareMap hMap) {
        this.encoder = new AS5600(hMap.get(AnalogInput.class, "turret"));
        this.servo = hMap.get(CRServo.class, "turretServo");
    }

    public void goAngle(double targetAngle) {
        double currentRawAngle = encoder.getAngle();

        double delta = currentRawAngle - previousRawAngle;

        if (delta < -Math.PI) {
            rotations++;
        } else if (delta > Math.PI) {
            rotations--;
        }

        previousRawAngle = currentRawAngle;

        double continuousServoAngle = (rotations * Math.PI * 2) + previousRawAngle;
        double currentTurretAngle = continuousServoAngle / 5;

        double error = targetAngle - currentTurretAngle;
        double power = error * 0.1;

        servo.setPower(Range.clip(power, -1.0, 1.0));
    }
}