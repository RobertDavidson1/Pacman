package com.pacman.game;

import java.util.Scanner;


public class Game {
    // Scanner object to read user input
    public static final Scanner scanner = new Scanner(System.in);

    // Variable to store the difficulty level
    private static int difficulty;

    // Flag to determine if the game should be played again
    public static boolean playAgain = true;

    // Main game loop
    public static void gameLoop() {
        while (true) {
            UI.clearGrid();

            // Display the welcome screen and get the difficulty level
            difficulty = UI.displayWelcomeScreen();

            // Ensure the difficulty level is between 1 and 3
            if (difficulty >= 1 && difficulty <= 3) {
                break;
            }
            System.out.println("Please select a difficulty level between 1 and 3.");
        }

        // Initialize the game grid and characters
        int gridHeight = 11;
        int gridWidth = 35;
        Grid grid = new Grid(gridHeight, gridWidth);
        Pacman pacman = new Pacman(1, 1, grid);
        Ghost ghost = new Ghost(gridHeight - 2, gridWidth - 2, grid, pacman, difficulty);

        // Run the game logic
        Logic.runGame(difficulty, grid, pacman, ghost);
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
