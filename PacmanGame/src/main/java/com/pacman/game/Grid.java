package com.pacman.game;

public class Grid {
    // Static 2D array representing the game grid
    static char[][] grid = new char[0][];

    // Variable to keep track of the amount of food in the grid
    private int food;

    // Constructor to initialize the grid with the specified height and width
    public Grid(int height, int width) {
        grid = initGame(height, width);
    }

    // Method to initialize the game grid and set up walls and art
    public char[][] initGame(int height, int width) {
        char[][] grid = initializeGrid(height, width);
        addWallsToGrid(grid, height, width);
        placeArtInGrid(grid, height, width);
        return grid;
    }


    // Initializes the game grid and sets all cells to contain food.
    private char[][] initializeGrid(int height, int width) {
        char[][] grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = Assets.FOOD.charAt(0);
                food++;
            }
        }
        return grid;
    }

    private void addWallsToGrid(char[][] grid, int height, int width) {
        // Add walls to the top and bottom rows
        for (int i = 0; i < width; i++) {
            // Check if the current cell contains food and decrement initialFood if true
            if (grid[0][i] == Assets.FOOD.charAt(0)) {
                food--;
            }
            // Set the current cell to a wall
            grid[0][i] = Assets.WALL.charAt(0);

            // Check if the current cell contains food and decrement initialFood if true
            if (grid[height - 1][i] == Assets.FOOD.charAt(0)) {
                food--;
            }
            // Set the current cell to a wall
            grid[height - 1][i] = Assets.WALL.charAt(0);
        }

        // Add walls to the left and right columns
        for (int i = 1; i < height - 1; i++) {
            // Check if the current cell contains food and decrement initialFood if true
            if (grid[i][0] == Assets.FOOD.charAt(0)) {
                food--;
            }
            // Set the current cell to a wall
            grid[i][0] = Assets.WALL.charAt(0);

            // Check if the current cell contains food and decrement initialFood if true
            if (grid[i][width - 1] == Assets.FOOD.charAt(0)) {
                food--;
            }
            // Set the current cell to a wall
            grid[i][width - 1] = Assets.WALL.charAt(0);
        }
    }

    private void placeArtInGrid(char[][] grid, int height, int width) {
        int startX = (height - 5) / 2;
        int startY = (width - 23) / 2;

        for (int i = 0; i < Assets.art.length; i++) {
            for (int j = 0; j < Assets.art[i].length; j++) {
                grid[startX + i][startY + j] = Assets.art[i][j];
                if (Assets.art[i][j] != Assets.FOOD.charAt(0)) {
                    food--;
                }
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
                grid[x][y] == Pacman.getCurrentSprite().charAt(0); // Pacman (Ghost Case)
    }

    public void updateCell(int x, int y, char newValue) {
        grid[x][y] = newValue;
    }

    public char getCell(int x, int y) {
        return grid[x][y];
    }

    public int getFoodRemaining() {
        return food;
    }

    public void decrementFood() {
        food--;
    }
}