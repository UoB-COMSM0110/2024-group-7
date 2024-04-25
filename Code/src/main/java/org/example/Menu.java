package org.example;

import processing.core.PApplet;

public class Menu extends GameLoop{
    public static void render(PApplet game) {
        game.background(87, 108, 164);
        game.textFont(Sprites.cherry, 150);
        game.textAlign(CENTER);
        game.fill(237, 87, 33);
        game.text("ボンバーマン", 0, 150, width, 300);
        game.textFont(Sprites.daruma, 35);
        game.textAlign(CENTER);
        game.fill(250, 236, 0);
        game.text("Explore", 0, 450, ((float) width / 4), 100);
        game.text("Battle", ((float) width / 4), 450, ((float) width / 4), 100);
        game.text("Achievements", ((float) width / 4 * 2), 450, ((float) width / 4), 100);
        game.text("Settings", ((float) width / 4 * 3), 450, ((float) width / 4), 100);
        game.textSize(100);
        game.text("Dungeon", 0, 250, width, 100);
    }
}
