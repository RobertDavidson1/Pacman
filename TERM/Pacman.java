package TERM;

public class Pacman {
    int HEIGHT = 15;
    int WIDTH = 35;

    public static class Assets{
        // Colours    
        public static final String RESET = "\033[0m";
        public static final String PINK = "\033[38;2;234;130;229m";
        public static final String ORANGE = "\033[38;2;219;133;28m"; 
        public static final String YELLOW = "\033[38;2;253;255;0m"; 
        public static final String BLUE = "\033[38;2;33;33;222m"; 
        
        // Characters
        public static final String PACMAN = "⬤";
        public static final String GHOST = "G";
        public static final String WALL = "▀";
        public static final String FOOD = "•";
        public static final String EMPTY = " ";
    
        public static final char[][] art = {
            {'▀', '▀', '▀', '•', '▀', '▀', '▀', '•', '▀', '▀', '•', '•', '▀', '▀', '•', '•', '•', '▀', '•', '•', '▀', '▀', '•'},
            {'▀', '•', '•', '•', '•', '▀', '•', '•', '•', '•', '▀', '•', '•', '▀', '•', '•', '▀', ' ', '▀', '•', '•', '▀', '•'},
            {'▀', '•', '•', '•', '•', '▀', '•', '•', '•', '•', '▀', '•', '•', '▀', '•', '•', '▀', ' ', '▀', '•', '•', '▀', '•'},
            {'▀', '•', '•', '•', '•', '▀', '•', '•', '▀', '•', '•', '•', '•', '▀', '•', '•', '▀', ' ', '▀', '•', '•', '▀', '•'},
            {'▀', '▀', '▀', '•', '•', '▀', '•', '•', '▀', '▀', '▀', '•', '▀', '▀', '▀', '•', '•', '▀', '•', '•', '▀', '▀', '▀'},
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
            for (int j = 0; j < Assets.art[i].length; j++) {
                grid[startX+i][startY+j] = Assets.art[i][j];
            }

        }

        return grid;

    }

    public static void showGrid(char[][] grid) {
        int food_count = 0;

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
                    food_count += 1;
                } else if (aChar == Assets.EMPTY.charAt(0)) {
                    System.out.print(Assets.EMPTY + Assets.RESET + " ");
                }

            }
            System.out.println();
        }
    }




    public static void main(String[] args) {
        char[][] grid = initGrid(10, 35);
        showGrid(grid);
    }
}

