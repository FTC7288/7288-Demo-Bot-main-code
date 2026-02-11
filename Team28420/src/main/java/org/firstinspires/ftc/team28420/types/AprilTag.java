package org.firstinspires.ftc.team28420.types;

public enum AprilTag {
    BLUE, GREEN, RED, UNKNOWN;

    public static String getMotif(int id) {
        if (id == 21) {
            return "GPP";
        } else if (id == 22) {
            return "PGP";
        } else if (id == 23) {
            return "PPG";
        }
        return null;
    }

    public static AprilTag fromId(int id) {
        if (id == 20) {
            return BLUE;
        } else if (id >= 21 && id <= 23) {
            return GREEN;
        } else if (id == 24) {
            return RED;
        }
        return UNKNOWN;
    }
}
