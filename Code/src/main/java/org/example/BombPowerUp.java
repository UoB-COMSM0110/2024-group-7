package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class BombPowerUp extends Items{
    boolean markedForRemoval = false;
    PImage powerUpImage;

    public BombPowerUp(int x, int y, PApplet parent) {
        this.x = x;
        this.y = y;
        this.powerUpImage = parent.loadImage("images/fire_up.PNG");
        System.out.println("PowerUp: " + x + ", " + y);
    }

    public void render(PApplet parent) {
        if(this.visible) {
            parent.image(this.powerUpImage, this.x, this.y, 30, 30);
        }
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        System.out.println("powerUp item visible: " + visible);
    }

    public static void getPowerUp(PApplet parent){
        for (BombPowerUp powerUp : Items.powerUps) {
            if (powerUp.visible) {
                powerUp.render(parent);
                // Check if player collects it
                Player player = Characters.players.get(0);
                float distanceToPowerUp = dist(player.px, player.py, powerUp.x, powerUp.y);
                if (distanceToPowerUp < 30) {
                    powerUp.setVisible(false);
                    Characters.players.get(0).increasePower();
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
