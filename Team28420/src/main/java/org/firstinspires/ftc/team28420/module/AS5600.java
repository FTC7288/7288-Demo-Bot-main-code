package org.firstinspires.ftc.team28420.module;

import com.qualcomm.robotcore.hardware.AnalogInput;

public class AS5600 {
    private final AnalogInput analogInput;
    private final double MAX_VOLTAGE;

    public AS5600(AnalogInput analogInput) {
        this.analogInput = analogInput;
        this.MAX_VOLTAGE = analogInput.getMaxVoltage();
    }

    public double getAngle() {
        double voltage = analogInput.getVoltage();
        return voltage / MAX_VOLTAGE * Math.PI * 2;
    }
}
