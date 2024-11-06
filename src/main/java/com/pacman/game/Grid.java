package com.pacman.game;

import java.util.Random;

public class Grid {
    // Static 2D array representing the game grid
    static char[][] grid = new char[0][];

    // Variable to keep track of the amount of food in the grid
    private int food;

    // Track maximum possible food
    private final int maxFood;

    // Constructor to initialize the grid with the specified height and width
    public Grid(int height, int width) {
        // Calculate max food (total cells minus walls and art)
        this.maxFood = calculateMaxFood(height, width);
        grid = initGame(height, width);
        recountFood(); // Recount food after initialization
    }

    private int calculateMaxFood(int height, int width) {
        // Total cells minus border walls
        int totalCells = height * width;
        int wallCells = (height * 2) + (width * 2) - 4; // Border walls (subtract 4 for corners counted twice)
        int artCells = 0;
        
        // Count non-food cells in art
        for (char[] row : Assets.art) {
            for (char cell : row) {
                if (cell != Assets.FOOD.charAt(0)) {
                    artCells++;
                }
            }
        }
        
        return totalCells - wallCells - artCells;
    }

    // Method to initialize the game grid and set up walls and art
    public char[][] initGame(int height, int width) {
        char[][] grid = new char[height][width];
        
        // Calculate target food amount based on current round
        int targetFood = switch (Game.getCurrentRound()) {
            case 1 -> maxFood / 100;
            case 2 -> (maxFood * 2) / 100;
            case 3 -> (maxFood * 3) / 100;
            default -> maxFood / 100;
        };

        // First fill with empty spaces
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = Assets.EMPTY.charAt(0);
            }
        }

        // Add walls
        addWallsToGrid(grid, height, width);
        
        // Add art
        placeArtInGrid(grid, height, width);

        // Add food randomly until we reach target amount
        addRandomFood(grid, targetFood, height, width);

        // Add invincibility power-up randomly
        addInvincibilityPowerUp(grid, height, width);

        return grid;
    }

    private void addRandomFood(char[][] grid, int targetFood, int height, int width) {
        food = 0;
        java.util.Random rand = new java.util.Random();
        
        while (food < targetFood) {
            int x = rand.nextInt(grid.length);
            int y = rand.nextInt(grid[0].length);
            
            // Only place food in empty spaces and not in the art area
            if (grid[x][y] == Assets.EMPTY.charAt(0) && !isInArtArea(x, y, height, width)) {
                grid[x][y] = Assets.FOOD.charAt(0);
                food++;
            }
        }
    }

    // Modified helper method to use height and width parameters
    private boolean isInArtArea(int x, int y, int height, int width) {
        int artStartX = (height - 5) / 2;  // 5 is the height of the art
        int artStartY = (width - 23) / 2;  // 23 is the width of the art
        
        // Check if the position is within the art boundaries
        return x >= artStartX && x < artStartX + Assets.art.length &&
               y >= artStartY && y < artStartY + Assets.art[0].length;
    }

    private void addWallsToGrid(char[][] grid, int height, int width) {
        // Add walls to the top and bottom rows
        for (int i = 0; i < width; i++) {
            grid[0][i] = Assets.WALL.charAt(0);
            grid[height - 1][i] = Assets.WALL.charAt(0);
        }

        // Add walls to the left and right columns
        for (int i = 1; i < height - 1; i++) {
            grid[i][0] = Assets.WALL.charAt(0);
            grid[i][width - 1] = Assets.WALL.charAt(0);
        }
    }

    private void placeArtInGrid(char[][] grid, int height, int width) {
        int startX = (height - 5) / 2;
        int startY = (width - 23) / 2;

        for (int i = 0; i < Assets.art.length; i++) {
            for (int j = 0; j < Assets.art[i].length; j++) {
                grid[startX + i][startY + j] = Assets.art[i][j];
            }
        }
    }

    public void showGrid() {
        for (char[] row : grid) {
            for (char cell : row) {
                // Get the colored string representation of the cell
                String output = Assets.charToColoredString.getOrDefault(cell, Assets.RESET + cell + " ");
                System.out.print(output);
            }
            // Move to the next line after printing all cells in the row
            System.out.println();
        }
    }

    public static boolean isValidMove(int x, int y) {
        // A valid move is defined as moving into:
        return grid[x][y] == Assets.FOOD.charAt(0) || // Food
                grid[x][y] == Assets.EMPTY.charAt(0) || // Empty space
                grid[x][y] == Assets.INVINCIBLE.charAt(0) || // Invincibility power-up
                grid[x][y] == Pacman.getCurrentSprite().charAt(0); // Pacman (Ghost Case)
    }

    public void updateCell(int x, int y, char newValue) {
        // If we're removing food (replacing food with something else)
        if (grid[x][y] == Assets.FOOD.charAt(0) && newValue != Assets.FOOD.charAt(0)) {
            food--;
        }
        // If a ghost is moving and revealing food that was underneath it
        else if (newValue == Assets.FOOD.charAt(0)) {
            food++;
        }
        
        // Update the cell with the new value
        grid[x][y] = newValue;
    }

    public char getCell(int x, int y) {
        return grid[x][y];
    }

    public int getFoodRemaining() {
        // Recount food every time to ensure accuracy
        recountFood();
        return food;
    }

    // Add new method to show grid with multiple ghosts
    public void showGrid(Ghost[] ghosts) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                char cell = grid[i][j];
                String output;
                
                // Check if there's a ghost at this position
                Ghost ghostAtPosition = null;
                for (Ghost ghost : ghosts) {
                    if (ghost != null && ghost.getX() == i && ghost.getY() == j) {
                        ghostAtPosition = ghost;
                        break;
                    }
                }
                
                if (ghostAtPosition != null) {
                    output = Assets.getColoredGhostString(ghostAtPosition);
                } else {
                    output = Assets.charToColoredString.getOrDefault(cell, Assets.RESET + cell + " ");
                }
                
                System.out.print(output);
            }
            System.out.println();
        }
    }

    // Add method to count actual food in grid
    private void recountFood() {
        food = 0;
        
        // Count food in grid
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == Assets.FOOD.charAt(0)) {
                    food++;
                }
            }
        }

        // Check ghosts' previous positions for food only if ghosts exist
        Ghost[] ghosts = Game.getGhosts();
        if (ghosts != null) {
            for (Ghost ghost : ghosts) {
                if (ghost != null && ghost.getPreviousCell() == Assets.FOOD.charAt(0)) {
                    food++;
                }
            }
        }
    }

    private void addInvincibilityPowerUp(char[][] grid, int height, int width) {
        Random rand = new Random();
        boolean placed = false;
        
        while (!placed) {
            int x = rand.nextInt(grid.length);
            int y = rand.nextInt(grid[0].length);
            
            // Only place invincibility in empty spaces and not in the art area
            if (grid[x][y] == Assets.EMPTY.charAt(0) && !isInArtArea(x, y, height, width)) {
                grid[x][y] = Assets.INVINCIBLE.charAt(0);
                placed = true;
            }
        }
    }
}
