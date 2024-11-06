package com.pacman.game;

public class Pacman {
    private int x; // Pacman's current x-coordinate
    private int y; // Pacman's current y-coordinate
    private static String currentSprite; // Pacman's current sprite
    private final Grid grid; // Reference to the game grid
    private int invincibleMovesLeft = 0; // Number of moves remaining for invincibility
    private static final int INVINCIBLE_DURATION = 60; // 60 moves of invincibility

    public Pacman(int startX, int startY, Grid grid) {
        this.x = startX; // Initialize x-coordinate
        this.y = startY; // Initialize y-coordinate
        currentSprite = Assets.PACMAN_RIGHT; // Default facing right
        this.grid = grid; // Assign the grid reference

        // Place Pacman on the grid without decrementing food
        grid.updateCell(x, y, currentSprite.charAt(0));
    }

    public void move(String direction) {
        // Initialize new coordinates and sprite for Pacman
        int newX = x, newY = y;

        // Determine the new coordinates and sprite based on the direction
        String newPacmanSprite = switch (direction.toLowerCase()) {
            case "w" -> { newX--; yield Assets.PACMAN_UP; }
            case "s" -> { newX++; yield Assets.PACMAN_DOWN; }
            case "a" -> { newY--; yield Assets.PACMAN_LEFT; }
            case "d" -> { newY++; yield Assets.PACMAN_RIGHT; }
            default -> {yield currentSprite;}
        };

        // Check if the new position is a valid move
        if (Grid.isValidMove(newX, newY)) {
            // Check if moving onto invincibility power-up
            if (grid.getCell(newX, newY) == Assets.INVINCIBLE.charAt(0)) {
                invincibleMovesLeft = INVINCIBLE_DURATION;
            }

            // Remove Pacman from the old position
            grid.updateCell(x, y, Assets.EMPTY.charAt(0));

            // Update Pacman's position
            x = newX;
            y = newY;

            // Update Pacman's sprite to represent the new direction
            currentSprite = newPacmanSprite;

            // Place Pacman at the new position on the grid
            grid.updateCell(x, y, currentSprite.charAt(0));

            // Decrease invincibility counter if active
            if (invincibleMovesLeft > 0) {
                invincibleMovesLeft--;
            }
        }
    }

    public boolean isInvincible() {
        return invincibleMovesLeft > 0;
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
