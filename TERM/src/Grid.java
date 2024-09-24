package TERM.src;

public class Grid {
    private char[][] grid;
    private int height;
    private int width;
    private int initialFood;

    public Grid(int height, int width) {
        this.height = height;
        this.width = width;
        grid = new char[height][width];
        this.initialFood = 0;
    }

    public void initGrid() {
        // Fill the entire grid with food initially
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = Assets.FOOD.charAt(0);
                initialFood++;
            }
        }

        // Add walls to the top and bottom rows, decrement initialFood if we overwrite a food tile
        for (int i = 0; i < width; i++) {
            // Top row
            if (grid[0][i] == Assets.FOOD.charAt(0)) {
                initialFood--;
            }
            grid[0][i] = Assets.WALL.charAt(0);

            // Bottom row
            if (grid[height - 1][i] == Assets.FOOD.charAt(0)) {
                initialFood--;
            }
            grid[height - 1][i] = Assets.WALL.charAt(0);
        }

        // Add walls to the far left and right columns, decrement initialFood if we overwrite a food tile
        for (int i = 1; i < height - 1; i++) { // Start from 1 and end at GRID_HEIGHT - 2 to avoid corners
            // Left column
            if (grid[i][0] == Assets.FOOD.charAt(0)) {
                initialFood--;
            }
            grid[i][0] = Assets.WALL.charAt(0);

            // Right column
            if (grid[i][width - 1] == Assets.FOOD.charAt(0)) {
                initialFood--;
            }
            grid[i][width - 1] = Assets.WALL.charAt(0);
        }

    }

    public void displayGrid() {
        for (char[] row : grid) {
            for (char cell : row) {
                // Use the mapping for other characters
                String output = Assets.charToColoredString.getOrDefault(cell, cell + " ");
                System.out.print(output);
                }

            System.out.println();
            }

        }
    }


