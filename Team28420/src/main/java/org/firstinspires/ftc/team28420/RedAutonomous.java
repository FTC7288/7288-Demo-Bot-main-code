package org.firstinspires.ftc.team28420;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.team28420.config.CameraConf;
import org.firstinspires.ftc.team28420.config.ShooterConf;
import org.firstinspires.ftc.team28420.module.Actions;
import org.firstinspires.ftc.team28420.types.AprilTag;

@Autonomous(name = "auto main RED", group = "MAIN")
public class RedAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Actions act = new Actions(hardwareMap, true, telemetry);

        ElapsedTime elapsedTime = new ElapsedTime();
        int counter = 1;
        boolean shootingDone = false;

        act.init();
        act.setDefaultAutoMotif("PPG");
        act.setScanAllowed(false);
        act.resetYaw();

        waitForStart();

        act.afterStart();
        elapsedTime.reset();
        ShooterConf.TARGET_MOTIF = null;
        while (opModeIsActive()) {
            if (elapsedTime.milliseconds() <= 10000) {
                act.move(act.getRatios(0, 0, act.getForceToGyro(Math.PI / 2)));
            } else {
                act.move(act.getRatios(0, 0, 0));
            }

            telemetry.addData("counter", counter);
            act.updateShooter();
            act.updateApriltags();
            act.updateLastAngles();
            act.log();
            telemetry.update();
        }
    }
}
