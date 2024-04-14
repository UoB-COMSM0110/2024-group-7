package org.example;

import processing.core.PApplet;

public class Settings extends GameLoop {
    PApplet parent; // Reference to the parent PApplet
    boolean visible;

    public Settings(PApplet p) {
        super(); // 调用父类的构造函数
        parent = p;
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
}
