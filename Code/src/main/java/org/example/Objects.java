package org.example;

import java.util.ArrayList;

public class Objects extends GameLoop{
    int x, y;
    /*public static int totBombs=0;*/
    public static boolean bomb=false;
    public static Flame [][] flames = new Flame[cols][rows];
    //public static Bomb [][] bombs = new Bomb [cols][rows];
    public static ArrayList<Bomb> bombs = new ArrayList<>();
    public static ArrayList<Flame> activeFlames = new ArrayList<>();

    int x(){return x;}
    int y(){return y;}
}
