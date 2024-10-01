#!/bin/bash

# Set the directories
SRC_DIR="src/main/java/com/pacman/game"
OUT_DIR="out/production/PacmanGame"

# Step 1: Compile the Java files
echo "Compiling Java files..."
javac -d "$OUT_DIR" "$SRC_DIR"/*.java
java -cp "$OUT_DIR" com.pacman.game.Game

