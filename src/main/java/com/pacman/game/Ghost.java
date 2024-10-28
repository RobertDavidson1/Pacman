package com.pacman.game;
import java.util.Random;


public class Ghost {
    // The ghost's current position
    private int x, y;

    // Probability of a good move
    double probabilityGoodMove;

    // The character in the cell before the ghost moved into it
    private static char previousCell = Assets.FOOD.charAt(0);

    private final String color; // Add color property
    private final String name; // Add name property

    public Ghost(int startX, int startY, Grid grid, Pacman pacman, double difficulty, String name) {
        // Initialize the ghost's starting position
        this.x = startX;
        this.y = startY;

        // Determine how good the Ghost moves are
        // The ghost's difficulty level
        this.probabilityGoodMove = 0.3 * difficulty;

        // Assign color based on ghost name
        this.color = switch (name.toLowerCase()) {
            case "blinky" -> Assets.GHOST_RED;
            case "pinky" -> Assets.GHOST_PINK;
            case "inky" -> Assets.GHOST_CYAN;
            default -> Assets.GHOST_RED;
        };
        
        this.name = name;
        
        // Set the previous cell to contain food initially
        previousCell = Assets.FOOD.charAt(0);

        // Update the grid to place the ghost at the starting position
        grid.updateCell(x, y, Assets.GHOST.charAt(0));
    }

    public void move(int pacmanX, int pacmanY, Grid grid) {

        Random random = new Random();
        double randomChoice = random.nextDouble();
        if (randomChoice < probabilityGoodMove) {
            greedyMove(pacmanX, pacmanY, grid);
        } else {
            randomMove(grid); // Otherwise, call randomMove
        }
    }

    public void randomMove(Grid grid) {
        Random random = new Random();
        int[] directions = {-1, 0, 1};
        boolean moved = false;

        while (!moved) {
            // Randomly select a direction to move in
            int incrementX = directions[random.nextInt(3)];
            int incrementY = (incrementX == 0) ? directions[random.nextInt(3)] : 0;

            // Check if the move is valid
            if (Grid.isValidMove(x + incrementX, y + incrementY)) {
                // Restore the item in the previous cell
                grid.updateCell(x, y, previousCell);
                // Store the item in the cell we plan on moving to
                previousCell = grid.getCell(x + incrementX, y + incrementY);
                // Update the ghost's position
                x += incrementX;
                y += incrementY;
                moved = true;
            }
        }
        // Update the grid with the ghost's new position
        grid.updateCell(x, y, Assets.GHOST.charAt(0));
    }

    public void greedyMove(int pacmanX, int pacmanY, Grid grid) {
        // Calculate the difference between the ghost's current position and the pacman's position
        int deltaX = pacmanX - x;
        int deltaY = pacmanY - y;

        // Determine the direction to move in X and Y axes
        int incrementX = deltaX != 0 ? deltaX / Math.abs(deltaX) : 0;
        int incrementY = deltaY != 0 ? deltaY / Math.abs(deltaY) : 0;

        // Move in the X direction if possible
        if (incrementX != 0 && Grid.isValidMove(x + incrementX, y)) {
            grid.updateCell(x , y, previousCell); // Restore the item in previous cell (Ghost doesn't eat food)
            previousCell = grid.getCell(x + incrementX, y); // Store the item in the cell we plan on moving too
            x += incrementX; // Update the ghost's X position
        }
        // If moving in the X direction is not possible, try moving in the Y direction
        else if (incrementY != 0 && Grid.isValidMove(x , y + incrementY)) {
            grid.updateCell(x , y, previousCell); // Restore the item in previous cell (Ghost doesn't eat food)
            previousCell = grid.getCell(x, y + incrementY); // Store the item in the cell we plan on moving too
            y += incrementY; // Update the ghost's Y position
        }

        // Update the grid with the ghost's new position
        grid.updateCell(x, y, Assets.GHOST.charAt(0));

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColor() {
        return color;
    }
}
