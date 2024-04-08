package org.example;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashSet;

public class Door extends Items{
    /*int x, y;
    boolean visible = false;*/
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

    public void setDoor(PApplet parent){
        //HashSet<Integer> chosenIndexes = new HashSet<>();
        int doorRockIndex = (int) random(Obstacle.rocks.size());
        while (!Items.chosenIndexes.add(doorRockIndex)) {
            doorRockIndex = (int) random(Obstacle.rocks.size());
        }
        BreakableRock doorRock = Obstacle.rocks.get(doorRockIndex);
        doorRock.setHideDoor(true);
        door = new Door(doorRock.x(), doorRock.y(), parent);
        door.setVisible(false);
    }

    public static void doorRender(PApplet parent){
        if (Items.door != null && door.visible) {
            Items.door.render(parent);
        }
    }
}
