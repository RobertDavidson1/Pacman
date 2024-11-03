package com.pacman.game;

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
            case 1 -> maxFood / 5;
            case 2 -> (maxFood * 2) / 5;
            case 3 -> maxFood;
            default -> maxFood / 5;
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
                grid[x][y] == Pacman.getCurrentSprite().charAt(0); // Pacman (Ghost Case)
    }

    public void updateCell(int x, int y, char newValue) {
        // If we're removing food (replacing food with something else)
        if (grid[x][y] == Assets.FOOD.charAt(0) && newValue != Assets.FOOD.charAt(0)) {
            food--;
        }
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
                if (cell == Assets.GHOST.charAt(0)) {
                    // Find which ghost is at this position
                    Ghost ghostAtPosition = null;
                    for (Ghost ghost : ghosts) {
                        if (ghost.getX() == i && ghost.getY() == j) {
                            ghostAtPosition = ghost;
                            break;
                        }
                    }
                    output = ghostAtPosition != null ? Assets.getColoredGhostString(ghostAtPosition) 
                                                   : Assets.charToColoredString.get(cell);
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
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == Assets.FOOD.charAt(0)) {
                    food++;
                }
            }
        }
    }
}
