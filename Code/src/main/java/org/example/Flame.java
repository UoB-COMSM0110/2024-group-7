package org.example;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.Iterator;

// This class is used to creat the explosion flame after bomb bombs
public class Flame extends Objects{
    PApplet parent;
    PImage frameImage;
    boolean showed;
    int startTime, duration;
    Flame(int x, int y, PApplet parent, PImage frameImage){
        this.x = x;
        this.y = y;
        this.parent =parent;
        this.frameImage = frameImage;
        this.showed = false;
        this.duration = 1000;
    }

    void render(){
        this.parent.image(frameImage,x,y,30,30);
    }
    void appear(){
        this.startTime = parent.millis();
        this.showed = true;
        activeFlames.add(this);
        //System.out.println("Flame active:" + this.x +", " + this.y);
    }
    public boolean update(){
        if (showed && parent.millis() - startTime >= duration){
            this.showed = false;
            return true;
        }
        return false;
    }

    public static void initializeFlames(PApplet parent){
        for (int row =0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                int x = 15 + col * tile;
                int y = 75 + row * tile;
                flames[col][row] = new Flame(x,y,parent,ResourceManager.flame);
            }
        }
    }

    public static void creatFlame(int x, int y) {
        int explosionDistance = Character.players.get(0).getExplosionDistance();

        int col = (x-15)/tile;
        int row = (y-75)/tile;

        // Mark the center position where the bomb is placed to explode
        flames[col][row].appear();

        // Direction to check: left, right, up, down
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int[] direction : directions) {
            for (int i=1; i<=explosionDistance; i++) {
                int newCol = col + direction[0]*i;
                int newRow = row + direction[1]*i;

                if (Wall.isWallAt(newCol, newRow)) break;

                // Otherwise, place flame
                flames[newCol][newRow].appear();

                boolean stopSpreading = Items.checkAndHandleBreakable(newCol, newRow)
                        || Items.isDoorOrKeyAt(newCol, newRow)
                        || Items.checkAndDestroyExposedItems(newCol, newRow);
                if(stopSpreading) break;
            }
        }
    }

    // Check if flame exist in a position
    public static boolean flameCheck(int x,int y){
        int col = (x - 15)/tile;
        int row = (y - 75)/tile;
        return flames[col][row].showed;
    }

    public boolean isActive() {
        return showed;
    }

    public  static void flameRender(){
        Iterator<Flame> iterator = activeFlames.iterator();
        while(iterator.hasNext()){
            Flame flame = iterator.next();
            flame.render();
            if (flame.update()){
                iterator.remove();
                //System.out.println("Flame inactive:" + flame.x +", " + flame.y);
            }
        }
    }
}
