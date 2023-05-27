package relearningSwing;

import javax.swing.*;

public class JFrameOne extends JFrame {

    public JFrameOne() {
        initialise();
    }

    public void initialise() {
        setTitle("JFrsame 1");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 200);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
}
