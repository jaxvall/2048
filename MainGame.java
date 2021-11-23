import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MainGame extends JPanel {

    Board board = new Board();

    public static void main(String[] args) {
        startGame();
    }

    public MainGame() {

    }

    public static void startGame() {

        JFrame window = new JFrame("Welcome to 2048!");

        MainGame game = new MainGame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setVisible(true);
        window.getContentPane().add(game);
        window.setResizable(false);
        window.setSize(500, 500);

    }

    public void paint(Graphics g) {
        super.paint(g);
        board.createTiles(g);
    }

}
