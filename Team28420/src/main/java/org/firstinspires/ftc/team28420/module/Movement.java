package org.firstinspires.ftc.team28420.module;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.team28420.config.WheelBaseConf;
import org.firstinspires.ftc.team28420.types.MovementParams;
import org.firstinspires.ftc.team28420.types.PolarVector;
import org.firstinspires.ftc.team28420.types.WheelsRatio;

public class Movement {
    private final DcMotorEx leftFront, rightFront, leftBack, rightBack;
    private double currentLF = 0.0;
    private double currentRF = 0.0;
    private double currentLB = 0.0;
    private double currentRB = 0.0;

    public static WheelsRatio<Double> vectorToRatios(MovementParams params) {
        double sin = Math.sin(params.getMoveVector().getTheta() - Math.PI / 4);
        double cos = Math.cos(params.getMoveVector().getTheta() - Math.PI / 4);
        double max = Math.max(Math.abs(sin), Math.abs(cos));

        double lf = params.getMoveVector().getAbs() * cos / max + params.getTurnAbs();
        double rf = params.getMoveVector().getAbs() * sin / max - params.getTurnAbs();
        double lb = params.getMoveVector().getAbs() * sin / max + params.getTurnAbs();
        double rb = params.getMoveVector().getAbs() * cos / max - params.getTurnAbs();

        if ((params.getMoveVector().getAbs() + Math.abs(params.getTurnAbs())) > 1) {
            lf /= params.getMoveVector().getAbs() + Math.abs(params.getTurnAbs());
            rf /= params.getMoveVector().getAbs() + Math.abs(params.getTurnAbs());
            lb /= params.getMoveVector().getAbs() + Math.abs(params.getTurnAbs());
            rb /= params.getMoveVector().getAbs() + Math.abs(params.getTurnAbs());
        }

        return new WheelsRatio<>(lf, rf, lb, rb);
    }

    public Movement(HardwareMap hMap) {
        this.leftFront = hMap.get(DcMotorEx.class, WheelBaseConf.LEFT_TOP_MOTOR);
        this.rightFront = hMap.get(DcMotorEx.class, WheelBaseConf.RIGHT_TOP_MOTOR);
        this.leftBack = hMap.get(DcMotorEx.class, WheelBaseConf.LEFT_BOTTOM_MOTOR);
        this.rightBack = hMap.get(DcMotorEx.class, WheelBaseConf.RIGHT_BOTTOM_MOTOR);
    }

    public void setup() {
        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);

        setMotorsMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setMotorsMode(DcMotor.RunMode.RUN_USING_ENCODER);
        setMotorsZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setMotorsMode(DcMotor.RunMode mode) {
        leftFront.setMode(mode);
        rightFront.setMode(mode);
        leftBack.setMode(mode);
        rightBack.setMode(mode);
    }

    public void setMotorsZeroPowerBehavior(DcMotor.ZeroPowerBehavior behavior) {
        leftFront.setZeroPowerBehavior(behavior);
        rightFront.setZeroPowerBehavior(behavior);
        leftBack.setZeroPowerBehavior(behavior);
        rightBack.setZeroPowerBehavior(behavior);
    }

    public void setMotorsTargetPosition(WheelsRatio<Double> wheelsRatio) {
        WheelsRatio<Integer> wheelsRatioInteger = wheelsRatio.toInt(1);
        leftFront.setTargetPosition(wheelsRatioInteger.getLeftTop());
        rightFront.setTargetPosition(wheelsRatioInteger.getRightTop());
        leftBack.setTargetPosition(wheelsRatioInteger.getLeftBottom());
        rightBack.setTargetPosition(wheelsRatioInteger.getRightBottom());
    }

    public void setMotorsPowerRatios(WheelsRatio<Double> wheelsRatio) {
        leftFront.setPower(wheelsRatio.getLeftTop());
        rightFront.setPower(wheelsRatio.getRightTop());
        leftBack.setPower(wheelsRatio.getLeftBottom());
        rightBack.setPower(wheelsRatio.getRightBottom());
    }

    public void setMotorsVelocityRatios(WheelsRatio<Double> wheelsRatio, int velocityMult) {
        leftFront.setVelocity(wheelsRatio.getLeftTop() * velocityMult);
        rightFront.setVelocity(wheelsRatio.getRightTop() * velocityMult);
        leftBack.setVelocity(wheelsRatio.getLeftBottom() * velocityMult);
        rightBack.setVelocity(wheelsRatio.getRightBottom() * velocityMult);
    }
    
    public void setMotorsVelocityRatiosWithAcceleration(WheelsRatio<Double> wheelsRatio, int velocityMult) {
        double targetLF = wheelsRatio.getLeftTop() * velocityMult;
        double targetRF = wheelsRatio.getRightTop() * velocityMult;
        double targetLB = wheelsRatio.getLeftBottom() * velocityMult;
        double targetRB = wheelsRatio.getRightBottom() * velocityMult;
        
        currentLF = accelerate(currentLF, targetLF);
        currentRF = accelerate(currentRF, targetRF);
        currentLB = accelerate(currentLB, targetLB);
        currentRB = accelerate(currentRB, targetRB);
        
        leftFront.setVelocity(currentLF);
        rightFront.setVelocity(currentRF);
        leftBack.setVelocity(currentLB);
        rightBack.setVelocity(currentRB);
    }
    
    private double accelerate(double current, double target) {
        double difference = target - current;
        if (Math.abs(difference) <= WheelBaseConf.ACCELERATION) {
            return target;
        }
        return current + Math.signum(difference) * WheelBaseConf.ACCELERATION;
    }

    public void brake() {
        setMotorsPowerRatios(WheelsRatio.ZERO);
    }
}
