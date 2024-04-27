
// This class is used to creat the explosion flame after bomb bombs
public static class Flame extends Objects{
    PApplet parent;
    PImage frameImage;
    boolean showed;
    public int startTime, duration;
    Flame(int x, int y, PApplet parent, PImage frameImage){
        this.x = x;
        this.y = y;
        this.parent =parent;
        this.frameImage = frameImage;
        this.showed = false;
        this.duration = 200;
    }

    void render(){
        this.parent.image(frameImage,x,y,30,30);
    }
    void appear(){
        this.startTime = parent.millis();
        this.showed = true;
        activeFlames.add(this);
        //System.out.println("Flame active:" + this.x +", " + this.y);
    }
    void appearUltimate(){
        this.startTime = parent.millis();
        if(!this.showed) {
            this.showed = true;
            activeUltimateFlames.add(this);
        }
        //System.out.println("Flame active:" + this.x +", " + this.y);
    }
    public boolean update(){
        if (showed && parent.millis() - startTime >= duration){
            this.showed = false;
            return true;
        }
        return false;
    }

    public static void initializeFlames(PApplet parent){
        for (int row =0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                //When the map modified, the conversion between (col,row) and (x,y)should be modified
                int x = 15 + col * tile;
                int y = 75 + row * tile;
                flames[col][row] = new Flame(x,y,parent,ResourceManager.flame);
            }
        }
    }

    public static void resetFlames(){
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Objects.flames[j][i].duration = 200;
                Objects.flames[j][i].showed = false;
            }
        }
    }

    public static void initializeUltimateFlames(PApplet parent){
        for (int row =0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                //When the map modified, the conversion between (col,row) and (x,y)should be modified
                int x = 15 + col * tile;
                int y = 75 + row * tile;
                ultimateFlames[col][row] = new Flame(x,y,parent,ResourceManager.flame);
            }
        }
    }

    public static void initializeEnemyNoHarmFlames(PApplet parent){
        for (int row =0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                //When the map modified, the conversion between (col,row) and (x,y)should be modified
                int x = 15 + col * tile;
                int y = 75 + row * tile;
                enemyNoHarmFlames[col][row] = new Flame(x,y,parent,ResourceManager.flame);
            }
        }
    }

    public static void creatFlame(int x, int y) {
        bomb.play();
        int explosionDistance = Character.players.get(0).getExplosionDistance();

        int col = (x-15)/tile;
        int row = (y-75)/tile;

        // Mark the center position where the bomb is placed to explode
        flames[col][row].appear();

        // Direction to check: left, right, up, down
        int[][] directions = {{-1,0}, {1,0}, {0,-1}, {0,1}};

        for (int[] direction : directions) {
            for (int i=1; i<=explosionDistance; i++) {
                int newCol = col + direction[0]*i;
                int newRow = row + direction[1]*i;

                if (Wall.isWallAt(newCol, newRow) || Shop.isShopAt(newCol, newRow)) break;

                // Otherwise, place flame
                flames[newCol][newRow].appear();

                boolean stopSpreading = Items.checkAndHandleBreakable(newCol, newRow)
                        /*|| Items.isDoorOrKeyAt(newCol, newRow)*/
                        || Items.checkAndDestroyExposedItems(newCol, newRow);
                if(stopSpreading) break;
            }
        }
    }

    // Check if flame exist in a position
    public static boolean flameCheck(int x,int y){
        int col = (x - 15)/tile;
        int row = (y - 75)/tile;
        return flames[col][row].showed;
    }

    public static boolean ultimateFlameCheck(int x,int y){
        int col = (x - 15)/tile;
        int row = (y - 75)/tile;
        return ultimateFlames[col][row].showed;
    }

    public static boolean enemyNoHarmFlameCheck(int x,int y){
        int col = (x - 15)/tile;
        int row = (y - 75)/tile;
        return enemyNoHarmFlames[col][row].showed;
    }

    public boolean isActive() {
        return showed;
    }

    public  static void flameRender(){
        Iterator<Flame> iterator = activeFlames.iterator();
        while(iterator.hasNext()){
            Flame flame = iterator.next();
            flame.render();
            if (flame.update()){
                iterator.remove();
                //System.out.println("Flame inactive:" + flame.x +", " + flame.y);
            }
        }
    }

    public  static void ultimateFlameRender(){
        Iterator<Flame> iterator = activeUltimateFlames.iterator();
        while(iterator.hasNext()){
            Flame flame = iterator.next();
            flame.render();
            if (flame.update()){
                iterator.remove();
                //System.out.println("Flame inactive:" + flame.x +", " + flame.y);
            }
        }
    }
}
