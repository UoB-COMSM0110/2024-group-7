package org.example;
import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage;

public class Bomberman extends PApplet {
    boolean menu=true, play=false,  up=false, down=false, left=false, right=false, bomb=false;
    int width=960, height=540;
    public void settings() {
        size(width, height);
        //surface.setTitle("BOMBERMAN DUNGEON");
        //noLoop();
    }

    //define variables
    PImage  player1Image;
    Player one;

    //load variables
    public void setup(){
        player1Image = loadImage("images/player.png");
        one = new Player(45, 105,this, player1Image);
        println("Setup complete.");
    }


    public void draw() {
        if(menu) {
            background(87, 108, 164);
            PFont Cherry = createFont("CherryBombOne-Regular.ttf", 60);
            PFont Daruma = createFont("DarumadropOne-Regular.ttf", 60);
            //String[] fontList = PFont.list();
            //printArray(fontList);
            textFont(Cherry, 150);
            textAlign(CENTER);
            fill(237, 87, 33);
            text("ボンバーマン", 0, 150, width, 300);
            textFont(Daruma, 35);
            textAlign(CENTER);
            fill(250, 236, 0);
            text("Play", 0, 450, ((float) width / 4), 100);
            text("Character", ((float) width / 4), 450, ((float) width / 4), 100);
            text("Achievements", ((float) width / 4 * 2), 450, ((float) width / 4), 100);
            text("Settings", ((float) width / 4 * 3), 450, ((float) width / 4), 100);
            textSize(100);
            text("Dungeon", 0, 250, width, 100);
        }
        if(play){
            menu=false;
            background(165, 165, 165);
            fill(87, 108, 164);
            noStroke();
            rect(15, 75, 930, 450);
            fill(93, 88, 95);
            for (int i = 15; i < width - 15; i += 30) {
                square(i, 75, 30);
                square(i, height - 15 - 30, 30);
            }
            for (int j = 75; j < height - 15; j += 30) {
                square(15, j, 30);
                square(width - 15-30, j, 30);
            }
            for (int i = 75; i < width - 15-30; i += 60) {
                for (int j = 135; j < height - 15-30; j += 60) {
                    square(i, j, 30);
                }
            }
            one.render();
        }
        if(up){
            one.up();
            up=false;
        }
        if(down){
            one.down();
            down=false;
        }
        if(left){
            one.left();
            left=false;
        }
        if(right){
            one.right();
            right=false;
        }
    }
    public void mouseClicked() {
        if (mouseX>=150 && mouseX<350) {
            link("https://github.com/UoB-COMSM0110/2024-group-7");
        }
        if (mouseX>=0 && mouseX<(width/4) && mouseY>=450 && mouseY<540) {
            play=true;
        }
    }
    public void keyPressed(){
        if (key=='w') {
            up=true;
        }
        if (key == 's'){
            down=true;
        }
        if (key =='a'){
            left=true;
        }
        if (key =='d'){
            right=true;
        }
        if (keyCode == SHIFT){
            bomb=true;
        }
    }
    public static void main(String[] args){
//        String[] processingArgs = {"Bomberman"};
//        Bomberman dungeon = new Bomberman();
//        PApplet.runSketch(processingArgs, dungeon);
        PApplet.main("org.example.Bomberman");
    }
}