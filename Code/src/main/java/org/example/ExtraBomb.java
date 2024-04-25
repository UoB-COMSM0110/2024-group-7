package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class ExtraBomb extends Items{
    /*int x, y;
    boolean visible = false;*/
    boolean markedForRemoval = false;
    PImage extraBombImage;
    public ExtraBomb(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.extraBombImage = parent.loadImage("images/bomb_up.PNG");
        System.out.println("BombUp: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(this.visible) {
            parent.image(this.extraBombImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("bombUp item visible: " + visible);
    }

    public static void getExtraBomb(PApplet parent){
        for (ExtraBomb extraBomb : Items.extraBombs) {
            if (extraBomb.visible) {
                extraBomb.render(parent);
                // Check if player collects it
                Player player = Characters.players.get(0);
                float distanceToExtraBomb = dist(player.px, player.py, extraBomb.x, extraBomb.y);
                if (distanceToExtraBomb < 30) {
                    extraBomb.setVisible(false);
                    Characters.players.get(0).increaseMaxBomb();
                }
            }
        }
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
