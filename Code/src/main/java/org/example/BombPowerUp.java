package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class BombPowerUp {
    int x, y;
    boolean visible = false;
    boolean markedForRemoval = false;
    PImage powerUpImage;

    public BombPowerUp(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.powerUpImage = parent.loadImage("images/fire_up.PNG");
        System.out.println("PowerUp: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(visible) {
            parent.image(this.powerUpImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("powerUp item visible: " + visible);
    }

    public boolean isVisible() {
        return visible;
    }

    public void markForRemoval() {
        this.markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return this.markedForRemoval;
    }
}
