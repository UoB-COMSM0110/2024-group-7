package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import javax.print.CancelablePrintJob;
import java.util.ArrayList;
import java.util.Random;

public class Player extends Character {
    PImage playerImage;
    private int explosionDistance;
    private int maxBombs;

    Player(int x, int y, PApplet parent, PImage playerImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.playerImage = playerImage;
        this.health = 1;
        this.explosionDistance = 1;
        this.maxBombs = 1;
        this.speed = 3;
        this.exist = true;
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

    public static void absorbToIntersection(){
        if(!(up || right || left || down)) {
            Character.players.get(0).px = Math.round(Character.players.get(0).px / 15.0f) * 15;
            Character.players.get(0).py = Math.round(Character.players.get(0).py / 15.0f) * 15;
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
    public void increaseLife() {this.health += 1;}

    public static void player1Render(){
        players.get(0).ifDamageCharacter();
        if(Character.players.get(0).exist){
            Character.players.get(0).render();
        }else {
            GameLoop.gameLost = true;
        }
    }
}
