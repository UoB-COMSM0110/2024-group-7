package org.example;
import processing.core.PApplet;
import processing.core.PImage;

public class Player{
    PApplet parent;
    int px, py;

    PImage playerImage;
    Player(int x, int y, PApplet parent, PImage playerImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.playerImage = playerImage;
    }
    void render(){
        parent.image(playerImage,px,py,30,30);
    }
    void up(){
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
    }

    int x(){return px;}
    int y(){return py;}
}
