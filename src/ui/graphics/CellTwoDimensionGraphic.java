package ui.graphics;

import exceptions.InvariantBroken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CellTwoDimensionGraphic extends classes.cells.CellTwoDimension {

    private JPanel rect;
    private int DIMENSION = 10;

    public CellTwoDimensionGraphic(boolean initial, int x, int y) throws InvariantBroken {
        super(initial, x, y);
        rect = new JPanel();
        rect.setSize(new Dimension(DIMENSION, DIMENSION));
        rect.setBackground(Color.white);
        rect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                toggle();
            }
        });
        rect.setVisible(true);
    }

    public JPanel getRect() {
        return rect;
    }

    public void toggle() {
//        if (rect.getBackground().equals(Color.white)) {
//            rect.setBackground(Color.black);
//            this.state = true;
//        } else {
//            rect.setBackground(Color.white);
//            this.state = false;
//        }
        if (state) {
            rect.setBackground(Color.white);
            state = false;
        } else {
            rect.setBackground(Color.black);
            state = true;
        }
    }
}
