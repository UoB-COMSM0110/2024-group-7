package org.example;

import processing.core.PApplet;

import java.util.ArrayList;

public class Characters extends GameLoop{
    PApplet parent;
    int px, py, health, speed, coin;
    double damageTime = 0;
    boolean exist;
    public int direction;
    public static ArrayList<Player> players = new ArrayList<>();
    public static ArrayList<Enemy> enemies = new ArrayList<>();

    public static void createPlayers(PApplet game){
        players.add(new Player(15+(49*tile)+14*tile, 75+(33*tile)+5*tile,game, Sprites.pinkBomber));
        players.add(new Player(15+(49*tile)+16*tile, 75+(33*tile)+5*tile,game, Sprites.pinkBomber));
    }

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

    int x(){
        return px;
    }
    int y(){
        return py;
    }
    int getSpeed(){
        return speed;
    }

    public void ifDamageCharacter(){
        if (Flame.flameCheck(px, py) && parent.millis() - damageTime > 1000) {
            this.health -= 1;
            damageTime = parent.millis();
            if (this.health <= 0) {
                this.exist = false;
            }
        }
    }

    public void ifUltimateDamageCharacter(){
        if (Flame.ultimateFlameCheck(px, py) && parent.millis() - damageTime > 1000) {
            System.out.println(this.health);
            this.health -= 1;
            damageTime = parent.millis();
            if (this.health <= 0) {
                this.exist = false;
            }
        }
    }

    public boolean collisionDetectPVP() {
        int x = px, y = py;
        if (direction == 0) {
            x = this.x() + tile / 2;
            y = this.y() + tile / 2 - this.getSpeed();
        }
        if (direction == 2) {
            x = this.x() + tile / 2;
            y = this.y() + tile / 2 + this.getSpeed();
        }
        if (direction == 3) {
            x = this.x() + tile / 2 - this.getSpeed();
            y = this.y() + tile / 2;
        }
        if (direction == 1) {
            x = this.x() + tile / 2 + this.getSpeed();
            y = this.y() + tile / 2;
        }

        int width = tile;
        int height = tile;

        int leftX = x - width / 2;
        int rightX = x + width / 2 - 1;
        int topY = y - height / 2;
        int bottomY = y + height / 2 - 1;

        int leftGridX = (leftX - 15) / tile;
        int topGridY = (topY - 75) / tile;
        int rightGridX = (rightX - 15) / tile;
        int bottomGridY = (bottomY - 75) / tile;

        if (Obstacle.obstacleGridPVP[leftGridX][topGridY] ||
                Obstacle.obstacleGridPVP[rightGridX][topGridY] ||
                Obstacle.obstacleGridPVP[leftGridX][bottomGridY] ||
                Obstacle.obstacleGridPVP[rightGridX][bottomGridY]) {
            return false;
        }

        for (Bomb bomb : Characters.players.get(0).bombs) {
            if (bomb.showed && bomb.bombActive &&
                    dist(x, y, bomb.x() + (float) tile / 2, bomb.y() + (float) tile / 2) <= tile) {
                return false;
            }
        }
        for (Bomb bomb : Characters.players.get(1).bombs) {
            if (bomb.showed && bomb.bombActive &&
                    dist(x, y, bomb.x() + (float) tile / 2, bomb.y() + (float) tile / 2) <= tile) {
                return false;
            }
        }
        return true;
    }
}
