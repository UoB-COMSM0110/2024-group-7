package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class BreakableRock extends Wall{
    int health;
    boolean rockExist;
    BreakableRock(int x, int y, PApplet parent, PImage rock) {
        super(x, y, parent, rock);
        this.health = 1;
        this.rockExist = true;
    }
}
