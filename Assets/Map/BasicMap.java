Tile [] grid = new Tile [1000];

for (int i = 0; i < rows*9; i++) {
        for (int j = 0; j < cols*9; j++) {
        if (tiles[i][j].type().equals("wall") || tiles[i][j].type().equals("rock") || tiles[i][j].type().equals("crystal")){
tiles[i][j].render();
                    }
                            }
                            }
                            for (int i = 15; i < width - 15; i += tile) {
grid[cntGrid] = new Tile(i, 75, this, wall, "wall");
grid[cntGrid].render();
cntGrid++;
grid[cntGrid] = new Tile(i, height-15-tile, this, wall, "wall");
grid[cntGrid].render();
cntGrid++;
        }
        for (int j = 75; j < height - 15; j += tile) {
grid[cntGrid] = new Tile(15, j, this, wall, "wall");
grid[cntGrid].render();
cntGrid++;
grid[cntGrid] = new Tile(width-15-tile, j, this, wall, "wall");
grid[cntGrid].render();
cntGrid++;
        }
        for (int i = 75; i < width - 15-tile; i += 60) {
        for (int j = 135; j < height - 15-tile; j += 60) {
grid[cntGrid] = new Tile(i, j, this, wall, "wall");
grid[cntGrid].render();
cntGrid++;
        }
        }



                    /*
            for (int i = 15; i < width - 15; i += tile) {
                grid[cntGrid] = new Tile(i, 75, this, wall, "wall");
                grid[cntGrid].render();
                cntGrid++;
                grid[cntGrid] = new Tile(i, height-15-tile, this, wall, "wall");
                grid[cntGrid].render();
                cntGrid++;
            }
            for (int j = 75; j < height - 15; j += tile) {
                grid[cntGrid] = new Tile(15, j, this, wall, "wall");
                grid[cntGrid].render();
                cntGrid++;
                grid[cntGrid] = new Tile(width-15-tile, j, this, wall, "wall");
                grid[cntGrid].render();
                cntGrid++;
            }
            for (int i = 75; i < width - 15-tile; i += 60) {
                for (int j = 135; j < height - 15-tile; j += 60) {
                    grid[cntGrid] = new Tile(i, j, this, wall, "wall");
                    grid[cntGrid].render();
                    cntGrid++;
                }
            }
            totGrid=cntGrid;
            cntGrid=0;
             */

        for (int i = 0; i < 15; i++) {
        for (int j = 0; j < 31; j++) {
        if (nonNull(tiles[32+i][55+j]) && (tiles[32+i][55+j].type().equals("wall") || tiles[32+i][55+j].type().equals("rock") || tiles[32+i][55+j].type().equals("crystal"))){
tiles[32+i][55+j].setX((15+j*tile));
tiles[32+i][55+j].setY((75+i*tile));
println(i, j, tiles[32+i][55+j].y(), tiles[32+i][55+j].x(), tiles[32+i][55+j].type());
tiles[32+i][55+j].render();
                    }
                            }
                            }