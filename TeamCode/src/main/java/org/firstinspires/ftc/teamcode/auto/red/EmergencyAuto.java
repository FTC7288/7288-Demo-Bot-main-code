package org.firstinspires.ftc.teamcode.auto.red;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.MecanumDrive;

// This auto is ONLY intended to be used for emergency situations where the robot is unable to function
// properly due to damage or other problems with the robot. This auto moves the robot out of the starting
// area to get the moving points.
@Autonomous(name = "RedEmergencyAuto", group = "Autonomous")
public class EmergencyAuto extends LinearOpMode {

    double startPoseX = -56;
    double startPoseY = 45;

    @Override
    public void runOpMode() {
        Pose2d startingPose = new Pose2d(
            startPoseX,
            startPoseY,
            Math.toRadians(127)
        );

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPose);

        Action trajectoryAction = drive
            .actionBuilder(startingPose)
                .strafeToLinearHeading(new Vector2d(-40, 48), Math.toRadians(127))
            .waitSeconds(.5)
            .build();
        // Initialization
        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addData("Position during Init", 1);
            telemetry.update();
        }

        if (isStopRequested()) return;

        waitForStart();

        Actions.runBlocking(
            new SequentialAction(trajectoryAction)
            //                        Example
            //                        trajectoryActionChosen,
            //                        lift.liftUp(),
            //                        claw.openClaw(),
            //                        lift.liftDown(),
            //                        trajectoryActionCloseOut
        );
    }
}
