package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class DoorKey {
    int x,y;
    PImage keyImage;
    boolean visible = false;
    boolean collected = false;

    public DoorKey(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.keyImage = parent.loadImage("images/key.PNG");
        System.out.println("Key: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(!collected) {
            parent.image(this.keyImage, this.x, this.y, 30, 30);
        }
    }

    public void setCollected() {
        this.collected = true;
        System.out.println("DoorKey has been collected!");
    }

    public void setVisible(boolean visible) {
        this.visible = !visible;
        System.out.println("Key visible: " + visible);
    }
}
