package org.example;

import processing.core.PApplet;

public class PVPui {

    private static PApplet parent;
    static Boolean PVPuivisible = false;

    public PVPui(PApplet parent) {
        this.parent = parent;
    }

    public static void PVPuishow() {
        PVPuivisible = true;
    }

    public static void PVPuihide() {
        PVPuivisible = false;
    }
}
