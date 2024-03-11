package org.example;
import processing.core.PApplet;
import processing.core.PImage;


public class Bomb{
    PApplet parent;
    int px, py;
    PImage bombImage;
    boolean bombActive;
    double timer;
    int duration = 3000;
    int attack = 1;
    boolean showed;
    Bomb(int x, int y, PApplet parent, PImage bombImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.bombImage = bombImage;
        this.bombActive = false;
        this.showed = false;
        this.timer = 3.0;
    }
    void render(){
        parent.image(bombImage,px,py,30,30);
    }
    int x(){return px;}
    int y(){return py;}
    int update(){
        if(showed && timer <= 0){
            explode();
            //to return the coordinate with a single number
            return encodeCoordinate(this.px,this.py);
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

}
