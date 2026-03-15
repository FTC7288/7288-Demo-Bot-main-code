package org.firstinspires.ftc.teamcode.euler.feeder;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Feeder {
    private final Servo feederServo;
    
    // Positions
    public static final double PUSH_POSITION = 1.0;
    public static final double IDLE_POSITION = 0.0;
    public static final double RETRACT_POSITION = 0.1;

    // Temps de trajet estimé en millisecondes
    public static final long TRAVEL_TIME_MS = 250;

    private FeederInternalState internalState = FeederInternalState.IDLE;
    private double targetPosition = IDLE_POSITION;
    private double lastCommandedPosition = -1; // Pour éviter les appels hardware inutiles
    private final ElapsedTime timer = new ElapsedTime();
    private double moveStartTime = 0;

    public Feeder(Servo feederServo) {
        this.feederServo = feederServo;
    }

    public void push() {
        setTarget(FeederInternalState.PUSHING, PUSH_POSITION);
    }

    public void retract() {
        setTarget(FeederInternalState.RETRACTING, RETRACT_POSITION);
    }

    public void idle() {
        setTarget(FeederInternalState.IDLE, IDLE_POSITION);
    }

    private void setTarget(FeederInternalState state, double position) {
        if (this.internalState != state) {
            this.internalState = state;
            this.targetPosition = position;
            this.moveStartTime = timer.milliseconds();
        }
    }

    public void update() {
        // On ne communique avec le hardware que si la position cible a changé
        if (targetPosition != lastCommandedPosition) {
            feederServo.setPosition(targetPosition);
            lastCommandedPosition = targetPosition;
        }
    }

    public FeederInternalState getInternalState() {
        return internalState;
    }

    public FeederState getState() {
        // On considère que le servo bouge tant que le temps de trajet n'est pas écoulé
        // sauf si on est en IDLE
        if (internalState == FeederInternalState.IDLE) {
            return FeederState.IDLE;
        }
        
        if (timer.milliseconds() - moveStartTime < TRAVEL_TIME_MS) {
            return FeederState.MOVING;
        } else {
            return FeederState.IDLE;
        }
    }
}
