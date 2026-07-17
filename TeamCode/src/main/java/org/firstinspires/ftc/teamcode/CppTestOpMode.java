package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class CppTestOpMode extends OpMode {
    static {System.loadLibrary("sdk_loader");}
    public native void start(Object motor);
    public native void run(double power);
    public native void dump();

    double liftPower;


    @Override
    public void init() {
        DcMotor lift = hardwareMap.get(DcMotor.class, "lift");
        start(lift);
    }

    @Override
    public void loop() {
        if (gamepad1.dpadUpWasPressed()) {
            liftPower += (liftPower == 1) ? 0 : .1;
        } else if (gamepad1.dpadDownWasPressed()) {
            liftPower -= (liftPower == 1) ? 0 : .1;
        }

        run(liftPower);
    }

    @Override
    public void stop() {
        dump();
    }
}

