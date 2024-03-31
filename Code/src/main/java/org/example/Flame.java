package org.example;

import processing.core.PApplet;
import processing.core.PImage;

// This class is used to creat the explosion frame after bomb bombs
public class Flame {
    int px,py;
    PApplet parent;
    PImage frameImage;
    boolean showed;
    double timer;
    int startTime, duration;
    Flame(int x, int y, PApplet parent, PImage frameImage){
        this.px = x;
        this.py = y;
        this.parent =parent;
        this.frameImage = frameImage;
        this.showed = false;
        this.duration = 1000;
    }

    void render(){
        this.parent.image(frameImage,px,py,30,30);
    }
    void appear(){
        this.startTime = parent.millis();
        this.showed = true;
    }
    void update(){
        if (showed && parent.millis() - startTime >= duration){
            this.showed = false;
            //System.out.println(this.px+" "+this.py+"switched");
        }
    }
    int x(){return px;}
    int y(){return py;}

    public boolean isActive() {
        return showed;
    }

}
