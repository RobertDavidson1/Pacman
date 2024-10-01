#!/bin/bash

SRC_DIR="src/main/java/com/pacman/game"
OUT_DIR="out/production/PacmanGame"

javac -d "$OUT_DIR" "$SRC_DIR"/*.java
java -cp "$OUT_DIR" com.pacman.game.Game

