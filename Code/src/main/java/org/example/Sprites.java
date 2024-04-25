package org.example;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

import static java.awt.Font.createFont;

public class Sprites extends PApplet{
    public static PImage wall, pinkBomber, basicBomb, redEnemy, rock, flame, shop, door, bombUp, fireUp, speedUp, healthUp;
    static PFont cherry, daruma;

    public static void load(PApplet game) {
        wall = game.loadImage("images/unbreakable_wall.png");
        pinkBomber = game.loadImage("images/player.png");
        basicBomb = game.loadImage("images/bomb.png");
        redEnemy = game.loadImage("images/red_mob.png");
        rock = game.loadImage("images/rock.png");
        flame = game.loadImage("images/fire.png");
        shop = game.loadImage("images/shop.png");
        door = game.loadImage("images/door.png");
        bombUp = game.loadImage("images/bomb_up.png");
        fireUp = game.loadImage("images/fire_up.png");
        speedUp = game.loadImage("images/speed_up.png");
        healthUp = game.loadImage("images/health_up.png");
        cherry = game.createFont("fonts/CherryBombOne-Regular.ttf", 60);
        daruma = game.createFont("fonts/DarumadropOne-Regular.ttf", 60);
    }
}
