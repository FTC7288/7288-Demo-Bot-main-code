package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(preselectTeleOp = "EulerTeleop", group = "Euler")
public class RealTimeOpMode extends LinearOpMode {

    @Override
    public void runOpMode() {

        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();

        int i = 0;
        ElapsedTime timer = new ElapsedTime();

        while (opModeIsActive()) {
            telemetry.addData("opModeIsActive", i);
            telemetry.update();
            i++;
        }

        telemetry.addData("Status", "Done");
        telemetry.addData("Timer", +timer.milliseconds() + "ms)");
        telemetry.addData("i", i);
        telemetry.update();
    }
}
