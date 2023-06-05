package graphics;

import exceptions.InvariantBroken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Primary contained class for a 2D array for game of life. Follows all rules of the classical version. For rules refer
 * to README file.
 *
 * Attributes:
 *             @see cells.CellTwoDimension
 *             square: is of type JPanel; is the actual cell on the realised matrix (observable).
 *             dimension: the cells are square in nature. This determines the size of the cells (the squares).
 *
 * Invariant: Coordinates are always positive or zero.
 */

public class CellTwoDimensionGraphic extends cells.CellTwoDimension {

    private JPanel square;
    public static int DIMENSION = 10;

    /** Constructor  intended for extensibility: i.e. if one wishes to preload a configuration.
     *
     * @param initial sets the initial state of the cell (and the colour of rectangle).
     * @param x is the row location.
     * @param y is the column location.
     * @throws InvariantBroken when either x or y < 0;
     */

    public CellTwoDimensionGraphic(boolean initial, int x, int y) throws InvariantBroken {
        super(initial, x, y);
        square = new JPanel();
        square.setSize(new Dimension(DIMENSION, DIMENSION));
        if (!initial) {
            square.setBackground(Color.white);
        } else {
            square.setBackground(Color.black);
        }
        square.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // TODO
                //super.mouseClicked(e);
                toggle();
            }
        });
    }


    /** Constructor; default behaviour.
     *
     * @param x is the row location.
     * @param y is the column location.
     * @throws InvariantBroken when either x or y < 0;
     *
     * rect's colour is set to white and state is set to false.
     */

    public CellTwoDimensionGraphic(int x, int y) throws InvariantBroken {
        super(x, y);
        square = new JPanel();
        square.setSize(new Dimension(DIMENSION, DIMENSION));
        square.setBackground(Color.white);
        square.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) { // TODO
                //super.mouseClicked(e);
                toggle();
            }
        });
    }


    /**
     *
     * Entails minor coupling with class GUI (refer to implementation section of README)
     * and (former) deliberate moderate coupling with the containing class ContainerTwoDimensionGraphic.
     *
     * For the former the GUI has a JPanel which effectively maps all squares to sections of a grid. It therefore holds a reference to
     * the object though it never does modify it (unless a suitable action is taken e.g. mouse click on a rect).
     *
     * For the latter, since the container class then had access to the JPanel associated with the cell it has free rein to access all methods available
     * to the class JLabel.
     * @see ContainerTwoDimensionGraphic's private method, condCheckerAndUpdater's, commented out code.
     * To be succinct: in all instances where getSquare was invoked in ContainerTwoDimensionGraphic, set/getState should have been invoked and then the method update() should
     * have been invoked.
     * n.b. above documentation is archival, this issue has been resolved.
     *
     * @return the JLabel associated with this class' object instance.
     */
    public JPanel getSquare() {
        return square;
    }

    /**
     * Updates the colour of the square based on the state. EXPLICITLY must be invoked when state is updated.
     */

    public void update() {
        if (state) {
            square.setBackground(Color.black);
        } else {
            square.setBackground(Color.white);
        }
    }

    /**
     * toggle is the actionPerformed for the anonymous MouseAdapter class for the square. It toggles the state/colour.
     */

    public void toggle() {
        if (state) {
            square.setBackground(Color.white);
            state = false;
        } else {
            square.setBackground(Color.black);
            state = true;
        }
    }

}

// >>> visibility is inherited from the container.


//        if (rect.getBackground().equals(Color.white)) {
//            rect.setBackground(Color.black);
//            this.state = true;
//        } else {
//            rect.setBackground(Color.white);
//            this.state = false;
//        }