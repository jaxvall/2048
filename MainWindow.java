import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.*;

public class MainWindow extends JFrame {

    static int windowSize = 600;

    public static void main(String[] args) {
        MainWindow game = new MainWindow();
    }

    public MainWindow() {
        setupComps();

    }

    public void setupComps() {

        Board board = new Board(windowSize);

        this.setLayout(new BorderLayout(5, 5));

        this.setTitle("Welcome to 2048!");

        this.add(board, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.setResizable(false);
        this.setSize(windowSize, windowSize);

    }

    public static void startGame() {

    }

}
