package org.example;
import processing.core.PApplet;
import processing.core.PImage;

public class Wall {
    PApplet parent;
    int px, py;
    PImage uWall;

    Wall(int x, int y, PApplet parent, PImage uWall){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.uWall = uWall;
    }
    void render(){
        parent.image(uWall,px,py,30,30);
    }
    int x(){return px;}
    int y(){return py;}
}
