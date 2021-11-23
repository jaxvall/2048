import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainGame extends JPanel {

    static JFrame window = new JFrame("Welcome to 2048!");

    public static void main(String[] args) {

        MainGame game = new MainGame();

        window.setVisible(true);
        window.getContentPane().add(game);
        window.setResizable(false);
        window.setSize(500, 500);
        window.add(new JButton());
    }

}
