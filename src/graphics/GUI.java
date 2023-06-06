package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * The class responsible for the Graphics User Interface.
 *
 * Attributes:
 * There are three JFrames:
 *                  - frameForParameters: this is used to set the dimensions of the grid
 *                  - frameForInitialisation: this is used to set the initial state of the board (via interacting with the grid)
 *                  - mainFrame: this is the primary frame where all the "action" takes place i.e. where you will see Conway's Game of life... come to life!
 * There are two JPanels (which both reside inside mainFrame):
 *                  - westForMatrix: this panel is responsible for the grid of cells; its Layout Manager is GridLayout and its rows and columns are determined by the user input.
 *                  - eastForButtons: this is where all the buttons (see below) reside.
 * There are five JButtons:
 *                  - next: this button's action is to call update() of the container variable. It moves ahead a generation.
 *                  - clear: this button's action is to clear the game board.
 *                  - toggleTimer: toggles the timer.
 *                  - quickenTimer: increases the timer's speed up to a maximum.
 *                  - slowTimer: decreases the timer's speed up to a minimum.
 * There is one Timer:
 *                  - timer: calls container's update() at intervals.
 *  - container is of type ContainerTwoDimensionGraphic i.e. the GUI stores the container (composition aggregation)
 *  - rows: no of rows of the grid.
 *  - columns: no of columns of the grid.
 *          ^^^ rows and columns are for convenience: container's getters could satisfy requirements easily, but a primary window container
 *          should know the attributes of its contained Panels.
 *  - DEFAULT_DIMENSION: dimensions of the grid, should the user input erroneous data during initialising.
 */

public class GUI {

    JFrame frameForParameters;
    JFrame frameForInitialisation;
    JFrame mainFrame;
    JPanel westForMatrix;
    JPanel eastForButtons;
    JButton next;
    JButton buttonForClear;
    JButton toggleTimer;
    JButton quickenTimer;
    JButton slowTimer;
    Timer timer;


    ContainerTwoDimensionGraphic container;
    int rows;
    int columns;


    private static int DEFAULT_DIMENSION = 50;

    /** Constructor
     *
     * Begins the EventDispatchThread (well invokes the method that does the aforementioned).
     */
    public GUI() {
        initialise();
    }

    /**
     * This produces and populates the frameForParameters i.e. the frame responsible for discerning how many rows and columns
     * the user wants.
     * Implements local JPanels, JLabels, JTextFields, JButtons
     * Implements error handling for invalid input by reverting to default values (50, 50).
     *
     * Upon termination of this method, the container should have parameters to begin construction.
     *
     * Method is terminated by the button press which disposes of the frameForParameters and invokes method initialisingMatrix().
     */

