package org.nknsd.teamcode.programs.tests.thisYear.peakFinding;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.artifact.ArtifactSystem;
import org.nknsd.teamcode.components.handlers.artifact.MicrowaveScoopHandler;
import org.nknsd.teamcode.components.handlers.artifact.SlotTracker;
import org.nknsd.teamcode.components.handlers.color.BallColorInterpreter;
import org.nknsd.teamcode.components.handlers.color.ColorReader;
import org.nknsd.teamcode.components.handlers.launch.LaunchSystem;
import org.nknsd.teamcode.components.handlers.launch.LauncherHandler;
import org.nknsd.teamcode.components.handlers.launch.TrajectoryHandler;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.handlers.srs.PeakFinder;
import org.nknsd.teamcode.components.handlers.srs.PeakPointer;
import org.nknsd.teamcode.components.handlers.srs.SRSHubHandler;
import org.nknsd.teamcode.components.handlers.srs.SRSIntakeState;
import org.nknsd.teamcode.components.handlers.vision.BasketLocator;
import org.nknsd.teamcode.components.handlers.vision.ID;
import org.nknsd.teamcode.components.handlers.vision.TargetingSystem;
import org.nknsd.teamcode.components.motormixers.AbsolutePowerMixer;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.motormixers.MecanumMotorMixer;
import org.nknsd.teamcode.components.motormixers.PowerInputMixer;
import org.nknsd.teamcode.components.sensors.AprilTagSensor;
import org.nknsd.teamcode.components.sensors.FlowSensor;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.StateMachine;
import org.nknsd.teamcode.frameworks.NKNComponent;
import org.nknsd.teamcode.frameworks.NKNProgram;

import java.util.List;

@TeleOp(name = "PeakTargetingTest", group = "Tests")
public class PeakTargetTester extends NKNProgram {

    @Override
    public void createComponents(List<NKNComponent> components, List<NKNComponent> telemetryEnabled) {

        PowerInputMixer powerInputMixer = new PowerInputMixer();
        components.add(powerInputMixer);
        AbsolutePowerMixer absolutePowerMixer = new AbsolutePowerMixer();
        components.add(absolutePowerMixer);
        MecanumMotorMixer mecanumMotorMixer = new MecanumMotorMixer();
        components.add(mecanumMotorMixer);
        AutoPositioner autoPositioner = new AutoPositioner();
        components.add(autoPositioner);

        FlowSensor flowSensor1 = new FlowSensor("RODOS");
        FlowSensor flowSensor2 = new FlowSensor("LODOS");
        components.add(flowSensor1);
        components.add(flowSensor2);
        AbsolutePosition absolutePosition = new AbsolutePosition(flowSensor1, flowSensor2);
        components.add(absolutePosition);

        AprilTagSensor aprilTagSensor = new AprilTagSensor();
        components.add(aprilTagSensor);
//        telemetryEnabled.add(aprilTagSensor);

        BasketLocator basketLocator = new BasketLocator(RobotVersion.INSTANCE.aprilDistanceInterpolater);
        components.add(basketLocator);
//        telemetryEnabled.add(basketLocator);

        TargetingSystem targetingSystem = new TargetingSystem(RobotVersion.INSTANCE.pidControllerH);
        components.add(targetingSystem);
//        telemetryEnabled.add(targetingSystem);
        targetingSystem.setTargetingColor(ID.BLUE);
        TrajectoryHandler trajectoryHandler = new TrajectoryHandler();
        components.add(trajectoryHandler);
//        telemetryEnabled.add(trajectoryHandler);

        LauncherHandler launcherHandler = new LauncherHandler(0.95, 1.10);
        components.add(launcherHandler);
//        telemetryEnabled.add(launcherHandler);
        launcherHandler.setEnabled(true);

        LaunchSystem launchSystem = new LaunchSystem(RobotVersion.INSTANCE.launchSpeedInterpolater, RobotVersion.INSTANCE.launchAngleInterpolater, 2, 16, 132);

        targetingSystem.link(basketLocator, powerInputMixer, absolutePosition);
        basketLocator.link(aprilTagSensor);

        SRSHubHandler srsHubHandler = new SRSHubHandler();
        components.add(srsHubHandler);
//        telemetryEnabled.add(srsHubHandler);

        PeakFinder peakFinder = new PeakFinder();
        PeakPointer peakPointer = new PeakPointer(peakFinder, srsHubHandler, autoPositioner, absolutePosition);
        components.add(peakPointer);
//        telemetryEnabled.add(peakPointer);


        components.add(StateMachine.INSTANCE);

        powerInputMixer.link(absolutePowerMixer, mecanumMotorMixer);
        absolutePowerMixer.link(mecanumMotorMixer, absolutePosition);
        autoPositioner.link(powerInputMixer, absolutePosition);

        ColorReader colorReader = new ColorReader("ColorSensor");
        components.add(colorReader);
        BallColorInterpreter ballColorInterpreter = new BallColorInterpreter(10, 0.01);
        components.add(ballColorInterpreter);
        ballColorInterpreter.link(colorReader);

            MicrowaveScoopHandler microwaveHandler = new MicrowaveScoopHandler();
            components.add(microwaveHandler);
//            telemetryEnabled.add(microwaveHandler);

        SlotTracker slotTracker = new SlotTracker();
        components.add(slotTracker);
        slotTracker.link(microwaveHandler, ballColorInterpreter);
        telemetryEnabled.add(slotTracker);

        MicrowaveScoopHandler microwaveScoopHandler = new MicrowaveScoopHandler();
        components.add(microwaveScoopHandler);

        ArtifactSystem artifactSystem = new ArtifactSystem();
        artifactSystem.link(microwaveScoopHandler, slotTracker, launchSystem);




        telemetryEnabled.add(powerInputMixer);
        telemetryEnabled.add(srsHubHandler);
        telemetryEnabled.add(absolutePosition);
        telemetryEnabled.add(peakPointer);

        StateMachine.INSTANCE.addState("intake", new SRSIntakeState(peakPointer, true, microwaveScoopHandler, slotTracker, artifactSystem, new String[]{}, new String[]{"intake"}));
        StateMachine.INSTANCE.startState("intake");
    }
}
