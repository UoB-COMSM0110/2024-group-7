package org.example;
import processing.core.PApplet;
import processing.core.PImage;

public class Player{
    public static final int tile=30;
    PApplet parent;
    int px, py;
    int health;
    PImage playerImage;
    private int explosionDistance;
    private int maxBombs;
    private int speed;
    Player(int x, int y, PApplet parent, PImage playerImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.playerImage = playerImage;
        this.health = 3;
        this.explosionDistance = 1;
        this.maxBombs = 1;
        this.speed = 5;
    }
    void render(){
        parent.image(playerImage,px,py,30,30);
    }
    /*void up(){
        py-=5;
    }
    void down() {
        py+=5;
    }
    void left() {
        px-=5;
    }
    void right() {
        px+=5;
    }*/

    void up() {
        py -= speed;
        alignToGridVertical();
    }

    void down() {
        py += speed;
        alignToGridVertical();
    }

    void left() {
        px -= speed;
        alignToGridHorizontal();
    }

    void right() {
        px += speed;
        alignToGridHorizontal();
    }

    // 对齐到最近的网格线 - 水平方向
    private void alignToGridHorizontal() {
        // 计算距离最近的网格线的偏移量
        int closestGridLineX = (Math.round((float)(px - 15) / tile) * tile) + 15;
        // 如果玩家的速度大于他们与最近网格线的距离，调整位置到最近的网格线
        if (Math.abs(px - closestGridLineX) < speed) {
            px = closestGridLineX;
        }
    }

    // 对齐到最近的网格线 - 垂直方向
    private void alignToGridVertical() {
        // 计算距离最近的网格线的偏移量
        int closestGridLineY = (Math.round((float)(py - 75) / tile) * tile) + 75;
        // 如果玩家的速度大于他们与最近网格线的距离，调整位置到最近的网格线
        if (Math.abs(py - closestGridLineY) < speed) {
            py = closestGridLineY;
        }
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
