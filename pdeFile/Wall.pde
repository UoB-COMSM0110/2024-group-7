

public static class Wall extends Obstacle{
    /*PApplet parent;*/
    /*int px, py;*/
    PImage uWall;
    Wall(int x, int y, PApplet parent, PImage uWall){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.uWall = uWall;
    }
    void render(){
        parent.image(uWall,px,py,30,30);
    }

    public static ArrayList<Wall> generateWalls(int rows, int cols, PApplet parent) {
        ArrayList<Wall> walls = new ArrayList<Wall>();
        // Generate walls in a grid pattern
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                // Add wall to the outer border and every other grid cell
                if (i == 0 || j == 0 || i == cols - 1 || j == rows - 1 || (i % 2 == 0 && j % 2 == 0)) {
                    int x = 15 + i * tile;
                    int y = 75 + j * tile;
                    walls.add(new Wall(x, y, parent, ResourceManager.unbreakable));
                }
            }
        }
        return walls;
    }

    public static boolean isWallAt(int i, int j) {
        return i == 0 || j == 0 || i == cols - 1 || j == rows - 1 || (i % 2 == 0 && j % 2 == 0);
    }

    public static void wallsRender(){
        for (Wall wall : walls) {
            wall.render();
        }
    }
}
