package TERM;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Pacman {
    // Grid Height and Widths
    static int GRID_HEIGHT = 11;
    static int GRID_WIDTH = 35;

    // Positions of Pacman and Ghost
    static int pacmanX, pacmanY;
    static int ghostX, ghostY;

    // Pacman's current direction
    // Default sprite is facing right
    static String currentPacmanSprite = Assets.PACMAN_RIGHT; //

    // Track the previous tile of the ghost
    static char previousGhostTile;

    // Counters for food
    static int initialFood;
    static int foodRemaining;

    // Difficulty level (how good the ghost is at chasing Pacman)
    static int difficulty;

    static Random random = new Random();

    public static class Assets {
        // Colours represented as ANSI escape codes
        public static final String RESET = "\033[0m";
        public static final String PINK = "\033[38;2;234;130;229m";
        public static final String ORANGE = "\033[38;2;219;133;28m";
        public static final String YELLOW = "\033[38;2;253;255;0m";
        public static final String BLUE = "\033[38;2;33;33;222m";
        public static final String RED = "\033[38;2;229;72;67m";
        public static final String GREEN = "\033[38;2;48;238;39m";

        // Pacman sprites for different directions
        public static final String PACMAN_RIGHT = "ᗧ";
        public static final String PACMAN_LEFT = "ᗤ";
        public static final String PACMAN_UP = "ᗢ";
        public static final String PACMAN_DOWN = "ᗣ";

        // Other game sprites
        public static final String GHOST = "⩍";
        public static final String WALL = "▀";
        public static final String FOOD = "•";
        public static final String EMPTY = " ";

        // Art representing module code
        // R - Red, G - Green, Y - Yellow, B - Blue, • - Food,
        public static final char[][] art = {
                {'R', 'R', 'R', '•', 'G', 'G', 'G', '•', 'R', 'R', '•', '•', 'Y', 'Y', '•', '•', '•', 'G', '•', '•', 'B', 'B', '•'},
                {'R', '•', '•', '•', '•', 'G', '•', '•', '•', '•', 'R', '•', '•', 'Y', '•', '•', 'G', ' ', 'G', '•', '•', 'B', '•'},
                {'R', '•', '•', '•', '•', 'G', '•', '•', '•', '•', 'R', '•', '•', 'Y', '•', '•', 'G', ' ', 'G', '•', '•', 'B', '•'},
                {'R', '•', '•', '•', '•', 'G', '•', '•', 'R', '•', '•', '•', '•', 'Y', '•', '•', 'G', ' ', 'G', '•', '•', 'B', '•'},
                {'R', 'R', 'R', '•', '•', 'G', '•', '•', 'R', 'R', 'R', '•', 'Y', 'Y', 'Y', '•', '•', 'G', '•', '•', 'B', 'B', 'B'},
        };

        // Map characters to their colored string representations
        static Map<Character, String> charToColoredString = new HashMap<>();

        // Initialize the map
        // Allows us to map characters to their colored string representations for printing
        static {
            charToColoredString.put(' ', Assets.EMPTY + " ");
            charToColoredString.put(Assets.FOOD.charAt(0), Assets.ORANGE + Assets.FOOD + Assets.RESET + " ");
            charToColoredString.put(Assets.WALL.charAt(0), Assets.BLUE + Assets.WALL + Assets.RESET + " ");
            charToColoredString.put('R', Assets.RED + Assets.WALL + Assets.RESET + " ");
            charToColoredString.put('G', Assets.GREEN + Assets.WALL + Assets.RESET + " ");
            charToColoredString.put('B', Assets.BLUE + Assets.WALL + Assets.RESET + " ");
            charToColoredString.put('Y', Assets.YELLOW + Assets.WALL + Assets.RESET + " ");
            charToColoredString.put(Assets.GHOST.charAt(0), Assets.PINK + Assets.GHOST + Assets.RESET + " ");
        }
    }

    public static char[][] initGame(int GRID_HEIGHT, int GRID_WIDTH) {
        // Initialize grid
        char[][] grid = new char[GRID_HEIGHT][GRID_WIDTH];

        // Fill the entire grid with food initially
        for (int i = 0; i < GRID_HEIGHT; i++) {
            for (int j = 0; j < GRID_WIDTH; j++) {
                grid[i][j] = Assets.FOOD.charAt(0);
                initialFood++;
            }
        }

        // Add walls to the top and bottom rows, decrement initialFood if we overwrite a food tile
        for (int i = 0; i < GRID_WIDTH; i++) {
            // Top row
            if (grid[0][i] == Assets.FOOD.charAt(0)) {
                initialFood--;
            }
            grid[0][i] = Assets.WALL.charAt(0);

            // Bottom row
            if (grid[GRID_HEIGHT - 1][i] == Assets.FOOD.charAt(0)) {
                initialFood--;
            }
            grid[GRID_HEIGHT - 1][i] = Assets.WALL.charAt(0);
        }

        // Add walls to the far left and right columns, decrement initialFood if we overwrite a food tile
        for (int i = 1; i < GRID_HEIGHT - 1; i++) { // Start from 1 and end at GRID_HEIGHT - 2 to avoid corners
            // Left column
            if (grid[i][0] == Assets.FOOD.charAt(0)) {
                initialFood--;
            }
            grid[i][0] = Assets.WALL.charAt(0);

            // Right column
            if (grid[i][GRID_WIDTH - 1] == Assets.FOOD.charAt(0)) {
                initialFood--;
            }
            grid[i][GRID_WIDTH - 1] = Assets.WALL.charAt(0);
        }

        // Calculate starting positions in the grid for art (Art is 5x23)
        int startX = (GRID_HEIGHT - 5) / 2;
        int startY = (GRID_WIDTH - 23) / 2;

        // Place the art
        for (int i = 0; i < Assets.art.length; i++) {
            for (int j = 0; j < Assets.art[i].length; j++) {
                grid[startX + i][startY + j] = Assets.art[i][j];

                // Decrement initialFood if we overwrite a food tile
                if (Assets.art[i][j] != Assets.FOOD.charAt(0)) {
                    initialFood--;
                }
            }
        }

        // Initialize Pacman's position
        pacmanX = 1;
        pacmanY = 1;

        // Decrement initialFood if Pacman's starting position contains food
        if (grid[pacmanX][pacmanY] == Assets.FOOD.charAt(0)) {
            initialFood--;
        }

        // Place Pacman on the grid
        grid[pacmanX][pacmanY] = currentPacmanSprite.charAt(0);
        initialFood--;

        // Initialize Ghost's position
        ghostX = GRID_HEIGHT - 2;
        ghostY = GRID_WIDTH - 2;

        // Store the original content of the ghost's starting tile
        previousGhostTile = grid[ghostX][ghostY];

        // Place the ghost on the grid
        grid[ghostX][ghostY] = Assets.GHOST.charAt(0);

        return grid;
    }

    public static void showGrid(char[][] grid) {
        for (char[] row : grid) {
            for (char cell : row) {
                if (cell == currentPacmanSprite.charAt(0)) {
                    // Handle Pacman separately because it has the sprite is dynamic
                    System.out.print(Assets.YELLOW + currentPacmanSprite + Assets.RESET + " ");
                } else {
                    // Use the mapping for other characters
                    String output = Assets.charToColoredString.getOrDefault(cell, cell + " ");
                    System.out.print(output);
                }
            }
            System.out.println();
        }
    }

    public static boolean isValidMove(char[][] grid, int x, int y) {
        // A valid move is defined as moving into:
        return grid[x][y] == Assets.EMPTY.charAt(0) || // an empty square
                grid[x][y] == Assets.FOOD.charAt(0) || // a square containing food
                grid[x][y] == currentPacmanSprite.charAt(0); // Pacman's position (Ghost Case)
    }

    public static void movePacman(char[][] grid, String direction) {
        // Store the previous position
        int newX = pacmanX;
        int newY = pacmanY;

        String newPacmanSprite;

        // Determine the new position and sprite based on the direction
        if (direction.equalsIgnoreCase("w")) {
            newX -= 1;
            newPacmanSprite = Assets.PACMAN_UP;
        } else if (direction.equalsIgnoreCase("s")) {
            newX += 1;
            newPacmanSprite = Assets.PACMAN_DOWN;
        } else if (direction.equalsIgnoreCase("a")) {
            newY -= 1;
            newPacmanSprite = Assets.PACMAN_LEFT;
        } else if (direction.equalsIgnoreCase("d")) {
            newY += 1;
            newPacmanSprite = Assets.PACMAN_RIGHT;
        } else {
            return;
        }

        // Check if the move is valid
        if (isValidMove(grid, newX, newY)) {
            if (grid[newX][newY] == Assets.FOOD.charAt(0)) {
                foodRemaining--;
            }

            // Update the grid
            grid[pacmanX][pacmanY] = Assets.EMPTY.charAt(0); // Remove Pacman from old position and eat the food
            pacmanX = newX; // Update Pacman's x position
            pacmanY = newY; // Update Pacman's y position
            currentPacmanSprite = newPacmanSprite; // Update sprite only after a valid move
            grid[pacmanX][pacmanY] = currentPacmanSprite.charAt(0); // Place Pacman with the new sprite
        }
    }

    public static void updateGhostPosition(char[][] grid, int newX, int newY) {
        // Restore the old position with the previous tile content
        grid[ghostX][ghostY] = previousGhostTile;

        ghostX = newX; // Update Ghost's x position
        ghostY = newY; // Update Ghost's y position

        // Store content of new tile before ghost moves onto it
        previousGhostTile = grid[newX][newY];

        // Place the ghost on new position
        grid[newX][newY] = Assets.GHOST.charAt(0);
    }

    public static void moveGhost(char[][] grid, int percentGoodMoves) {
        int chance = random.nextInt(100);

        if (chance < percentGoodMoves) {
            // Good move
            greedyGhostMove(grid);
        } else {
            randomGhostMove(grid);
        }
    }

    public static void greedyGhostMove(char[][] grid) {
        // Calculate difference in x and y coordinates
        int deltaX = pacmanX - ghostX;
        int deltaY = pacmanY - ghostY;

        // Store the direction to move in
        int moveX = 0;
        int moveY = 0;

        // If the ghost is not at the same X position as Pacman
        if (deltaX != 0) {
            moveX = deltaX / Math.abs(deltaX); // Move towards Pacman in the x direction
        }

        // If the ghost is not at the same Y position as Pacman
        if (deltaY != 0) {
            moveY = deltaY / Math.abs(deltaY); // Move towards Pacman in the y direction
        }

        // Check if the move is valid
        // Try horizontal move first
        if (moveX != 0 && isValidMove(grid, ghostX + moveX, ghostY)) {
            updateGhostPosition(grid, ghostX + moveX, ghostY);
        }
        // Try vertical move if horizontal move is not possible
        else if (moveY != 0 && isValidMove(grid, ghostX, ghostY + moveY)) {
            updateGhostPosition(grid, ghostX, ghostY + moveY);
        }
    }

    public static void randomGhostMove(char[][] grid) {
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int[] direction = directions[random.nextInt(4)];

        int newX = ghostX + direction[0];
        int newY = ghostY + direction[1];

        if (isValidMove(grid, newX, newY)) {
            updateGhostPosition(grid, newX, newY);
        }
    }

    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                // For Windows systems
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For UNIX-like systems (Linux, macOS)
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException ex) {
            System.out.println("Error while clearing the screen.");
        }
    }


    public static void main (String[]args){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter A Difficulty Level (1, 2, 3): ");
        difficulty = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        char[][] grid = initGame(GRID_HEIGHT, GRID_WIDTH);
        foodRemaining = initialFood;


        int percentGoodMoves = 30 * difficulty; // 30% good moves for difficulty 1, 60% for difficulty 2, 90% for difficulty 3


        while (true) {
            clearConsole();

            // Show the grid and score
            showGrid(grid);

            // Print the food remaining
            System.out.printf("Food Remaining:  %d / %d%n", foodRemaining, initialFood);

            // Get user input
            System.out.print("Move (WASD): ");
            String input = scanner.nextLine();

            // Move Pacman
            movePacman(grid, input);

            // Move Ghost
            moveGhost(grid, percentGoodMoves);
        }

    }
}
