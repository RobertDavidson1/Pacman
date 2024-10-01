#!/bin/bash

# Ensure UTF-8 is being used
export LANG=en_US.UTF-8
export LC_ALL=en_US.UTF-8

SRC_DIR="src/main/java/com/pacman/game"
OUT_DIR="out/production/PacmanGame"

javac -d "$OUT_DIR" "$SRC_DIR"/*.java
java -cp "$OUT_DIR" com.pacman.game.Game

