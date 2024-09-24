package TERM;
import java.util.Scanner;

public class Pacman {
    static int HEIGHT = 11;
    static int WIDTH = 35;


    // Positions of Pacman and Ghost
    static int pacmanX, pacmanY;
    static int ghostX, ghostY;


    public static class Assets{
        // Colours    
        public static final String RESET = "\033[0m";
        public static final String PINK = "\033[38;2;234;130;229m";
        public static final String ORANGE = "\033[38;2;219;133;28m"; 
        public static final String YELLOW = "\033[38;2;253;255;0m"; 
        public static final String BLUE = "\033[38;2;33;33;222m";
        public static final String RED = "\033[38;2;229;72;67m";
        public static final String GREEN = "\033[38;2;48;238;39m";
        
        // Characters
        public static final String PACMAN = "⬤";
        public static final String GHOST = "O";
        public static final String WALL = "▀";
        public static final String FOOD = "•";
        public static final String EMPTY = " ";
    
        public static final char[][] art = {
                {'R', 'R', 'R', '•', 'G', 'G', 'G', '•', 'R', 'R', '•', '•', 'Y', 'Y', '•', '•', '•', 'G', '•', '•', 'B', 'B', '•'},
                {'R', '•', '•', '•', '•', 'G', '•', '•', '•', '•', 'R', '•', '•', 'Y', '•', '•', 'G', ' ', 'G', '•', '•', 'B', '•'},
                {'R', '•', '•', '•', '•', 'G', '•', '•', '•', '•', 'R', '•', '•', 'Y', '•', '•', 'G', ' ', 'G', '•', '•', 'B', '•'},
                {'R', '•', '•', '•', '•', 'G', '•', '•', 'R', '•', '•', '•', '•', 'Y', '•', '•', 'G', ' ', 'G', '•', '•', 'B', '•'},
                {'R', 'R', 'R', '•', '•', 'G', '•', '•', 'R', 'R', 'R', '•', 'Y', 'Y', 'Y', '•', '•', 'G', '•', '•', 'B', 'B', 'B'},
        };
    }

    public static char[][] initGrid(int HEIGHT, int WIDTH) {
        // Initialize grid
        char[][] grid = new char[HEIGHT][WIDTH];

        // Fill the grid with food initially
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                grid[i][j] = Assets.FOOD.charAt(0);
            }
        }


        // Add walls to the top and bottom rows
        for (int i = 0; i < WIDTH; i++) {
            grid[0][i] = Assets.WALL.charAt(0); // Top row
            grid[HEIGHT - 1][i] = Assets.WALL.charAt(0); // Bottom Row
        }

        // Add walls to the far left and right columns
        for (int i = 0; i < HEIGHT; i++) {
            grid[i][0] = Assets.WALL.charAt(0); // Left column
            grid[i][WIDTH - 1] = Assets.WALL.charAt(0); // Right column
        }

        // Calculate starting positions for art
        int startX = (HEIGHT - 5) / 2;
        int startY = (WIDTH - 23) / 2;

        // Place the art
        for (int i = 0; i < Assets.art.length; i++) {
            System.arraycopy(Assets.art[i], 0, grid[startX + i], startY, Assets.art[i].length);

        }

        // Initialize Pac man's position
        pacmanX = 1;
        pacmanY = 1;
        grid[pacmanX][pacmanY] = Assets.PACMAN.charAt(0);

        // Initialize Ghost's position
        ghostX = HEIGHT - 2;
        ghostY = WIDTH - 2;
        grid[ghostX][ghostY] = Assets.GHOST.charAt(0);

        return grid;

    }

    public static void showGrid(char[][] grid) {
        for (char[] chars : grid) {
            for (char aChar : chars) {
                if (aChar == Assets.PACMAN.charAt(0)) {
                    System.out.print(Assets.YELLOW + Assets.PACMAN + Assets.RESET + " ");
                } else if (aChar == Assets.GHOST.charAt(0)) {
                    System.out.print(Assets.PINK + Assets.GHOST + Assets.RESET + " ");
                } else if (aChar == Assets.WALL.charAt(0)) {
                    System.out.print(Assets.BLUE + Assets.WALL + Assets.RESET + " ");
                } else if (aChar == Assets.FOOD.charAt(0)) {
                    System.out.print(Assets.ORANGE + Assets.FOOD + Assets.RESET + " ");
                } else if (aChar == 'R') {
                    System.out.print(Assets.RED + Assets.WALL + Assets.RESET + " ");
                } else if (aChar == 'G') {
                    System.out.print(Assets.GREEN + Assets.WALL + Assets.RESET + " ");
                } else if (aChar == 'B') {
                    System.out.print(Assets.BLUE + Assets.WALL + Assets.RESET + " ");
                } else if (aChar == 'Y') {
                    System.out.print(Assets.YELLOW + Assets.WALL + Assets.RESET + " ");
                } else if (aChar == ' ') {
                    System.out.print(Assets.EMPTY + " ");
                } else {
                    System.out.print(aChar + " "); // Default case
                }
            }
            System.out.println();
        }
    }


    public static boolean isValidMove(char[][] grid, int x, int y) {
        // A valid move is defined as moving into an empty square or a square containing food
        return grid[x][y] == Assets.EMPTY.charAt(0) || grid[x][y] == Assets.FOOD.charAt(0);
    }


    public static void movePacman(char[][] grid, String direction) {
        int newX = pacmanX;
        int newY = pacmanY;

        if (direction.equalsIgnoreCase("w")) {
            newX -= 1;
        } else if (direction.equalsIgnoreCase("s")) {
            newX += 1;
        } else if (direction.equalsIgnoreCase("a")) {
            newY -= 1;
        } else if (direction.equalsIgnoreCase("d")) {
            newY += 1;
        } else {
            // Invalid input
            return;
        }
        if (isValidMove(grid, newX, newY)) {
            // Update grid
            grid[pacmanX][pacmanY] = Assets.EMPTY.charAt(0); // Remove Pacman from old position
            pacmanX = newX;
            pacmanY = newY;
            grid[pacmanX][pacmanY] = Assets.PACMAN.charAt(0); // Place Pacman at new position
        }
    }

    public static void main(String[] args) {
        char[][] grid = initGrid(HEIGHT, WIDTH);
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            // Show the grid and score
            showGrid(grid);



            // Get user input
            System.out.print("Move (WASD): ");
            String input = scanner.nextLine();

            // Move Pacman
            movePacman(grid, input);

        }

//    scanner.close();
    }

}
