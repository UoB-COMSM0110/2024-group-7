package org.example;

import java.util.ArrayList;

public class Objects extends GameLoop{
    int x, y;
    /*public static int totBombs=0;*/
    //public static boolean bomb=false;

    /* This array should be same with the map, and create flames when setup() runs
        just used to ensure the right position of
     */
    public static Flame [][] flames = new Flame[cols][rows];
    //public static Bomb [][] bombs = new Bomb [cols][rows];
    /*public static ArrayList<Bomb> bombs = new ArrayList<>();*/
    public static ArrayList<Flame> activeFlames = new ArrayList<>();

    int x(){return x;}
    int y(){return y;}
}
