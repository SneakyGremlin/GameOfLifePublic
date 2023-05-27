package relearningSwing;

import javax.swing.*;

public class MainWindow {
    // >>> JFrame is the top level container

    // composiiton/association
    JFrame jframe;

    public MainWindow() {
        jframe = new JFrame();
        jframe.setTitle("Hello");
        jframe.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jframe.setSize(800,500);
        jframe.setLocationRelativeTo(null);
    }
    public void show() {
        jframe.setVisible(true);

    }
}
