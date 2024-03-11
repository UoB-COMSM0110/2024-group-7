class Map {
  int gens=100;
  Tile [][] tiles = new Tile[rows*9][cols*9];
  int [][] map = new int[rows][cols];
  Random random = new Random();
  PApplet parent;
  PImage wall, rock, crystal, redEnemy;
  
  Map(PApplet parent, PImage wall, PImage rock, PImage crystal, PImage redEnemy){
      this.wall = wall;
      this.parent = parent;
      this.rock = rock;
      this.crystal = crystal;
      this.redEnemy = redEnemy;
  }
  Tile[][] instructions() {
      generator();
  
      for(int f=0; f<rows; f++){
          for(int g=0; g<cols; g++){
              System.out.printf(String.valueOf(map[f][g]));
          }
          println();
      }
      translator();
      for (int i = 0; i < rows*9; i++) {
          for (int j = 0; j < cols*9; j++) {
              if (isNull(tiles[i][j])) {
                  System.out.print(" ");
              }
              if (nonNull(tiles[i][j])) {
                  if (tiles[i][j].type().equals("wall")) {
                      System.out.print("#");
                  }
                  if (tiles[i][j].type().equals("floor")) {
                      System.out.print(" ");
                  }
                  if (tiles[i][j].type().equals("rock")) {
                      System.out.print("®");
                  }
                  if (tiles[i][j].type().equals("crystal")) {
                      System.out.print("©");
                  }
              }
          }
          System.out.println();
      }
      return tiles;
  }
  
  private void generator() {
      int rms=0;
      for(int h=0; h<rows; h++){
          for(int i=0; i<cols; i++){
              map[h][i]=0;
          }
      }
      for(int i=0; i<2; i++) {
          for(int j=0; j<4; j++) {
              map[rows/2-1-1+i][cols/2-1+j]=1;
          }
      }
      for(int a=0; a<gens; a++){
          for(int b=0; b<rows; b++){
              for(int c=0; c<cols; c++){
                  if(map[b][c]==1){
                      int counter=0;
                      for(int d=-1; d<2; d++){
                          for(int e=-1; e<2; e++){
                              if(b+d>=0 && b+d<rows && c+e>=0 && c+e<cols && map[b+d][c+e]!=0){
                                  counter++;
                              }
                          }
                      }
                      for(int d=-1; d<2; d++){
                          for(int e=-1; e<2; e++){
                              if(b+d>=0 && b+d<rows && c+e>=0 && c+e<cols){
                                  if((Math.abs(d)==1 && e==0) || (d==0 && Math.abs(e)==1)){
                                      if(map[b+d][c+e]==0){
                                          int r=random.nextInt(100);
                                          if(counter<3 && r<6){
                                              map[b+d][c+e]=1;
                                          }
                                          if(counter>=3 && counter<=5 && r<1){
                                              map[b+d][c+e]=1;
                                          }
                                      }
                                  }
                              }
                          }
                      }
                  }
              }
          }
      }
      for(int f=0; f<rows; f++){
          for(int g=0; g<cols; g++){
              if(map[f][g]==1){
                  rms++;
              }
          }
      }
      if(rms<(rows*cols/7) || rms>(rows*cols/3)){
          generator();
      }
  }
  
  private void translator() {
      for(int h=0; h<rows; h++){
          for(int i=0; i<cols; i++){
              if(map[h][i]==1){
                  for(int j=0; j<8; j++){
                      for(int k=0; k<8; k++){
                          tiles[h*8+1+k][i*8+1+j]= new Tile(h*8+1+k, i*8+1+j, parent, wall, "wall");
                      }
                  }
              }
          }
      }
      for(int j=0; j<rows*8; j++){
          for(int k=0; k<cols*8; k++){
              if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "wall")){
                  tiles[j-1][k]=new Tile(j-1, k, parent, wall, "wall");
                  tiles[j][k-1]=new Tile(j, k-1, parent, wall, "wall");
                  tiles[j-1][k-1]=new Tile(j-1, k-1, parent, wall, "wall");
              }
          }
      }
  
      for(int j=0; j<rows*8; j++){
          for(int k=0; k<cols*8; k++){
              if(j-1>=0 && k-1>=0 && j+1<(rows*8) && k+1<(cols*8) && tiles[j-1][k-1]!=null && tiles[j-1][k]!=null && tiles[j-1][k+1]!=null
                      && tiles[j][k-1]!=null && tiles[j][k+1]!=null && tiles[j+1][k-1]!=null && tiles[j+1][k]!=null && tiles[j+1][k+1]!=null){
                  tiles[j][k].type="floor";
              }
          }
      }
  
      for(int j=0; j<rows*8; j++){
          for(int k=0; k<cols*8; k++){
              if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "floor") && Objects.equals(tiles[j - 1][k].type, "floor") && Objects.equals(tiles[j][k - 1].type, "floor") && Objects.equals(tiles[j - 1][k - 1].type, "floor")){
                  tiles[j][k].type="wall";
                  tiles[j][k].skin=wall;
              }
          }
      }
      for(int j=0; j<rows*8; j++){
          for(int k=0; k<cols*8; k++){
              if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "floor")) {
                  boolean a=Objects.equals(tiles[j+1][k].type, "floor"), b=Objects.equals(tiles[j][k+1].type, "floor"), c=Objects.equals(tiles[j-1][k].type, "floor"), d=Objects.equals(tiles[j][k-1].type, "floor");
                  if (a ? (b || c || d) : b ? ( c || d) : c && d) {
                      tiles[j][k].crossRoads = true;
                  }
              }
          }
      }
      // breakable walls (rock tiles)
      for(int a=0; a<gens/5; a++){
          for(int j=0; j<rows*8; j++){
              for(int k=0; k<cols*8; k++){
                  int r=random.nextInt(100);
                  if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "rock") && r<2){
                      tiles[j][k].type="rock";
                      tiles[j][k].skin=rock;
                  }
                  if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "floor") && r<1){
                      tiles[j][k].type="rock";
                      tiles[j][k].skin=rock;
                  }
              }
          }
      }
      // breakable walls (crystal tiles)
      for(int a=0; a<gens/6; a++){
          for(int j=0; j<rows*8; j++){
              for(int k=0; k<cols*8; k++){
                  int r=random.nextInt(100);
                  if(tiles[j][k]!=null && Objects.equals(tiles[j][k].type, "rock") && r<1){
                      tiles[j][k].type="crystal";
                      tiles[j][k].skin=crystal;
                  }
              }
          }
      }
      // Assign coordinates to tiles
      for(int j=0; j<rows*8; j++){
          for(int k=0; k<cols*8; k++){
              if(tiles[j][k]!=null) {
                  tiles[j][k].px=(15+k*30);
                  tiles[j][k].py=(75+j*30);
              }
          }
      }
      // clear starting area
      for(int j=0; j<3; j++){
          for(int k=0; k<3; k++){
              if (tiles[37+j][63+k]!=null && !tiles[37+j][63+k].type.equals("wall")) {
                  tiles[37+j][63 +k].type="floor";
              }
          }
      }
  }
}
