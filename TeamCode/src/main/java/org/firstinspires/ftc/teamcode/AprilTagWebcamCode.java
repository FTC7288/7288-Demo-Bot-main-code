package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

@Autonomous
public class AprilTagWebcamCode extends OpMode {
    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void init() {
        aprilTagWebcam.init(hardwareMap, telemetry);
    }

    @Override
    public void loop() {
        // update the vision portal
        aprilTagWebcam.update();
        AprilTagDetection id22 = aprilTagWebcam.getTagBySpecificId(22);
        aprilTagWebcam.displayDetectionTelemetry(id22);
        AprilTagDetection id21 = aprilTagWebcam.getTagBySpecificId(21);
        aprilTagWebcam.displayDetectionTelemetry(id21);
        AprilTagDetection id23 = aprilTagWebcam.getTagBySpecificId(23);
        aprilTagWebcam.displayDetectionTelemetry(id23);

        if (id22 != null) {
            telemetry.addData("Motif", "purple green purple");
        } else if (id21 != null) {
            telemetry.addData("Motif", "green purple purple");
        } else if (id23 != null) {
            telemetry.addData("Motif", "purple purple green");
        }
        else {
            telemetry.addData("Motif", "Not Found");
        }

        // This is important: update the driver station display with the new data
        telemetry.update();
    }
}
