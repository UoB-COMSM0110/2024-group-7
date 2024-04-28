import ddf.minim.*;
public static class ResourceManager{
    public static PImage unbreakable;
    public static PImage blueBomber;
    public static PImage pinkBomber;
    public static PImage basicBomb;
    public static PImage redEnemy;
    public static PImage rock;
    public static PImage flame;
    public static PImage shop;
    public static PImage mainMenu;
    public static PImage logo;
    public static PImage gear;
    public static PImage floor;
    public static void loadAllImages(PApplet app) {
        unbreakable = app.loadImage("images/unbreakable_wall.png");
        blueBomber = app.loadImage("images/player.png");
        pinkBomber = app.loadImage("images/beauty_bomber.png");
        basicBomb = app.loadImage("images/bomb.png");
        redEnemy = app.loadImage("images/red_mob.png");
        rock = app.loadImage("images/rock.png");
        flame = app.loadImage("images/fire.png");
        shop = app.loadImage("images/shop.png");
        mainMenu = app.loadImage("images/menu.png");
        logo = app.loadImage("images/logo.png");
        gear = app.loadImage("images/gear.png");
        floor = app.loadImage("images/floor.jpg");
    }
}
