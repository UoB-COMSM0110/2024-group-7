package org.example;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.HashSet;

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

    public static void setExtraBombs(PApplet parent){
        HashSet<Integer> chosenIndexes = new HashSet<>();
        // Randomly select 5 rocks to hide ExtraBomb items
        for (int i=0; i<extraBomb_items; i++) {
            int extraBombIndex = (int) parent.random(Obstacle.rocks.size());
            while (!chosenIndexes.add(extraBombIndex)) {
                extraBombIndex = (int) parent.random(Obstacle.rocks.size());
            }
            BreakableRock extraBombRock = Obstacle.rocks.get(extraBombIndex);
            ExtraBomb extraBomb = new ExtraBomb(extraBombRock.x(), extraBombRock.y(), parent);
            extraBombRock.setHideExtraBomb(true);
            extraBombRock.setHiddenExtraBomb(extraBomb);
            extraBombs.add(extraBomb);
        }
    }

    public static void getExtraBomb(PApplet parent){
        for (ExtraBomb extraBomb : Items.extraBombs) {
            if (extraBomb.visible) {
                extraBomb.render(parent);
                // Check if player collects it
                Player player = Character.players.get(0);
                float distanceToPowerUp = dist(player.px, player.py, extraBomb.x, extraBomb.y);
                if (distanceToPowerUp < 30) {
                    extraBomb.setVisible(false);
                    Character.players.get(0).increaseMaxBomb();
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