    private void initialise() {
        frameForParameters = new JFrame("Conrad's Game of Life");
        frameForParameters.setLocationRelativeTo(null);

        JPanel upper = new JPanel();
        JPanel lower = new JPanel();

        JTextField xDimensionEntry = new JTextField(10);
        JTextField yDimensionEntry = new JTextField(10);
        JLabel xDim = new JLabel("Enter the number of horizontal cells: (<=125)");
        JLabel yDim = new JLabel("Enter the number of vertical cells:  (<=125)    ");

        upper.add(xDimensionEntry, FlowLayout.LEFT); // !!!order of addition is reversed
        upper.add(xDim, FlowLayout.LEFT);
        lower.add(yDimensionEntry, FlowLayout.LEFT);
        lower.add(yDim, FlowLayout.LEFT);

        frameForParameters.add(upper, BorderLayout.NORTH);
        frameForParameters.add(lower, BorderLayout.CENTER);

        JButton buttonForNextStep = new JButton();
        buttonForNextStep.setText("Please Click Me to Proceed.");
        buttonForNextStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    try {
                        rows = Integer.valueOf(yDimensionEntry.getText().strip()); // !!!
                        columns = Integer.valueOf(xDimensionEntry.getText().strip());
                    } catch (NumberFormatException exception) {
                        JOptionPane.showMessageDialog(frameForParameters, "You entered invalid dimensions. Reverting to Default Dimensions"); // !!!
                        rows = DEFAULT_DIMENSION;
                        columns = DEFAULT_DIMENSION;
                    }
                //frameForParameters.dispatchEvent(new WindowEvent(frameForParameters, WindowEvent.WINDOW_CLOSED)); // !!! >>>
                frameForParameters.dispose();
                initialisingMatrix();
            }
        });

        frameForParameters.add(buttonForNextStep, BorderLayout.SOUTH);

        frameForParameters.pack();
        frameForParameters.setResizable(false);
        frameForParameters.setVisible(true);
        frameForParameters.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * REQUIRES: invoked from initialise after proper ActionEvent.
     *
     * Utilises JPanels, JButton.
     *
     * Constructs the container class and creates a dependency between each empty location in the grid with the square member
     * variable of the container's elements. This is much better illustrated via the UML Diagram. Note this results in more associations
     * with JFrame but only dependencies on CellTwoDimensionGraphic (the association with ContainerTwoDimensionGraphic is still present).
     *
     * After the above one has an interactive grid: it may be populated as seen fit.
     *
     * Clicking the appropriate button dispatches the next method createMainWindow()
     *
     * n.b. please note the implementation of the action performed for buttonForClear here and in east(). Then refer to README's "The Variance in clear methods".
     *
     */
    private void initialisingMatrix() {
        frameForInitialisation = new JFrame("Initial World State");
        frameForInitialisation.setLayout(new BorderLayout(5, 5));
        JPanel panelForTheInteractableGrid = new JPanel(new GridLayout(rows, columns, 3, 3));
        panelForTheInteractableGrid.setBorder(BorderFactory.createEmptyBorder());

        container = new ContainerTwoDimensionGraphic(rows, columns);
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++) {
                panelForTheInteractableGrid.add(container.getCell(c, r).getSquare());
            }
        };

        JPanel forButton = new JPanel(new BorderLayout());
        JButton buttonForNext = new JButton("Click me to proceed");
        buttonForNext.setSize(30, 8);
        buttonForNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameForInitialisation.dispose();

                createMainWindow();
            }
        });
        forButton.add(buttonForNext, BorderLayout.SOUTH);

        // ---
        JButton buttonForClear = new JButton("Click me to clear board.");
        buttonForClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.clearAll();
            }
        });
        forButton.add(buttonForClear, BorderLayout.NORTH);

        frameForInitialisation.add(forButton, BorderLayout.EAST);

        frameForInitialisation.add(panelForTheInteractableGrid);
        frameForInitialisation.pack();
        frameForInitialisation.setResizable(false);
        frameForInitialisation.setLocationRelativeTo(null);
        frameForInitialisation.setVisible(true);
        frameForInitialisation.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * This constructs the main JFrame whereupon Conway's game of life is beheld.
     *
     * "contains" the attributes westForMatrix and leftForButton
     *
     *  method invocations west and east respectively construct these frames whereafter they are added herein.
     *
     */
    private void createMainWindow() {
        mainFrame = new JFrame("Conrad's Game Of Life");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // mainFrame.setLocationRelativeTo(null);
        westForMatrix = new JPanel(new GridLayout(rows, columns, 3, 3));
        eastForButtons = new JPanel(new GridLayout(10, 1, 3, 3));

        west();
        east();

        mainFrame.add(westForMatrix, BorderLayout.WEST);
        mainFrame.add(eastForButtons, BorderLayout.EAST);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    /**
     * I include the remove invocation on the top is for better understanding.
     *
     * I concede this particular implementation is not ideal, however it builds on how update() works inside ContainerTwoDimensionGraphic.
     * A new container is created with its own Cells and thus JPanels. and the GUI needs access to them.
     *
     * This is in line with minimal computations on the graphics end.
     *
     * I am aware this solution has 0(n) space complexity.
     */

    private void west() {
        mainFrame.remove(westForMatrix);
        westForMatrix = new JPanel(new GridLayout(rows, columns, 3, 3));
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++) {
                westForMatrix.add(container.getCell(c, r).getSquare());
            }
        }
        mainFrame.add(westForMatrix, BorderLayout.WEST);
        mainFrame.pack();
    }

    /**
     * Initialises all the buttons and the frameForButtons. Note the concerted calls to update() for container and west().
     * container performs the algorithmic computations necessary to advance to the next generation; west() displays the result.
     */
    private void east() {
        next = new JButton("Click to proceed.");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.update();
                west();
            }
        });
        buttonForClear = new JButton("Click to clear board");
        buttonForClear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // container = new ContainerTwoDimensionGraphic(columns, rows);
                container.clearAll();
                west();
                timer.stop();
            }
        });
        timer = new Timer(500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.update();
                west();
            }
        });
        toggleTimer = new JButton("Toggle Timer");
        toggleTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(timer.isRunning()) {
                    timer.stop();
                } else {
                    timer.start();
                }
            }
        });
        quickenTimer = new JButton(">>>");
        quickenTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.getDelay() > 500) {
                    timer.setDelay(timer.getDelay() - 500);
                }
            }
        });
        slowTimer = new JButton("<<<");
        slowTimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timer.getDelay() <= 10000) {
                    timer.setDelay(timer.getDelay() + 500);
                }
            }
        });
        // these could be variable hence the repetition.
        quickenTimer.setSize(new Dimension(30, 10));
        slowTimer.setSize(new Dimension(30, 10));
        toggleTimer.setSize(new Dimension(30, 10));
        next.setSize(new Dimension(30, 10));
        buttonForClear.setSize(new Dimension(30, 10));
        eastForButtons.add(toggleTimer);
        eastForButtons.add(quickenTimer);
        eastForButtons.add(slowTimer);
        eastForButtons.add(next);
        eastForButtons.add(buttonForClear);
    }
}