package org.example;

import processing.core.PApplet;
import processing.core.PImage;

// This class is used to creat the explosion frame after bomb bombs
public class Frame {
    int px,py;
    PApplet parent;
    PImage frameImage;
    int radius;
    int attack;

    Frame(int x, int y, int radius, int attack, PApplet parent, PImage frameImage){
        this.px = x;
        this.py = y;
        this.radius = radius;
        this.attack = attack;
        this.parent =parent;
        this.frameImage = frameImage;
    }

    void render(){
        parent.image(frameImage,px,py,30,30);
    }
}
