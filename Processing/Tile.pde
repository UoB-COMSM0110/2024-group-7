class Tile {
  PApplet parent;
  int px, py;
  PImage skin;
  String type;
  boolean crossRoads;
  
  Tile(int x, int y, PApplet parent, PImage skin, String type){
      this.parent =parent;
      this.px=x;
      this.py=y;
      this.skin = skin;
      if (Objects.equals(type, "floor")) {
          this.type="floor";
      }
      if (Objects.equals(type, "wall")) {
          this.type="wall";
      }
      if (Objects.equals(type, "rock")) {
          this.type="rock";
      }
      if (Objects.equals(type, "crystal")) {
          this.type="crystal";
      }
  }
  void render(){
      if (!Objects.equals(this.type, "floor")) {
          parent.image(skin, px, py, 30, 30);
      }
      else {
          parent.fill(85, 85, 85);
          parent.rect(px, py, 30, 30);
  
      }
  }
  int x(){return this.px;}
  int y(){return this.py;}
  String type(){return type;}
}
