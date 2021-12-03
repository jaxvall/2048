import javax.swing.JFrame;
import java.awt.*;

public class MainWindow extends JFrame {

    private static int windowSize = 640;

    public static void main(String[] args) {
        new MainWindow();
    }

    private MainWindow() {
        setupComps();

    }

    private void setupComps() {

        Board board = new Board(windowSize);

        this.setLayout(new BorderLayout(5, 5));

        this.setTitle("Welcome to 2048!");

        this.add(board, BorderLayout.CENTER);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        this.setResizable(false);
        this.setSize(windowSize, windowSize);

    }

}
