package UI;


import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;

public class PacmanGame extends JFrame {

    public static void main(String[] args) {
        new PacmanGame();
    }

    public PacmanGame() {
        setTitle("Pac-Man Game");
        setSize(620, 420);
        setResizable(false);
        setLocationRelativeTo(null);

        // Add the game panel
        add(new GamePanel());

        // Handle window closing
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });

        setVisible(true);
    }
}
