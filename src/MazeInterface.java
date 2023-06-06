import javax.swing.JFrame;

public class MazeInterface extends JFrame{

    public static void main(String[] args) {
        MazeInterface window = new MazeInterface();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MazePanel panel = new MazePanel();
        window.add(panel);
        window.setVisible(true);

        window.pack();
    }
}
