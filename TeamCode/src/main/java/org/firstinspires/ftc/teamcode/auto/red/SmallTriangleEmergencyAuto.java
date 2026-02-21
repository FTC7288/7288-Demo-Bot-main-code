package org.firstinspires.ftc.teamcode.auto.red;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name = "RedSmallTriangleEmergencyAuto", group = "Autonomous")
public class SmallTriangleEmergencyAuto extends LinearOpMode {

    @Override
    public void runOpMode(){
        Pose2d startingPose = new Pose2d(62, 20, Math.toRadians(180));

        MecanumDrive drive = new MecanumDrive(hardwareMap, startingPose);

        Action trajectoryAction = drive.actionBuilder(startingPose)
                .lineToX(40)
                .build();
        // Initialization
        while (!isStopRequested() && !opModeIsActive()) {
            telemetry.addData("Position during Init", 1);
            telemetry.update();
        }

        if (isStopRequested()) return;

        waitForStart();

        Actions.runBlocking(
                new SequentialAction(
                        trajectoryAction
                )
//                        Example
//                        trajectoryActionChosen,
//                        lift.liftUp(),
//                        claw.openClaw(),
//                        lift.liftDown(),
//                        trajectoryActionCloseOut
        );


    }

}
