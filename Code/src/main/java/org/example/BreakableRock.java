package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class BreakableRock extends Wall{
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
        super(x, y, parent, rock);
        this.health = 1;
        this.rockExist = true;
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


}
