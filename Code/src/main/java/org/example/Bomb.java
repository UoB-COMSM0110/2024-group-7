package org.example;
import processing.core.PApplet;
import processing.core.PImage;

import java.util.Iterator;

public class Bomb extends Objects{
    PApplet parent;
    PImage bombImage;
    boolean bombActive;
    double timer;
    double setupTime;
    int duration = 3000;
    int attack = 1;
    boolean showed;
    Bomb(int x, int y, PApplet parent, PImage bombImage){
        this.parent =parent;
        this.x=x;
        this.y=y;
        this.bombImage = bombImage;
        this.bombActive = false;
        this.showed = true;
        this.timer = 3.0;
        this.setupTime = parent.millis();

    }

    void render(){
        parent.image(bombImage,x,y,30,30);
    }

    public static void setBombIfPossible(PApplet parent){
        if (bomb) {
            if (findCurrentBombsNumber() < Character.players.get(0).getMaxBombs()) {
                int playerCenterX = Character.players.get(0).x() + tile / 2 - 15;
                int playerCenterY = Character.players.get(0).y() + tile / 2 - 15;
                bombs.add(new Bomb(playerCenterX, playerCenterY, parent, ResourceManager.basicBomb));
            }
            bomb = false;
        }
        if (findCurrentBombsNumber()>0) {
            for (Bomb bomb : bombs) {
                if (!bomb.hasExpired()) {
                    bomb.render();
                } else {
                    bomb.showed = false;
                }
            }
        }
    }

    int update(){
        if(showed && parent.millis() - setupTime > duration){
            explode();
            //to return the coordinate with a single number
            return encodeCoordinate(this.x,this.y);
        }
        return 0;
    }

    // Check if the bomb has reached the time to expired
    boolean hasExpired(){
        return parent.millis() - setupTime > duration;
    }
    void explode(){
        showed = false;
        bombActive = false;
    }

    public static int encodeCoordinate(int x, int y) {
            // Combine x and y coordinates using the delimiter
            return (x * 10000) + y;
    }

    public static void bombRender(){
        Iterator<Bomb> iterator = bombs.iterator();
        while (iterator.hasNext()) {
            Bomb bomb = iterator.next();
            int packedNumber = bomb.update();
            if (bomb.showed) {
                bomb.render();
            } else {
                iterator.remove();  // 删除showed为false的元素
            }
            if (packedNumber != 0) {
                int x = packedNumber / 10000;
                int y = packedNumber % 10000;
                Flame.creatFlame(x, y);
            }
        }
    }

    public static int findCurrentBombsNumber(){
        int number =0;
        for(Bomb bomb : bombs){
            if(bomb.showed){
                number++;
            }
        }
        return number;
    }
}
