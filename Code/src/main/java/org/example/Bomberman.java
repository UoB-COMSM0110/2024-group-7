package org.example;
import processing.core.PApplet;

public class Bomberman extends PApplet{
    int width=960, height=540;
    public void settings() {
        size(width, height);
    }
    public void draw() {
        background(152,190,100);
        rect(0, 0, width, 30);
        for(int i=30; i<width;i+=60) {
            for (int j = 60; j < height; j +=60) {
                square(i, j, 30);
                fill(51);
                noStroke();
            }
        }
    }
    public static void main(String[] args){
        String[] processingArgs = {"Bomberman"};
        Bomberman dungeon = new Bomberman();
        PApplet.runSketch(processingArgs, dungeon);
    }
}