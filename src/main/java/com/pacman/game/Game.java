package com.pacman.game;

import java.util.Scanner;


public class Game {
    // Scanner object to read user input
    public static final Scanner scanner = new Scanner(System.in);

    // Variable to store the difficulty level
    private static int difficulty;

    // Flag to determine if the game should be played again
    public static boolean playAgain = true;

    // Round counter
    public static int currentRound = 1;
    public static final int MAX_ROUNDS = 3;

    // Add getter method for currentRound
    public static int getCurrentRound() {
        return currentRound;
    }

    private static Ghost[] ghosts;

    public static Ghost[] getGhosts() {
        return ghosts;
    }

    // Main game loop
    public static void gameLoop() {
        while (currentRound <= MAX_ROUNDS) {
            UI.clearGrid();
            
            // Only show welcome screen on first round
            if (currentRound == 1) {
                difficulty = UI.displayWelcomeScreen();
                while (difficulty < 1 || difficulty > 3) {
                    System.out.println("Please select a difficulty level between 1 and 3.");
                    difficulty = UI.displayWelcomeScreen();
                }
            } else {
                UI.displayRoundScreen(currentRound);
            }

            // Initialize the game grid and characters
            int gridHeight = 11;
            int gridWidth = 35;
            Grid grid = new Grid(gridHeight, gridWidth);
            Pacman pacman = new Pacman(1, 1, grid);

            // Create ghosts based on current round
            ghosts = new Ghost[currentRound];
            switch (currentRound) {
                case 1 -> ghosts[0] = new Ghost(gridHeight - 2, gridWidth - 2, grid, pacman, difficulty, "Blinky");
                case 2 -> {
                    ghosts[0] = new Ghost(gridHeight - 2, gridWidth - 2, grid, pacman, difficulty, "Blinky");
                    ghosts[1] = new Ghost(gridHeight - 2, 1, grid, pacman, difficulty, "Pinky");
                }
                case 3 -> {
                    ghosts[0] = new Ghost(gridHeight - 2, gridWidth - 2, grid, pacman, difficulty, "Blinky");
                    ghosts[1] = new Ghost(gridHeight - 2, 1, grid, pacman, difficulty, "Pinky");
                    ghosts[2] = new Ghost(1, gridWidth - 2, grid, pacman, difficulty, "Inky");
                }
            }

            // Run the game logic with ghosts
            boolean roundCompleted = Logic.runGame(difficulty, grid, pacman, ghosts);
            
            if (!roundCompleted) {
                // Player lost
                currentRound = 1; // Reset round counter
                if (!playAgain) break;
            } else {
                // Player completed the round
                if (currentRound == MAX_ROUNDS) {
                    UI.displayVictoryScreen(); // Show final victory
                    currentRound = 1;
                    if (!playAgain) break;
                } else {
                    currentRound++;
                }
            }
        }
    }

    // Main method to start the game
    public static void main(String[] args) {
        while (playAgain) {
            gameLoop();
        }
        // Close the Scanner to prevent resource leaks
        scanner.close();
        System.out.println("Thank you for playing!");
        System.out.print(Assets.RESET);
    }
}
