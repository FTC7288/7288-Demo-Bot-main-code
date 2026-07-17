package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.eventloop.opmode.*;

@Autonomous(<annotation>)
public class <CLASS_NAME> extends LinearOpMode {

    static {
        System.loadLibrary("sdk_loader");
    }

    @Override
    public void runOpMode() throws InterruptedException {
        nativeRun(this);
    }

    private native void nativeRun(
            LinearOpMode opmode
    );
}