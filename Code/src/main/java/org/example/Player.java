package org.example;
import processing.core.PApplet;
import processing.core.PImage;

public class Player{
    PApplet parent;
    int px, py;
    int health;
    PImage playerImage;
    private int explosionDistance;
    private int maxBombs;
    int speed;
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

    int x(){return px;}
    int y(){return py;}

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

    public int getSpeed() {
        return this.speed;
    }
}
