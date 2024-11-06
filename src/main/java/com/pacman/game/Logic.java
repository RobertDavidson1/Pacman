package com.pacman.game;


public class Logic {
    public static boolean runGame(int difficulty, Grid grid, Pacman pacman, Ghost[] ghosts) {
        boolean gameIsRunning = true;

        while (gameIsRunning) {
            UI.clearGrid();
            
            // First check for collisions
            checkGhostCollisions(pacman, ghosts, grid);
            
            // Then show the updated grid
            grid.showGrid(ghosts);
            UI.displayGameInfo(grid, difficulty, Game.getCurrentRound(), pacman);

            String input = getUserInput();
            
            // Check for quit command
            if (input.equals("Q")) {
                Game.playAgain = false;
                return false;
            }
            
            pacman.move(input);

            // Move remaining ghosts
            for (Ghost ghost : ghosts) {
                if (ghost != null) {
                    ghost.move(pacman.getX(), pacman.getY(), grid);
                    checkGhostCollisions(pacman, ghosts, grid);
                }
            }

            if (grid.getFoodRemaining() == 0) {
                if (Game.getCurrentRound() == Game.MAX_ROUNDS) {
                    UI.displayVictoryScreen();
                } else {
                    UI.displayRoundComplete(Game.getCurrentRound());
                }
                gameIsRunning = false;
                return true;
            }
        }
        return false;
    }

    private static String getUserInput() {
        return Game.scanner.nextLine().trim().toUpperCase();
    }

    private static void checkGhostCollisions(Pacman pacman, Ghost[] ghosts, Grid grid) {
        for (int i = 0; i < ghosts.length; i++) {
            if (ghosts[i] != null && pacman.getX() == ghosts[i].getX() && pacman.getY() == ghosts[i].getY()) {
                if (pacman.isInvincible()) {
                    // Get ghost's current position and what was under it
                    int ghostX = ghosts[i].getX();
                    int ghostY = ghosts[i].getY();
                    
                    // First restore what was under the ghost
                    grid.updateCell(ghostX, ghostY, Assets.EMPTY.charAt(0));
                    
                    // Then place Pacman there
                    grid.updateCell(ghostX, ghostY, Pacman.getCurrentSprite().charAt(0));
                    
                    // Remove ghost from array
                    ghosts[i] = null;
                    
                } else {
                    UI.displayLoseScreen();
                    Game.playAgain = false;
                    break;
                }
            }
        }
    }
}
