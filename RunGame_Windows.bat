@echo off 

:: Set the code page to UTF-8
chcp 65001 > nul

set SRC_DIR=src\main\java\com\pacman\game
set OUT_DIR=out\production\PacmanGame

javac -d %OUT_DIR% %SRC_DIR%\*.java
java -cp %OUT_DIR% com.pacman.game.Game

