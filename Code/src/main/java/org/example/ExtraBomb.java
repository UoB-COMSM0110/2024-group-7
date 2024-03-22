package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class ExtraBomb {
    int x, y;
    boolean visible = false;
    PImage extraBombImage;
    public ExtraBomb(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.extraBombImage = parent.loadImage("images/bomb_up.PNG");
        System.out.println("BombUp: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(visible) {
            parent.image(this.extraBombImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("bombup item visible: " + visible);
    }
}
