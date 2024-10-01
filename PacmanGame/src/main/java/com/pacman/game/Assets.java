package com.pacman.game;

import java.util.HashMap;
import java.util.Map;

public class Assets {
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
            {'R', '•', '•', '•', '•', 'G', '•', '•', '•', 'R', 'R', '•', '•', 'Y', '•', '•', 'G', ' ', 'G', '•', '•', 'B', '•'},
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
        charToColoredString.put(Assets.PACMAN_RIGHT.charAt(0), Assets.YELLOW + Assets.PACMAN_RIGHT + Assets.RESET + " ");
        charToColoredString.put(Assets.PACMAN_LEFT.charAt(0), Assets.YELLOW + Assets.PACMAN_LEFT + Assets.RESET + " ");
        charToColoredString.put(Assets.PACMAN_UP.charAt(0), Assets.YELLOW + Assets.PACMAN_UP + Assets.RESET + " ");
        charToColoredString.put(Assets.PACMAN_DOWN.charAt(0), Assets.YELLOW + Assets.PACMAN_DOWN + Assets.RESET + " ");
    }

    static String[][] welcomeScreen = {
            {"      ██████╗  █████╗  ██████╗███╗   ███╗ █████╗ ███╗   ██╗"},
            {"      ██╔══██╗██╔══██╗██╔════╝████╗ ████║██╔══██╗████╗  ██║"},
            {"      ██████╔╝███████║██║     ██╔████╔██║███████║██╔██╗ ██║"},
            {"      ██╔═══╝ ██╔══██║██║     ██║╚██╔╝██║██╔══██║██║╚██╗██║"},
            {"      ██║     ██║  ██║╚██████╗██║ ╚═╝ ██║██║  ██║██║ ╚████║"},
            {"      ╚═╝     ╚═╝  ╚═╝ ╚═════╝╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝"},
    };

    static String[][] winScreen = {
            {"    ██╗   ██╗ ██████╗ ██╗   ██╗    ██╗    ██╗██╗███╗   ██╗██╗"},
            {"    ╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║    ██║██║████╗  ██║██║"},
            {"     ╚████╔╝ ██║   ██║██║   ██║    ██║ █╗ ██║██║██╔██╗ ██║██║"},
            {"      ╚██╔╝  ██║   ██║██║   ██║    ██║███╗██║██║██║╚██╗██║╚═╝"},
            {"       ██║   ╚██████╔╝╚██████╔╝    ╚███╔███╔╝██║██║ ╚████║██╗"},
            {"       ╚═╝    ╚═════╝  ╚═════╝      ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝╚═╝"},
    };

    static String[][] loseScreen = {
            {"██╗   ██╗ ██████╗ ██╗   ██╗    ██╗      ██████╗ ███████╗███████╗██╗"},
            {"╚██╗ ██╔╝██╔═══██╗██║   ██║    ██║     ██╔═══██╗██╔════╝██╔════╝██║"},
            {" ╚████╔╝ ██║   ██║██║   ██║    ██║     ██║   ██║███████╗█████╗  ██║"},
            {"  ╚██╔╝  ██║   ██║██║   ██║    ██║     ██║   ██║╚════██║██╔══╝  ╚═╝"},
            {"   ██║   ╚██████╔╝╚██████╔╝    ███████╗╚██████╔╝███████║███████╗██╗"},
            {"   ╚═╝    ╚═════╝  ╚═════╝     ╚══════╝ ╚═════╝ ╚══════╝╚══════╝╚═╝"},
    };

}
