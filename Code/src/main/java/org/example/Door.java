package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class Door {
    int x, y;
    boolean visible = false;
    PImage doorImage;

    public Door(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.doorImage = parent.loadImage("images/door.PNG");
        System.out.println("Door: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(visible) {
            parent.image(this.doorImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("Door visible: " + visible);
    }
}
