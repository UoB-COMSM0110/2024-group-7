package org.example;

import processing.core.PApplet;
import processing.core.PImage;

public class UltimateAbilities extends GameLoop{
    private static int lastUpdateTime; // 上次更新时间
    private static final float span = 0; // 时间间隔，以毫秒为单位

    public static void generateUltimateFire(Player player, PApplet parent){
        if(!player.ultimateAbility || !player.useAbility) {
            return;
        }
        int startX = ( player.px - 15 ) / tile;
        int startY = ( player.py - 75 ) / tile;
        spiralFlames(startX, startY, cols, rows, Objects.ultimateFlames, parent);
    }

    public static void spiralFlames(int startX, int startY, int cols, int rows, Flame[][] ultimateFlames, PApplet parent) {
        boolean[][] visited = new boolean[cols][rows];
        int[] dx = {0, 1, 0, -1}; // 右，下，左，上
        int[] dy = {1, 0, -1, 0}; // 对应于dx的方向变化

        int x = startX;
        int y = startY;
        int dir = 0; // 开始方向向右
        int steps = cols * rows; // 最大步数

        for (int i = 0; i < steps; i++) {
            int currentTime = parent.millis();
            if (currentTime - lastUpdateTime > span) {
                if (x >= 0 && x < cols && y >= 0 && y < rows && !visited[x][y]) {
                    ultimateFlames[x][y].appearUltimate(); // 调用 appear 方法
                    visited[x][y] = true;
                }

                // 计算下一个点的坐标
                int nextX = x + dx[dir];
                int nextY = y + dy[dir];

                // 检查是否需要改变方向：下一个点越界或者已访问
                if (nextX < 0 || nextX >= cols || nextY < 0 || nextY >= rows || visited[nextX][nextY]) {
                    dir = (dir + 1) % 4; // 改变方向
                    nextX = x + dx[dir];
                    nextY = y + dy[dir];
                }

                x = nextX;
                y = nextY;
                lastUpdateTime = currentTime; // 更新最后更新时间
            }
        }
    }
}
