package com.pacman.game;

public class Pacman {
    private int x; // Pacman's current x-coordinate
    private int y; // Pacman's current y-coordinate
    private static String currentSprite; // Pacman's current sprite
    private final Grid grid; // Reference to the game grid

    public Pacman(int startX, int startY, Grid grid) {
        this.x = startX; // Initialize x-coordinate
        this.y = startY; // Initialize y-coordinate
        currentSprite = Assets.PACMAN_RIGHT; // Default facing right
        this.grid = grid; // Assign the grid reference

        // Place Pacman on the grid
        grid.updateCell(x, y, currentSprite.charAt(0));
        grid.decrementFood(); // Decrement the food count because Pacman is placed on food
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
            // If the new position contains food, decrement the food count
            if (grid.getCell(newX, newY) == Assets.FOOD.charAt(0)) {
                grid.decrementFood();
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
        }
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
