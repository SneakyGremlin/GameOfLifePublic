package relearningSwing;

import javax.swing.*;
import java.awt.*;

public class JPanelOne {
    //Jframe>JPanel
    // >>> JFrames default layout manager is border layout
    // >>> JPanel's flow
    // Jpanel has its own layout manager

    JPanel panel;

    public JPanelOne() {
        panel = new JPanel();
        // panel.setLayout(new BorderLayout(5, 10)); x and y spacing
        panel.setLayout(new FlowLayout(FlowLayout.LEFT)); // align,, align,spacing,spacing Flowlayout.
    }

}
