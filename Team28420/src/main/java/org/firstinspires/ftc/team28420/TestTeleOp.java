package org.firstinspires.ftc.team28420;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.AnalogInput;

import org.firstinspires.ftc.team28420.module.AS5600;
import org.firstinspires.ftc.team28420.module.Turret;

@TeleOp(name = "Test TeleOp")
public class TestTeleOp extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Turret turret = new Turret(hardwareMap);
        AS5600 as5600 = new AS5600(hardwareMap.get(AnalogInput.class, "turret"));
        double angle = 0;

        waitForStart();
        while (opModeIsActive()) {
            turret.goAngle(angle);

            if (gamepad1.dpad_up) {
                angle += Math.PI / 10;
            } else if (gamepad1.dpad_down) {
                angle -= Math.PI / 10;
            }

            telemetry.addData("angle", angle);
            telemetry.addData("servo", as5600.getAngle());
            telemetry.update();
        }
    }
}
