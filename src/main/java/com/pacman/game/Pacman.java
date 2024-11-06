package com.pacman.game;

public class Pacman {
    private int x;
    private int y;
    private static String currentSprite;
    private final Grid grid;
    private static boolean invincible = false;
    private int invincibleMovesLeft = 0;
    private static final int INVINCIBLE_DURATION = 60;

    public Pacman(int startX, int startY, Grid grid) {
        this.x = startX;
        this.y = startY;
        currentSprite = Assets.PACMAN_RIGHT;
        this.grid = grid;
        grid.updateCell(x, y, currentSprite.charAt(0));
    }

    public void move(String direction) {
        int newX = x, newY = y;
        String newPacmanSprite = switch (direction.toLowerCase()) {
            case "w" -> { newX--; yield Assets.PACMAN_UP; }
            case "s" -> { newX++; yield Assets.PACMAN_DOWN; }
            case "a" -> { newY--; yield Assets.PACMAN_LEFT; }
            case "d" -> { newY++; yield Assets.PACMAN_RIGHT; }
            default -> {yield currentSprite;}
        };
        if (Grid.isValidMove(newX, newY)) {
            if (grid.getCell(newX, newY) == Assets.FOOD.charAt(0)) {
                Game.addScore(10);
            } else if (grid.getCell(newX, newY) == Assets.INVINCIBLE.charAt(0)) {
                Game.addScore(50);
                invincible = true;
                invincibleMovesLeft = INVINCIBLE_DURATION;
            }
            grid.updateCell(x, y, Assets.EMPTY.charAt(0));
            x = newX;
            y = newY;
            currentSprite = newPacmanSprite;
            grid.updateCell(x, y, currentSprite.charAt(0));
            if (invincibleMovesLeft > 0) {
                invincibleMovesLeft--;
                if (invincibleMovesLeft == 0) {
                    invincible = false;
                }
            }
        }
    }

    public static boolean isInvincible() {
        return invincible;
    }

    public int getInvincibleMovesLeft() {
        return invincibleMovesLeft;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static String getCurrentSprite() {
        return currentSprite;
    }
}
