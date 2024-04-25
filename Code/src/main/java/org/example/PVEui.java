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
            return "VerticalFlames";
        }

        if (x>=530 && x<610 && y>=170 && y<=210) {
            return "RoundFlames";
        }

        if (x>=530 && x<610 && y>=250 && y<=290) {
            return "MoveToTheDoor";
        }

        if (x>=530 && x<610 && y>=330 && y<=370) {
            return "KillAllEnemies";
        }

        if (x>=530 && x<610 && y>=410 && y<=450) {
            return "RemoveAllRocks";
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

            case "VerticalFlames":
                System.out.println("buy VerticalFlames");
                if(Characters.players.get(0).getCoin() >= 1){
                    UltimateAbilities.abilitiesList.add(1);
                    Characters.players.get(0).coin -= 1;
                }
                    break;

            case "RoundFlames":
                System.out.println("buy RoundFlames");
                if(Characters.players.get(0).getCoin() >= 2){
                    UltimateAbilities.abilitiesList.add(2);
                    Characters.players.get(0).coin -= 2;
                }
                break;

            case "MoveToTheDoor":
                System.out.println("buy MoveToTheDoor");
                if(Characters.players.get(0).getCoin() >= 3){
                    UltimateAbilities.abilitiesList.add(3);
                    Characters.players.get(0).coin -= 3;
                }
                break;

            case "KillAllEnemies":
                System.out.println("buy KillAllEnemies");
                if(Characters.players.get(0).getCoin() >= 4){
                    UltimateAbilities.abilitiesList.add(4);
                    Characters.players.get(0).coin -= 4;
                }
                break;

            case "RemoveAllRocks":
                System.out.println("buy RemoveAllRocks");
                if(Characters.players.get(0).getCoin() >= 5){
                    UltimateAbilities.abilitiesList.add(5);
                    Characters.players.get(0).coin -= 5;
                }
                break;

            /*case "Key":
                System.out.println("buy the key");
                if(Character.players.get(0).getCoin() >= 1){
                    Character.players.get(0).coin -= 1;
                    Character.players.get(0).isHavingTheKey = true;
                }
                break;*/

            default:
                break;
        }
    }
}
