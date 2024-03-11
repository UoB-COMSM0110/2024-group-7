package org.example;
import processing.core.PApplet;
import processing.core.PImage;

public class Bomb{
    PApplet parent;
    int px, py;
    PImage bombImage;
    Bomb(int x, int y, PApplet parent, PImage bombImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.bombImage = bombImage;
    }
    void render(){
        parent.image(bombImage,px,py,30,30);
    }
    int x(){return px;}
    int y(){return py;}
}
