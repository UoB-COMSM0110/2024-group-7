#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <math.h>
#include <stdbool.h>

#define ROWS 12
#define COLS 14
#define GENS 100
#define BLANK 32
#define WALL 219
#define DUNGEON 176

void generator(int room[ROWS][COLS]);
void translator(int room[ROWS][COLS]);

int main () {
   int room[ROWS][COLS];
   generator(room);
   translator(room);
   for(int f=0; f<ROWS; f++){
      for(int g=0; g<COLS; g++){
         printf("%i ", room[f][g]);
      }
      printf("\n");
   }
   return(0);
}

void generator(int room[ROWS][COLS]) {
   int rms=0;
   for(int h=0; h<ROWS; h++){
      for(int i=0; i<COLS; i++){
         room[h][i]=0;
      }
   }
   for(int i=0; i<2; i++) {
      for(int j=0; j<4; j++) {
         room[ROWS/2-1-1+i][COLS/2-1+j]=1;
      }
   }
   time_t t=time(NULL);
   srand(t);
   for(int a=0; a<GENS; a++){
      for(int b=0; b<ROWS; b++){
         for(int c=0; c<COLS; c++){
            if(room[b][c]==1){
               int counter=0;
               for(int d=-1; d<2; d++){
                  for(int e=-1; e<2; e++){
                     if(b+d>=0 && b+d<ROWS && c+e>=0 && c+e<COLS && room[b+d][c+e]!=0){
                        counter++;
                     }
                  }
               }
               for(int d=-1; d<2; d++){
                  for(int e=-1; e<2; e++){
                     if(b+d>=0 && b+d<ROWS && c+e>=0 && c+e<COLS){
                        if((abs(d)==1 && e==0) || (d==0 && abs(e)==1)){
                           if(room[b+d][c+e]==0){
                              int r=rand()%100;
                              if(counter<3 && r<6){
                                 room[b+d][c+e]=1;
                              }
                              if(counter>=3 && counter<=5 && r<1){
                                 room[b+d][c+e]=1;
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
   for(int f=0; f<ROWS; f++){
      for(int g=0; g<COLS; g++){
         if(room[f][g]==1){
            rms++;
         }
      }
   }
   if(rms<(ROWS*COLS/7) || rms>(ROWS*COLS/3)){
      generator(room);
   }
   return;
}

void translator(int room[ROWS][COLS]) {
   int tiles[ROWS*9][COLS*9];
   for(int l=0; l<ROWS*9; l++){
      for(int m=0; m<COLS*9; m++){
         tiles[l][m]=BLANK;
      }
   }   
   for(int h=0; h<ROWS; h++){
      for(int i=0; i<COLS; i++){
         if(room[h][i]==1){
            for(int j=0; j<8; j++){
               for(int k=0; k<8; k++){
                  tiles[h*8+1+k][i*8+1+j]=WALL;
               }
            }
         }
      }
   }
   for(int j=0; j<ROWS*8; j++){
      for(int k=0; k<COLS*8; k++){
         if(tiles[j][k]==WALL){
            tiles[j-1][k]=WALL;
            tiles[j][k-1]=WALL;
            tiles[j-1][k-1]=WALL;
         }
      }
   }
   for(int j=0; j<ROWS*8; j++){
      for(int k=0; k<COLS*8; k++){
         if(j-1>=0 && k-1>=0 && j+1<(ROWS*8) && k+1<(COLS*8) && tiles[j-1][k-1]!=BLANK && tiles[j-1][k]!=BLANK && tiles[j-1][k+1]!=BLANK
            && tiles[j][k-1]!=BLANK && tiles[j][k+1]!=BLANK && tiles[j+1][k-1]!=BLANK && tiles[j+1][k]!=BLANK && tiles[j+1][k+1]!=BLANK){
            tiles[j][k]=DUNGEON;
         }
      }
   }
   for(int j=0; j<ROWS*8; j++){
      for(int k=0; k<COLS*8; k++){
         if(tiles[j][k]==DUNGEON && tiles[j-1][k]==DUNGEON && tiles[j][k-1]==DUNGEON && tiles[j-1][k-1]==DUNGEON){
            tiles[j][k]=WALL;
         }
      }
   }
   for(int j=0; j<ROWS*8; j++){
      for(int k=0; k<COLS*8; k++){
         if(tiles[j][k]==DUNGEON){
            tiles[j][k]=BLANK;
         }
      }
   }
   FILE *r=fopen("rooms.txt", "w");
   for(int f=0; f<ROWS; f++){
      for(int g=0; g<COLS; g++){
         if(room[f][g]==1){
            fprintf(r, "■");
         }
         if(room[f][g]==0){
            fprintf(r, " ");
         }
      }
      fprintf(r, "\n");
   }
   FILE *t=fopen("tiles.txt", "w");
   for(int l=0; l<ROWS*8; l++){
      for(int m=0; m<COLS*8; m++){
         if(tiles[l][m]==BLANK){
            fprintf(t, " ");
         }
         if(tiles[l][m]==WALL){
            fprintf(t, "#");
         }
         if(tiles[l][m]==DUNGEON){
            fprintf(t, "░");
         }
      }
      fprintf(t, "\n");
   }
   fclose(t);
   fclose(r);
   return;
}
