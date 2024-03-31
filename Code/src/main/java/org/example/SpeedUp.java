package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class SpeedUp {
    int x, y;
    boolean visible = false;
    boolean markedForRemoval = false;
    PImage speedUpImage;

    public SpeedUp(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.speedUpImage = parent.loadImage("images/speed_up.PNG");
        System.out.println("SpeedUp: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(visible) {
            parent.image(this.speedUpImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("speedUp item visible: " + visible);
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
