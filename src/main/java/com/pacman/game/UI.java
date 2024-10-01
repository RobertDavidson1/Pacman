package com.pacman.game;

import java.io.IOException;
import java.util.Scanner;

public class UI {
    private static final Scanner scanner = new Scanner(System.in);

    // Method to display the welcome screen and prompt for difficulty level
    public static int displayWelcomeScreen() {
        clearGrid();
        for (String[] row : Assets.welcomeScreen) {
            for (String cell : row) {
                System.out.println(Assets.ORANGE + cell);
            }
        }
        System.out.println("                Enter a Difficulty Level (1, 2, 3): ");
        System.out.print("                                ⨠  ");
        int difficulty = -1;
        try {
            difficulty = scanner.nextInt(); // Read the difficulty level from user input
            scanner.nextLine(); // Consume the newline character
        } catch (Exception e) {
            scanner.nextLine(); // Consume the newline character
            return -1; // Return -1 if an exception occurs
        }
        System.out.print(Assets.RESET);
        return difficulty; // Return the chosen difficulty level
    }

    public static boolean promptPlayAgain() {
        while (true) {
            System.out.print("                        Play Again? (Y/N): ");
            String option = scanner.nextLine().trim();

            if (option.equalsIgnoreCase("Y")) {
                return true;
            } else if (option.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("              Invalid input! Please enter 'Y' or 'N'.");
            }
        }
    }

    public static void displayWinScreen() {
        // Flashing effect for the win screen
        for (int i = 0; i < 11; i++) {
            clearGrid();
            for (String[] row : Assets.winScreen) {
                for (String cell : row) {
                    if (i % 2 == 0) {
                        System.out.println(Assets.GREEN + cell);
                    } else {
                        System.out.println(Assets.RESET + cell);
                    }
                }
            }
            sleep();
        }


        boolean playAgain = promptPlayAgain();
        if (playAgain) {
            Game.gameLoop(); // Restart the game loop
        } else {
            System.out.println("                       Thank you for playing!");
            System.exit(0); // Exit the program
        }
    }

    public static void displayLoseScreen() {
        for (int i = 0; i < 11; i++) {
            clearGrid();
            for (String[] row : Assets.loseScreen) {
                for (String cell : row) {
                    if (i % 2 == 0) {
                        System.out.println(Assets.RED + cell);
                    } else {
                        System.out.println(Assets.RESET + cell);
                    }
                }
            }
            sleep();
        }

        boolean playAgain = promptPlayAgain();
        if (playAgain) {
            Game.gameLoop(); // Restart the game loop
        } else {
            System.out.println("                       Thank you for playing!");
            System.exit(0); // Exit the program
        }
    }

    static void clearGrid() {
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

    static void sleep() {
        try {
            Thread.sleep(350);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    // Display food remaining and difficulty
    public static void displayGameInfo(Grid grid, int difficulty) {

        System.out.printf(
                "              Food Remaining: %03d  |||  Difficulty: %d%n",
                grid.getFoodRemaining(), difficulty
        );
        System.out.print("                     Move (WASD) ⨠  ");
    }
}



