package org.example;

import processing.core.PApplet;

public class PVPui {
    public static int seconds = 11;
    private static PApplet parent;
    static Boolean PVPuivisible = false;

    public PVPui(PApplet parent) {
        this.parent = parent;
    }

    public static void PVPuishow() {
        PVPuivisible = true;
    }

    public static void PVPUIHide() {
        PVPuivisible = false;
    }

    public static void clock() {
        while (seconds > 0 && GameLoop.PVP) {
            //System.out.println("Remaining: " + seconds + " seconds");

            try {
                Thread.sleep(1000); // 等待1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            seconds--;
        }
        System.out.println("Countdown complete!");
        GameLoop.timeStarted = false;
        if (seconds == 0){
            seconds = 11;
            UltimateAbilities.pvpClock = true;
        }
    }
}
