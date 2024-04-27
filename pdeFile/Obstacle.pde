
public static class Obstacle extends GameLoop{
    /*public static ArrayList<Obstacle> obstacle = new ArrayList<>();*/
    PApplet parent;
    int px;
    int py;

    public static ArrayList<ArrayList<BreakableRock>> manyRocks = new ArrayList<>();
    public static ArrayList<ArrayList<Wall>> manyWalls = new ArrayList<>();

    public static ArrayList<Wall> walls;
    public static ArrayList<BreakableRock> rocks = new ArrayList<>();
    public static ArrayList<BreakableRock> theOriginalRocks = new ArrayList<>();
    public static ArrayList<BreakableRock> lessRocks = new ArrayList<>();
    public static ArrayList<Shop> shops = new ArrayList<>();
    public static ArrayList<Point> availableShopPositions = new ArrayList<Point>();
    public static int shopsNum = 2;
    public static boolean[][]obstacleGrid = new boolean[cols][rows];
    public static boolean[][]obstacleGridPVP = new boolean[cols][rows];

    public static void initializeObstacleGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                obstacleGrid[j][i] = false;
            }
        }
        for (BreakableRock rock : Obstacle.rocks) {
            int gridX = (rock.x() - 15) / tile;
            int gridY = (rock.y() - 75) / tile;
            obstacleGrid[gridX][gridY] = true;
        }
        for (Wall wall : Obstacle.walls) {
            int gridX = (wall.x() - 15) / tile;
            int gridY = (wall.y() - 75) / tile;
            obstacleGrid[gridX][gridY] = true;
        }
        for (Shop shop : Obstacle.shops) {
            int gridX = (shop.x() - 15) / tile;
            int gridY = (shop.y() - 75) / tile;
            obstacleGrid[gridX][gridY] = true;
        }
    }

    public static void initializeObstacleGridPVP() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                obstacleGridPVP[j][i] = false;
            }
        }
        for (BreakableRock rock : Obstacle.lessRocks) {
            int gridX = (rock.x() - 15) / tile;
            int gridY = (rock.y() - 75) / tile;
            obstacleGridPVP[gridX][gridY] = true;
        }
        for (Wall wall : Obstacle.walls) {
            int gridX = (wall.x() - 15) / tile;
            int gridY = (wall.y() - 75) / tile;
            obstacleGridPVP[gridX][gridY] = true;
        }
        /*for (Shop shop : Obstacle.shops) {
            int gridX = (shop.x() - 15) / tile;
            int gridY = (shop.y() - 75) / tile;
            obstacleGrid[gridX][gridY] = true;
        }*/
    }

    public static void removeRockFromObstacleGrid(BreakableRock rock) {
        int gridX = (rock.x() - 15) / tile;
        int gridY = (rock.y() - 75) / tile;

        obstacleGrid[gridX][gridY] = false;
    }

    public static void removeRockFromObstacleGridPVP(BreakableRock rock) {
        int gridX = (rock.x() - 15) / tile;
        int gridY = (rock.y() - 75) / tile;
        //System.out.println("x = "+ gridX + "      y = "+ gridY);
        obstacleGridPVP[gridX][gridY] = false;
    }

    public static boolean areThereRocks(int x, int y) {
        int gridX = (x - 15) / tile;
        int gridY = (y - 75) / tile;
        return obstacleGrid[gridX][gridY];
    }
    int x(){return px;}
    int y(){return py;}
}
