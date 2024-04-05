package org.example;
import processing.core.PApplet;
import processing.core.PImage;

public class Enemy extends Player{
    PApplet parent;
    PImage enemyImage;

    Enemy(int x, int y, PApplet parent, PImage enemy) {
        super(x, y, parent, enemy);
        this.parent = parent;
        this.px=x;
        this.py=y;
        this.enemyImage = enemy;
        this.health = 3;
        this.speed = 3;
    }
    void render(){
        parent.image(enemyImage,px,py,30,30);
    }
}