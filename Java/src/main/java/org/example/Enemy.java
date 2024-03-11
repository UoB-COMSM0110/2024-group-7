package org.example;
import processing.core.PApplet;
import processing.core.PImage;

public class Enemy{
    PApplet parent;
    int px, py;

    PImage enemyImage;
    Enemy(int x, int y, PApplet parent, PImage enemyImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.enemyImage = enemyImage;
    }
    void render(){
        parent.image(enemyImage,px,py,30,30);
    }

    int x(){return px;}
    int y(){return py;}
}