## Pacman Terminal Game

#### Note:

-   The game works best when ran through a system terminal
    -   Allows the game to clear the screen between moves

### To run:

#### Linux:

-   Clone the repository
-   Run `./RunGame_Linux.sh`
-   Or:
    -   Compile: `javac -d out/production/PacmanGame src/com/pacman/game/*.java`
    -   Run: `java -Dfile.encoding=UTF-8 -cp out/production/PacmanGame com.pacman.game.Game`

#### Windows:

-   Clone the repository
-   Run `RunGame_Windows.bat`
-   Or:
    -   Set encoding (CMD Prompt) : `chcp 65001'
    -   Compile: `javac -d out\production\PacmanGame .\src\main\java\com\pacman\game\*.java`
    -   Run: `java -cp out\production\PacmanGame com.pacman.game.Game'

#### Expected Features:
- A minimum of 10 x 10 grid ✔
- Obstacle Detection ✔
- WASD and q are working ✔
- Scoring system ✔
- Ghost AI follows pacman ✔
- Multiple levels ✔
- Power up (Invincible) ✔
- Power up has timer ✔
- Powerup status indicator ✔
- After each game over or when advancing to a new level, prompt the player to continue playing or quit the game.  ✔
    - Final score should be display  