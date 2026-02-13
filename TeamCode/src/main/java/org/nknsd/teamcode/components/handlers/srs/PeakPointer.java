package org.nknsd.teamcode.components.handlers.srs;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.nknsd.teamcode.components.handlers.odometry.AbsolutePosition;
import org.nknsd.teamcode.components.motormixers.AutoPositioner;
import org.nknsd.teamcode.components.utility.IntPoint;
import org.nknsd.teamcode.components.utility.RobotVersion;
import org.nknsd.teamcode.components.utility.feedbackcontroller.PidController;
import org.nknsd.teamcode.frameworks.NKNComponent;

public class PeakPointer implements NKNComponent {
    private final PeakFinder peakFinder;
    private final AutoPositioner positioner;
    private final SRSHubHandler srsHubHandler;
    final private AbsolutePosition position;

    final private double MAX_XOFFSET = 0.1;
    final private double WAIT_TIME_MS = 50;

    final private PidController pidH =  new PidController(0.5, .4, 0.08, .2, true, 0.1, 0.2);

//    private final SRSDataAverager srsDataAverager;

    private boolean targetingEnabled;
    private boolean eatEnabled;
    private double lastRunTime;
    private IntPoint points = new IntPoint(0,0);
    private Double offset = 0.0;
    final private PidController pidXY = new PidController(0.1, .2, 0.05, .1, true, 0.01, 0.1);
    private Short dist;

    public void enableTargeting(boolean targetingEnabled, boolean eatEnabled) {
        this.targetingEnabled = targetingEnabled;
        this.eatEnabled = eatEnabled;
//        positioner.enableAutoPositioning(targetingEnabled, targetingEnabled, eatEnabled); // for when we merge
        positioner.enableAutoPositioning(targetingEnabled);
        positioner.setTargetH(position.getPosition().h, RobotVersion.INSTANCE.pidControllerH);
        positioner.setTargetX(position.getPosition().x, RobotVersion.INSTANCE.pidControllerX);
        positioner.setTargetY(position.getPosition().y, RobotVersion.INSTANCE.pidControllerY);
    }

    public PeakPointer(PeakFinder peakFinder, SRSHubHandler srsHubHandler, AutoPositioner positioner, AbsolutePosition position /*, SRSDataAverager srsDataAverager*/) {
        this.peakFinder = peakFinder;
        this.srsHubHandler = srsHubHandler;
        this.positioner = positioner;
//        this.srsDataAverager = srsDataAverager;
        this.position = position;
    }

    public Double getOffset() {
        IntPoint currentPeak = peakFinder.getPeak(srsHubHandler.getNormalizedDists());
        if (currentPeak == null) {
            return null;
        }
        dist = srsHubHandler.getDistances()[currentPeak.getX()][currentPeak.getY()];
        return -(AngleCalculator.calculateHeadingOffset(currentPeak.getX(), dist));
    }

    public IntPoint getDist(){
        return peakFinder.getPeak(srsHubHandler.getNormalizedDists());
    }

    public boolean targetAcquired() {
        if (offset == null) {
            return false;
        }
        return Math.abs(offset) < MAX_XOFFSET;
    }

    @Override
    public boolean init(Telemetry telemetry, HardwareMap hardwareMap, Gamepad gamepad1, Gamepad gamepad2) {
        return true;
    }

    @Override
    public void init_loop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void start(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public void stop(ElapsedTime runtime, Telemetry telemetry) {

    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public void loop(ElapsedTime runtime, Telemetry telemetry) {
        if (runtime.milliseconds() - lastRunTime > WAIT_TIME_MS) {
            offset = getOffset();
            RobotLog.v("offset " + offset);
            if (targetingEnabled && offset != null) {
                positioner.setTargetH((position.getPosition().h + offset), pidH);

                if (eatEnabled) {
                    if (getDist() != null && getOffset() != null) {
                        points = getDist();
                        offset = getOffset();

                        RobotLog.v("offset " + offset);
                        RobotLog.v("dist x " + getDist().getX() + ", dist y " + getDist().getY());

                        double distance = Math.sqrt((7 - points.getY()) * (7 - points.getY()) + (3.5 + points.getY()) * (3.5 + points.getY()));
                        double angle = position.getPosition().h - offset;

                        double x = Math.cos(angle) * distance;
                        double y = Math.sin(angle) * distance;;

                        positioner.setTargetX(position.getPosition().x + x, pidXY);
                        positioner.setTargetY(position.getPosition().y + y, pidXY);
                    }
                    lastRunTime = runtime.milliseconds();
                }
            }
            lastRunTime = runtime.milliseconds();
        }
    }

    @Override
    public void doTelemetry(Telemetry telemetry) {
        telemetry.addData("enable", targetingEnabled);
        if (targetingEnabled) {
            offset = getOffset();
            IntPoint currentPeak = peakFinder.getPeak(srsHubHandler.getNormalizedDists());
            if (offset != null && currentPeak != null) {
                telemetry.addData("offset", offset);
                telemetry.addData("x", currentPeak.getX());
                telemetry.addData("y", currentPeak.getY());
                telemetry.addData("dist", dist);
            } else {
                telemetry.addLine("no peak");
            }
            telemetry.addData("targeted?", targetAcquired());
        }
    }
}