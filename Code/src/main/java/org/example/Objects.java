package org.example;

public class Objects extends GameLoop{
    int x, y;
    public static int totBombs=0;
    public static boolean bomb=false;
    public static Flame [][] flames = new Flame[cols][rows];
    public static Bomb [][] bombs = new Bomb [cols][rows];

    int x(){return x;}
    int y(){return y;}
}
