package ui.graphics;

import container.ContainerTwoDimension;
import exceptions.InvariantBroken;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class GUI {

    JFrame frameForParameters;
    JFrame frameForInitialisation;
    JFrame mainFrame;
    JPanel westForMatrix;
    JPanel eastForButton;
    JButton next;
    JButton clear;
    JButton toggleTimer;
    JButton quickenTimer;
    JButton slowTimer;
    Timer timer;


    ContainerTwoDimensionGraphic container;
    int rows;
    int columns;
    int cellDimension = 15;

    public GUI() {
        initialise();
    }

    public void initialise() {
        frameForParameters = new JFrame("Conrad's Game of Life");
        frameForParameters.setLocationRelativeTo(null);
        //frameForParameters.setSize(new Dimension(400, 100)); // !!!

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
                        rows = 50;
                        columns = 50;
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

    private void initialisingMatrix() {
        frameForInitialisation = new JFrame("Initial World State");
        frameForInitialisation.setLayout(new BorderLayout(5, 5));
        JPanel panelForTheInteractableGrid = new JPanel(new GridLayout(rows, columns, 3, 3));
        panelForTheInteractableGrid.setBorder(BorderFactory.createEmptyBorder());

        container = new ContainerTwoDimensionGraphic(rows, columns);
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++) {
                panelForTheInteractableGrid.add(container.getCell(c, r).getRect());
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
        frameForInitialisation.add(forButton, BorderLayout.EAST);

        frameForInitialisation.add(panelForTheInteractableGrid);
        frameForInitialisation.pack();
        frameForInitialisation.setResizable(false);
        frameForInitialisation.setLocationRelativeTo(null);
        frameForInitialisation.setVisible(true);
        frameForInitialisation.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void createMainWindow() {
        mainFrame = new JFrame("Conrad's Game Of Life");
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // mainFrame.setLocationRelativeTo(null);
        westForMatrix = new JPanel(new GridLayout(rows, columns, 3, 3));
        eastForButton = new JPanel(new GridLayout(10, 1, 3, 3));

        west();
        east();

        mainFrame.add(westForMatrix, BorderLayout.WEST);
        mainFrame.add(eastForButton, BorderLayout.EAST);
        mainFrame.pack();
        mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    private void west() {
        mainFrame.remove(westForMatrix);
        westForMatrix = new JPanel(new GridLayout(rows, columns, 3, 3));
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++) {
                westForMatrix.add(container.getCell(c, r).getRect());
            }
        }
        mainFrame.add(westForMatrix, BorderLayout.WEST);
        mainFrame.pack();
    }

    private void east() {
        next = new JButton("Click to proceed.");
        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.update();
                west();
            }
        });
        clear = new JButton("Click to clear board");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container = new ContainerTwoDimensionGraphic(columns, rows);
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
        quickenTimer.setSize(new Dimension(30, 10));
        slowTimer.setSize(new Dimension(30, 10));
        toggleTimer.setSize(new Dimension(30, 10));
        next.setSize(new Dimension(30, 10));
        clear.setSize(new Dimension(30, 10));
        eastForButton.add(toggleTimer);
        eastForButton.add(quickenTimer);
        eastForButton.add(slowTimer);
        eastForButton.add(next);
        eastForButton.add(clear);
    }
}

// String.toLowerCase()

// TODO what is modality
//public class DialogExample {
//    private static JDialog d;
//    DialogExample() {
//        JFrame f= new JFrame();
//        d = new JDialog(f , "Dialog Example", true);
//        d.setLayout( new FlowLayout() );
//        JButton b = new JButton ("OK");
//        b.addActionListener ( new ActionListener()
//        {
//            public void actionPerformed( ActionEvent e )
//            {
//                DialogExample.d.setVisible(false);
//            }
//        });
//        d.add( new JLabel ("Click button to continue."));
//        d.add(b);
//        d.setSize(300,300);
//        d.setVisible(true);
//    }

// begins with a window that is a dialog with input asking for array dimensions
// disappears for actual window using dimensions above.
// window has a nested label which extends the array of pixels above

// the cellTwoDimension is extended as well; introduce a graphicsCell which is a rectangle
// label contains

// array of squares with borders; preset the dimension;
// there is a label on a window; the label extends the array of pixels aforementioned