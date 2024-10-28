#!/bin/bash

# Ensure UTF-8 is being used
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8

SRC_DIR="src/main/java/com/pacman/game"
OUT_DIR="out/production/PacmanGame"

# Create output directory if it doesn't exist
mkdir -p "$OUT_DIR"

# Compile and capture any errors
COMPILE_OUTPUT=$(javac -d "$OUT_DIR" "$SRC_DIR"/*.java 2>&1)
COMPILE_STATUS=$?

# Check if compilation was successful
if [ $COMPILE_STATUS -ne 0 ]; then
    echo "Compilation failed with the following errors:"
    echo "$COMPILE_OUTPUT"
    exit 1
fi

# Run the game if compilation was successful
java -cp "$OUT_DIR" com.pacman.game.Game
