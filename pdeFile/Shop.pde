
public static class Shop extends Obstacle {
    PImage shopImage;
    Shop(int x, int y, PApplet parent, PImage shopImage){
        this.parent =parent;
        this.px=x;
        this.py=y;
        this.shopImage = shopImage;
    }

    void render(){
        parent.image(shopImage,px,py,30,30);
    }

    public static ArrayList<Shop> generateShops(int rows, int cols, PApplet parent) {
        // Step 1: Identify all potential positions for a shop
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (!Wall.isWallAt(i, j) &&
                        !(i <= 4 && j <= 4) && !(i >= cols - 4 && j >= rows - 4)) {
                    availableShopPositions.add(new Point(i, j));
                }
            }
        }
        // Step 2: Randomly pick two unique positions from the list of potential positions
        Collections.shuffle(availableShopPositions); // Shuffle the list to randomize
        if (availableShopPositions.size() >= shopsNum) { // Select the first two positions
            for (int k = 0; k < shopsNum; k++) {
                Point selected = availableShopPositions.get(k);
                int x = 15 + selected.x * tile;
                int y = 75 + selected.y * tile;
                shops.add(new Shop(x, y, parent, ResourceManager.shop));
                System.out.println("Shop is at:(" + x + "," + y + ")");
            }
        }
        return shops;
    }

    public static void resetShops(){
        // Step 1: Identify all potential positions for a shop
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (!Wall.isWallAt(i, j) &&
                        !(i <= 4 && j <= 4) && !(i >= cols - 4 && j >= rows - 4)) {
                    availableShopPositions.add(new Point(i, j));
                }
            }
        }
        // Step 2: Randomly pick two unique positions from the list of potential positions
        Collections.shuffle(availableShopPositions); // Shuffle the list to randomize
        if (availableShopPositions.size() >= shopsNum) { // Select the first two positions
            for (int k = 0; k < shopsNum; k++) {
                Point selected = availableShopPositions.get(k);
                int x = 15 + selected.x * tile;
                int y = 75 + selected.y * tile;
                shops.get(k).px = x;
                shops.get(k).py = y;
            }
        }
    }

    public static boolean isShopAt(int i, int j) {
        int x = 15 + i * tile;
        int y = 75 + j * tile;

        // Iterate over all shops to see if any is at the (x, y) position
        for (Shop shop : shops) {
            if (shop.x() == x && shop.y() == y) {
                return true;  // Found a shop at the given grid position
            }
        }
        return false;  // No shop found at this grid position
    }

    public static void shopsRender(){
        for (Shop shop : shops) {
            shop.render();
        }
    }
}
