package org.firstinspires.ftc.teamcode.generated;

import com.qualcomm.robotcore.eventloop.opmode.*;

@TeleOp(<annotation>)
public class <CLASS_NAME> extends OpMode {

    static {
        System.loadLibrary("sdk_loader");
    }

    private native void nativeInit(OpMode opmode);
    private native void nativeLoop(OpMode opmode);


    @Override
    public void init() throws InterruptedException {
        nativeInit(this);
    }

    @Override
    public void loop() throws InterruptedException {
        nativeLoop(this);
    }

}