package org.example;

import processing.core.PApplet;

public class PVEui {
    public static int seconds = 10;
    private static PApplet parent;
    static Boolean PVEuivisible = false;

    public PVEui(PApplet parent) {
        this.parent = parent;
    }

    public static void PVEUIShow() {
        PVEuivisible = true;
    }

    public static void PVEUIHide() {
        PVEuivisible = false;
    }
    public String pveShopMouseClicked(int x, int y) {
        if (x>=530 && x<610 && y>=90 && y<=130) {
            return "buyItem1";
        }

        if (x>=530 && x<610 && y>=170 && y<=210) {
            return "buyItem2";
        }

        if (x>=530 && x<610 && y>=250 && y<=290) {
            return "buyItem3";
        }

        if (x>=530 && x<610 && y>=330 && y<=370) {
            return "buyItem4";
        }

        if (x>=530 && x<610 && y>=410 && y<=450) {
            return "buyItem5";
        }

        if (x>=400 && x<=520 && y>=470 && y<=500) {
            return "close";
        }
        else {
            return "";
        }
    }
    public void dealOperation(String op) {
        switch (op) {
            case "close":
                GameLoop.openShop = false;
                System.out.println("shop closed");
                break;

            case "buyItem1":
                System.out.println("buyItem1");

                break;

            case "buyItem2":
                System.out.println("buyItem2");

                break;

            case "buyItem3":
                System.out.println("buyItem3");

                break;

            case "buyItem4":
                System.out.println("buyItem4");

                break;

            case "buyItem5":
                System.out.println("buyItem5");

                break;

            default:
                break;
        }
    }
}
