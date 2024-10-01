package com.pacman.game;


public class Logic {
    public static void runGame(int difficulty, Grid grid, Pacman pacman, Ghost ghost) {
        boolean gameIsRunning = true;

        while (gameIsRunning) {
            // Clear the grid and display the current state
            UI.clearGrid();
            grid.showGrid();

            // Display game information such as food remaining and difficulty level
            UI.displayGameInfo(grid, difficulty);

            // Get user input for Pacman's movement
            String input = getUserInput();

            // Move Pacman and the ghost based on the input
            pacman.move(input);
            ghost.move(pacman.getX(), pacman.getY(), grid);

            // Check if the game is over (either win or lose)
            if (isGameOver(pacman, ghost, grid)) {
                gameIsRunning = false;
            }
        }
        // Prompt the user to play again and update the playAgain flag
        Game.playAgain = UI.promptPlayAgain();
    }



    private static String getUserInput() {
        return Game.scanner.nextLine().trim().toUpperCase();
    }

    private static boolean isGameOver(Pacman pacman, Ghost ghost, Grid grid) {
        // Check if Pacman has collided with the ghost
        if (pacman.getX() == ghost.getX() && pacman.getY() == ghost.getY()) {
            UI.displayLoseScreen();
            return true;
        }

        // Check if all food has been consumed
        if (grid.getFoodRemaining() == 0) {
            UI.displayWinScreen();
            return true;
        }
        
        return false;
        
    }


}