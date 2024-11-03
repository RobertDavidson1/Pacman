package com.pacman.game;


public class Logic {
    public static boolean runGame(int difficulty, Grid grid, Pacman pacman, Ghost[] ghosts) {
        boolean gameIsRunning = true;

        while (gameIsRunning) {
            // Clear the grid and display the current state
            UI.clearGrid();
            grid.showGrid(ghosts); // Updated to pass ghosts array

            // Display game information such as food remaining and difficulty level
            UI.displayGameInfo(grid, difficulty, Game.getCurrentRound(), pacman); // Updated to pass pacman instance

            // Get user input for Pacman's movement
            String input = getUserInput();

            // Move Pacman and the ghost based on the input
            pacman.move(input);
            // Move all ghosts
            for (Ghost ghost : ghosts) {
                ghost.move(pacman.getX(), pacman.getY(), grid);
            }

            // Check for ghost collision (game over)
            if (!pacman.isInvincible() && checkGhostCollision(pacman, ghosts)) {
                UI.displayLoseScreen();
                gameIsRunning = false;
                return false; // Player lost
            }
            
            // Check if round is complete (all food eaten)
            if (grid.getFoodRemaining() == 0) {
                if (Game.getCurrentRound() == Game.MAX_ROUNDS) {
                    // Only show victory screen if player completed final round
                    UI.displayVictoryScreen();
                } else {
                    // Show round completion message for rounds 1 and 2
                    UI.displayRoundComplete(Game.getCurrentRound());
                }
                gameIsRunning = false;
                return true; // Round completed successfully
            }
        }
        return false;
    }

    private static boolean checkGhostCollision(Pacman pacman, Ghost[] ghosts) {
        for (Ghost ghost : ghosts) {
            if (pacman.getX() == ghost.getX() && pacman.getY() == ghost.getY()) {
                return true;
            }
        }
        return false;
    }

    private static String getUserInput() {
        return Game.scanner.nextLine().trim().toUpperCase();
    }
}
