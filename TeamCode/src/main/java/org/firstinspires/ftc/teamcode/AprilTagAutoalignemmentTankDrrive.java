package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;


import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;


@TeleOp
public class AprilTagAutoalignemmentTankDrrive extends OpMode {
    public final AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();
    public final TankDrivetrain tankDrivetrain = new TankDrivetrain();
     //-------------------PID controller--------------//
    double kP = 0.002;
    double error = 0;
    double lasterror = 0;
    double goalX = 0 ;// add an offset
    double angleTolerence = 0.4;
    double kD = 0.0001;
    double curTime = 0;
    double lastTIme = 0;

    //-------------------Drive setup-------------//
    double forward, strafe, rotate;

    //---------------controller bassed Pd tuning---------------------//
    double[] stepSizes ={0.1, 0.01, 0.001};
    int stepIndex = 1;

    @Override
    public void init() {
        aprilTagWebcam.init();
        tankDrivetrain.init();

    }
     public void start() {
        resetRuntime();
        curTime = getRuntime();
     }

    @Override
    public void loop() {
        //-----------------get mecanume drive inpute-----------------/
        forward = -gamepad1.left_stick_y;
        rotate = gamepad1.left_stick_x;

        //-------------get apriltag info------------------------//
        aprilTagWebcam.updateDetections();
        AprilTagDetection id20 = aprilTagWebcam.getTagBySpecificId(20);

        //----------auto aligne rotation object---------------//
        if(gamepad1.left_trigger > 0.3) {
            if (id20 != null) {
                error = goalX -id20.ftcPose.bearing; //bssicle is tX//

                if (Math.abs(error) < angleTolerence) {
                    rotate = 0;
                }else {
                    double pTerm = error * kP;
                    curTime = getRuntime();
                    double dt = curTime - lastTIme;
                    double dTerm = ((error - lasterror) /  dt) * kD;

                    rotate = Range.clip(pTerm+dTerm, 0.4,0.4);
                    lasterror = error;
                    lastTIme = curTime;
                }
            } else {
                lastTIme = getRuntime();
                lasterror = 0;

            }
        }else {
            lastTIme = getRuntime();
            lasterror = 0;
        }

        //-------------drive our motors--------------//
        tankDrivetrain.loop(); // on peut avoir des problem pour cette utilisation


        //-----------------tuning---------------------//
        if (gamepad1.dpadLeftWasPressed()) {
            kP -= stepSizes [stepIndex];
        }
        if (gamepad1.dpadRightWasPressed()) {
            kP += stepSizes [stepIndex];
        }
// D-pad up/down adjusts the D gain.
        if (gamepad1.dpadUpWasPressed()) {
            kD += stepSizes [stepIndex];
        }
        if (gamepad1.dpadDownWasPressed()) {
            kD -= stepSizes [stepIndex];
        }

        
        //-------------telemetrie------------------///
        if (id20 != null) {
           if (gamepad1.left_trigger > 0.3) {
               telemetry.addLine("autoalligne");
           }
           telemetry.addData("error",error);
        }else {
            telemetry.addLine("manual rotate");
        }
        telemetry.addData("kp value",kP);
        telemetry.addData("KD value",kD);

    }


}
