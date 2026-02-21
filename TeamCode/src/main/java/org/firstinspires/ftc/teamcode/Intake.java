package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Gamepad;
import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Intake {
    private final DcMotor intakeMotor;

    public Intake(HardwareMap hardwareMap) {
        this.intakeMotor = hardwareMap.dcMotor.get("intakeMotor");
        this.intakeMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void run(Gamepad gamepad) {
        if (gamepad.right_trigger >= 0.5) { // Intake
            intakeMotor.setPower(1.0);
        } else if (gamepad.left_trigger >= 0.5) { // Outtake
            intakeMotor.setPower(-1.0);
        } else {
            intakeMotor.setPower(0);
        }
    }

    public void setCollectBalls() {
        this.intakeMotor.setPower(1);
    }

    public void setStopCollecting() {
        this.intakeMotor.setPower(0);
    }


    public double getIntakePower() {
        return intakeMotor.getPower();
    }
}
