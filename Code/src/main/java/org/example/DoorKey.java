package org.example;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashSet;

public class DoorKey extends Items{
    PImage keyImage;
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
        this.visible = visible;
        System.out.println("Key visible: " + visible);
    }

    public void setKey(PApplet parent){
        HashSet<Integer> chosenIndexes = new HashSet<>();
        int keyRockIndex = (int) random(Obstacle.rocks.size()); // Key hided at a random breakableRock
        while (!chosenIndexes.add(keyRockIndex)) {
            keyRockIndex = (int) random(Obstacle.rocks.size());
        }
        BreakableRock keyRock = Obstacle.rocks.get(keyRockIndex);
        keyRock.setHideKey(true);
        // Initialize key but is not visible
        doorKey = new DoorKey(keyRock.x(), keyRock.y(), parent);
        doorKey.setVisible(false);
    }

    public static void doorKeyRender(PApplet parent){
        if (Items.doorKey != null && doorKey.visible) {
            Items.doorKey.render(parent);
        }
    }
}
