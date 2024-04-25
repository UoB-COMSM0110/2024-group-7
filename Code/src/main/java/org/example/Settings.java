package org.example;

import processing.core.PApplet;
import processing.core.PFont;

public class Settings extends GameLoop {
        public static int maxCoins=20, maxShops=2, maxDoors=1, maxEnemies=5, maxPlayers=2, maxBombUp=5, maxFireUp=5, maxSpeedUp=5, maxHealthUp=2;
        PApplet parent; // Reference to the parent PApplet
        boolean visible;

        public Settings(PApplet parent) {
            //super(); // 调用父类的构造函数
            this.parent = parent;
            visible = false;
        }


    // 重写 settings() 方法来设置窗口大小
    @Override
    public void settings() {
        size(width, height);
    }
    // Show the settings menu
    public void show() {
        visible = true;
    }

    // Hide the settings menu
    public void hide() {
        visible = false;
        settings = false;
        menu = true;
    }

    // Draw the settings menu
    public void dealOperation(String op, char keyInput) {

        switch (op) {
            case "close":
                hide();
                System.out.println("settings closed");
                break;
            case "changeUpKey1":
                System.out.println("changeUpKey1");
                GameLoop.upKey1 = keyInput;
                break;

            case "changeDownKey1":
                System.out.println("changeDownKey1");
                GameLoop.downKey1 = keyInput;
                break;

            case "changeLeftKey1":
                System.out.println("changeLeftKey1");
                GameLoop.leftKey1 = keyInput;
                break;

            case "changeRightKey1":
                System.out.println("changeRightKey1");
                GameLoop.rightKey1 = keyInput;
                break;

            case "changeBombKey1":
                System.out.println("changeBombKey1");
                GameLoop.bombKey1 = keyInput;
                break;

            default:
                break;
        }
    }
    public String settingsMouseClicked(int x, int y) {
        if (x>=400 && x<550 && y>=90 && y<=130) {
            return "changeUpKey1";
        }

        if (x>=400 && x<550 && y>=170 && y<=210) {
            return "changeDownKey1";
        }

        if (x>=400 && x<550 && y>=250 && y<=290) {
            return "changeLeftKey1";
        }

        if (x>=400 && x<550 && y>=330 && y<=370) {
            return "changeRightKey1";
        }

        if (x>=400 && x<550 && y>=410 && y<=450) {
            return "changeBombKey1";
        }

        if (x>=420 && x<=560 && y>=480 && y<=520) {
            return "close";
        }
        else {
            return "none";
        }
    }

    public static void reset() {
        Characters.players.get(0).health = 3;
        Characters.players.get(1).health = 3;

        Characters.players.get(0).explosionDistance = 1;
        Characters.players.get(0).maxBombs = 1;
        Characters.players.get(0).speed = 1;
        Characters.players.get(0).coin = 0;

        Characters.players.get(0).px = 45;
        Characters.players.get(0).py = 105;

        Characters.players.get(1).px = 885;
        Characters.players.get(1).py = 465;

        Player.players.get(0).otherPlayerWon = false;
        Player.players.get(1).otherPlayerWon = false;
        Player.players.get(0).exist = true;
        Player.players.get(1).exist = true;

        gameLost = false;

        PVPui.seconds = 11;
        UltimateAbilities.k = 0;
        UltimateAbilities.pvpClock = false;
        Flame.activeFlames.clear();
        Flame.resetFlames();

        reset = false;
        shrinkNumber = 2.5f;
    }
    public static void render(PApplet game) {
        GameLoop.menu = false;
        game.background(87, 108, 164);
        game.textFont(Sprites.daruma, 35);
        game.fill(250, 236, 0);
        game.text("Settings", 150, 30, (float) width / 4, height);
        game.text("P1", 350, 30, (float) width / 4, height);
        game.textSize(30);
        game.fill(0);
        game.text("UP KEY:", 150, 100, (float) width / 4, height);
        game.text("DOWN KEY:", 150, 180, (float) width / 4, height);
        game.text("LEFT KEY:", 150, 260, (float) width / 4, height);
        game.text("RIGHT KEY:", 150, 340, (float) width / 4, height);
        game.text("BOMB KEY:", 150, 420, (float) width / 4, height);
        game.textSize(35);
        game.fill(0);
        game.text(String.valueOf(upKey1), 350, 100, (float) width / 4, height);
        game.text(String.valueOf(downKey1), 350, 180, (float) width / 4, height);
        game.text(String.valueOf(leftKey1), 350, 260, (float) width / 4, height);
        game.text(String.valueOf(rightKey1), 350, 340, (float) width / 4, height);
        game.text(String.valueOf(bombKey1), 350, 420, (float) width / 4, height);
        game.textSize(30);
        game.textAlign(CENTER);
        game.fill(0, 0, 222);
        game.text("←BACK", 0, 490, width, 500);
    }

}
