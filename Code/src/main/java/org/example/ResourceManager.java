package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class ResourceManager{
    public static PImage unbreakable;
    public static PImage pinkBomber;
    public static PImage basicBomb;
    public static PImage redEnemy;
    public static PImage rock;
    public static PImage flame;
    public static PImage shop;

    public static void loadAllImages(PApplet app) {
        unbreakable = app.loadImage("images/unbreakable_wall.png");
        pinkBomber = app.loadImage("images/player.png");
        basicBomb = app.loadImage("images/bomb.png");
        redEnemy = app.loadImage("images/red_mob.png");
        rock = app.loadImage("images/rock.png");
        flame = app.loadImage("images/fire.png");
        shop = app.loadImage("images/shop.png");
    }
}
