package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import javax.print.CancelablePrintJob;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Character {
    /*PApplet parent;
    int px, py;
    int health;
    PImage playerImage;*/
    PImage playerImage;
    private int explosionDistance;
    private int maxBombs;

    Player(int x, int y, PApplet parent, PImage playerImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.playerImage = playerImage;
        this.health = 3;
        this.explosionDistance = 1;
        this.maxBombs = 1;
        this.speed = 3;
    }

    public static ArrayList<Player> setPlayer1(PApplet parent) {
        ArrayList<Player> players = new ArrayList<>();
        int x = 15 + tile;
        int y = 15 + 3*tile;
        players.add(new Player(x, y, parent, ResourceManager.pinkBomber));
        return players;
    }

    public void playerMove(){
            if (up) {
                this.up();
            }
            if (down) {
                this.down();
            }
            if (left) {
                this.left();
            }
            if (right) {
                this.right();
            }
    }

    void render(){
        parent.image(playerImage,px,py,30,30);
    }

    // 更新移动方法以设置方向标志
    void up() {
        py -= speed;
    }

    void down() {
        py += speed;
    }

    void left() {
        px -= speed;
    }

    void right() {
        px += speed;
    }

    /*int x(){return px;}
    int y(){return py;}*/

    public void increasePower() {
        this.explosionDistance +=1;
    }

    public int getExplosionDistance() {
        return this.explosionDistance;
    }

    public void increaseMaxBomb() {
        this.maxBombs +=1;
    }

    public int getMaxBombs() {
        return this.maxBombs;
    }

    public void increaseSpeed() {this.speed +=1; }

    /*public int getSpeed() {
        return this.speed;
    }*/
}
