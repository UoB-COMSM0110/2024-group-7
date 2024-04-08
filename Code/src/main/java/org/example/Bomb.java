package org.example;
import processing.core.PApplet;
import processing.core.PImage;


public class Bomb extends Objects{
    PApplet parent;
    PImage bombImage;
    boolean bombActive;
    double timer;
    int duration = 3000;
    int attack = 1;
    boolean showed;
    Bomb(int x, int y, PApplet parent, PImage bombImage){
        this.parent =parent;
        this.x=x;
        this.y=y;
        this.bombImage = bombImage;
        this.bombActive = false;
        this.showed = false;
        this.timer = 3.0;
    }

    public static void initializeBombs(PApplet parent){
        for (int row =0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                int x = 15 + col * tile;
                int y = 75 + row * tile;
                bombs[col][row] = new Bomb(x,y,parent,ResourceManager.basicBomb);
            }
        }
    }
    void render(){
        parent.image(bombImage,x,y,30,30);
    }

    public static void setBombIfPossible(){
        if (bomb) {
            if (totBombs < Character.players.get(0).getMaxBombs()) {
                int playerCenterX = Character.players.get(0).x() + tile / 2;
                int playerCenterY = Character.players.get(0).y() + tile / 2;

                int col = (playerCenterX - 15) / tile;
                int row = (playerCenterY - 75) / tile;

                bombs[col][row].showed = true;
                bombs[col][row].timer = 3.0;
                totBombs++;
            }
            bomb = false;
        }
        if (totBombs>0) {
            for (Bomb[] row : bombs) {
                for (Bomb bomb : row) {
                    if (!bomb.hasExpired()) {
                        bomb.render();
                    } else {
                        bomb.showed = false;
                        totBombs--;
                    }
                }
            }
        }
    }
    int update(){
        if(showed && timer <= 0){
            explode();
            //to return the coordinate with a single number
            return encodeCoordinate(this.x,this.y);
        }
        if (showed){
            timer -= 1.0/parent.frameRate;
            return 0;
        }
        return 0;
    }

    // Check if the bomb has reached the time to expired
    boolean hasExpired(){
        return parent.millis() - timer > duration;
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
        for (Bomb[] row : bombs) {
            for (Bomb bomb : row) {
                int packedNumber = bomb.update();
                if (bomb.showed) {
                    bomb.render();
                }
                if (packedNumber != 0) {
                    int x = packedNumber / 10000;
                    int y = packedNumber % 10000;
                    Flame.creatFlame(x, y);
                }
            }
        }
    }
}
