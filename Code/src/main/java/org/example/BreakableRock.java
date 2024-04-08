package org.example;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.ArrayList;

public class BreakableRock extends Obstacle{
    PImage rock;
    int health;
    boolean rockExist;
    boolean hideKey = false;
    boolean hideDoor = false;
    boolean hidePowerUp = false;
    boolean hideExtraBomb = false;
    boolean hideSpeedUp = false;
    public BombPowerUp hiddenPowerUp;
    public ExtraBomb hiddenExtraBomb;
    public SpeedUp hiddenSpeedUp;
    boolean markedForRemoval = false;
    BreakableRock(int x, int y, PApplet parent, PImage rock) {
        /*super(x, y, parent, rock);*/
        this.px=x;
        this.py=y;
        this.rock = rock;
        this.parent =parent;
        this.health = 1;
        this.rockExist = true;
    }

    public static ArrayList<BreakableRock> generateRocks(int rows, int cols, PApplet parent) {
        float chanceOfRock = 0.5F;
        ArrayList<BreakableRock> rocks = new ArrayList<>();
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                // Randomly decide whether to place a rock in this grid cell
                // Exclude the area around where player initially stand
                if (parent.random(1) < chanceOfRock && !Wall.isWallAt(i, j) && !(i <4 && j <4)) {
                    int x = 15 + i * tile;
                    int y = 75 + j * tile;
                    rocks.add(new BreakableRock(x, y, parent, ResourceManager.rock));
                }
            }
        }
        return rocks;
    }

    public void ifDestroyRock(){
        //handle the interaction between rocks and flames
        if (Flame.flameCheck(this.x(), this.y())) {
            this.health -= 1;
            if (this.health == 0) {
                this.rockExist = false;
            }
        }
    }

    void render(){
        parent.image(ResourceManager.rock,px,py,30,30);
    }

    public void setHideKey(boolean hideKey) {
        this.hideKey = hideKey;
    }

    public void setHideDoor(boolean hideDoor) {
        this.hideDoor = hideDoor;
    }

    public void setHideSpeedUp(boolean hideSpeedUp) {
        this.hideSpeedUp = hideSpeedUp;
    }

    // Method to set the hidden power-up
    public void setHiddenSpeedUp(SpeedUp SpeedUp) {
        this.hiddenSpeedUp = SpeedUp;
    }

    // Method to get the associated power-up
    public BombPowerUp getAssociatedPowerUp() {
        return this.hiddenPowerUp;
    }

    public void setHideExtraBomb(boolean hideExtraBomb) {
        this.hideExtraBomb = hideExtraBomb;
    }

    // Method to set the hidden extraBomb
    public void setHiddenExtraBomb(ExtraBomb extraBomb) {
        this.hiddenExtraBomb = extraBomb;
    }

    // Method to get the associated extraBomb
    public ExtraBomb getAssociatedExtraBomb() {
        return this.hiddenExtraBomb;
    }

    public void setHidePowerUp(boolean hidePowerUp) {
        this.hidePowerUp = hidePowerUp;
    }

    // Method to set the hidden power-up
    public void setHiddenPowerUp(BombPowerUp powerUp) {
        this.hiddenPowerUp = powerUp;
    }

    public void markForRemoval() {
        this.markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return this.markedForRemoval;
    }

    public static void rocksRender(){
        for (BreakableRock rock : rocks) {
            if (rock.rockExist) {
                rock.render();
            }
            rock.ifDestroyRock();
        }
    }
}
