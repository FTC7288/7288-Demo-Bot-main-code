package org.firstinspires.ftc.team28420;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.team28420.module.Actions;
import org.firstinspires.ftc.team28420.module.Camera;
import org.firstinspires.ftc.team28420.module.Movement;
import org.firstinspires.ftc.team28420.module.Shooter;
import org.firstinspires.ftc.team28420.types.AprilTag;
import org.firstinspires.ftc.team28420.util.Config;

@Autonomous(name = "MAIN RED")
public class NewActionsAutonomous extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Actions act = new Actions(
                new Movement(
                        hardwareMap.get(DcMotorEx.class, Config.WheelBaseConf.LEFT_TOP_MOTOR),
                        hardwareMap.get(DcMotorEx.class, Config.WheelBaseConf.RIGHT_TOP_MOTOR),
                        hardwareMap.get(DcMotorEx.class, Config.WheelBaseConf.LEFT_BOTTOM_MOTOR),
                        hardwareMap.get(DcMotorEx.class, Config.WheelBaseConf.RIGHT_BOTTOM_MOTOR)
                ),
                hardwareMap.get(IMU.class, Config.GyroConf.IMU),
                new Camera(hardwareMap.get(WebcamName.class, Config.CameraConf.WEBCAM)),
                new Shooter(hardwareMap),
                hardwareMap.get(Servo.class, "parkingServo")
        );
        ElapsedTime elapsedTime = new ElapsedTime();

        act.init();

        waitForStart();

        while(opModeIsActive()) {
            if (Config.ShooterConf.TARGET_MOTIF == null) {
                act.setMotif();
            }

            telemetry.addData("scanned motif", Config.ShooterConf.TARGET_MOTIF);
            telemetry.addData("elapsed time", elapsedTime.milliseconds());

            if (elapsedTime.milliseconds() <= 5000) {
                act.move(act.getRatiosForApriltag(AprilTag.GREEN, -20, 70));
            }
            else if (elapsedTime.milliseconds() <= 5252) {
                act.move(act.getRatios(0, 0, 0.5));
            }
            else if (elapsedTime.milliseconds() <= 10000) {
                act.move(act.getRatiosForApriltag(AprilTag.RED, 0, Config.CameraConf.RANGE_TO_TAG));
            }
            else if (elapsedTime.milliseconds() <= 10000) {

            }
        }
    }
}
