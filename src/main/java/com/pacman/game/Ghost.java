package com.pacman.game;
import java.util.Random;


public class Ghost {
    // The ghost's current position
    private int x, y;

    // Probability of a good move
    double probabilityGoodMove;

    // The character in the cell before the ghost moved into it
    private char previousCell = Assets.EMPTY.charAt(0);

    private final String color; // Add color property
    private final String name; // Add name property

    public Ghost(int startX, int startY, Grid grid, Pacman pacman, double difficulty, String name) {
        this.x = startX;
        this.y = startY;
        this.probabilityGoodMove = 0.3 * difficulty;
        
        this.color = switch (name.toLowerCase()) {
            case "blinky" -> Assets.GHOST_RED;
            case "pinky" -> Assets.GHOST_PINK;
            case "inky" -> Assets.GHOST_CYAN;
            default -> Assets.GHOST_RED;
        };
        
        this.name = name;
        this.previousCell = Assets.EMPTY.charAt(0);
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
                // Store the current cell's content before moving
                char currentCell = grid.getCell(x + incrementX, y + incrementY);
                
                // Restore the previous cell's content
                grid.updateCell(x, y, previousCell);
                
                // Update position
                x += incrementX;
                y += incrementY;
                
                // Store the new cell's content before placing ghost
                previousCell = currentCell;
                
                // Place ghost in new position
                grid.updateCell(x, y, Assets.GHOST.charAt(0));
                moved = true;
            }
        }
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
            char currentCell = grid.getCell(x + incrementX, y);
            grid.updateCell(x, y, previousCell);
            previousCell = currentCell;
            x += incrementX;
        }
        // If moving in the X direction is not possible, try moving in the Y direction
        else if (incrementY != 0 && Grid.isValidMove(x, y + incrementY)) {
            char currentCell = grid.getCell(x, y + incrementY);
            grid.updateCell(x, y, previousCell);
            previousCell = currentCell;
            y += incrementY;
        }

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

    public char getPreviousCell() {
        return previousCell;
    }

    public void setPreviousCell(char cell) {
        this.previousCell = cell;
    }
}
