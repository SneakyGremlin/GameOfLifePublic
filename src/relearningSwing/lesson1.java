package relearningSwing;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

public class lesson1 {
    public static void main(String[] args) {
        // >>> its called the event dispatch thread
        //  instance of runnable
        // anonymous inner class
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                MainWindow main = new MainWindow();
//                main.show();

                JFrameOne frame1 = new JFrameOne();
                // JPanelOne panel =  JPanelOne();
                JPanel panel = new JPanel();
                panel.setBackground(Color.red);
                // panel.setVisible(false);
                frame1.add(panel, BorderLayout.CENTER);
                // >>> when placed in center takes up entire space
                // >>> only need to set the top most container as visible; everything else inherits the container's property

                Button button = new Button("Button");
                frame1.add(button, BorderLayout.NORTH);
                // >>> default add location for components in border layout is CENTER; overrides it
                // >>> flowlayout's left center and right add in a line.
                // .setPrefferedSize is overWritten when added to center


                //  ____________________ GRID LAYOUT
                JPanel panel2 = new JPanel(new GridLayout()); // rows and columns possible
                for (int i = 1; i <=5; i++) {
                    JButton button2 = new JButton("Button" + Integer.toString(i));
                    panel2.add(button2);
                }
                frame1.add(panel2);

                // >>> for gridlayout, setting either of rows or columns to 0, intelligenlty produces a compact solution
                // 0,0 is the default behaviour so should fill to occupy all space

                // >>> center area components are resized as well
                // ------------------------------------------------------------------------

                JPanel panel3 = new JPanel();
                frame1.add(panel3);
                JButton button10 = new JButton();
                panel3.add(button10);

                // to remove focus
                button10.setFocusable(false);

                // to set icon
//                ImageIcon imic = new ImageIcon("name of file");
//                button10.setIcon();
                // button10.setIconTextGap();
                // SET shortcut with ALT
                // button10.setMnemonic(KeyEvent.VK_P);
                button10.setToolTipText("");
                button10.setFont(new Font("Arial", Font.PLAIN, 20));
                button10.setMargin(new Insets(10, 10, 10, 10));

                button10.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        System.out.println("Printing");
                    }
                });
                // >>> actionListener has the actionPerformed method.
                // an actionListener is ADDED to an object
                // button.setEnabled();
                button10.doClick();
                button10.setVerticalTextPosition(SwingConstants.BOTTOM);
                button10.setHorizontalTextPosition(SwingConstants.CENTER);

                // button10.setPreferredSize(new Dimension(200, 70));

                // -------------------------------------------------------------
                // JLabel
                //
                JLabel label = new JLabel();
                // label.setForeground(); for text color
                // label.setFont(new Font("Sans-serif", Font.BOLD, 50));

                // ImageIcon i = new ImageIcon();
                // label.setIcon();
                // label.setIconTextGap();
                // label.setHorizontalTextPosition(SwingConstants.);
                // .vertical
                // label.setText("<html> </html>"); // use <br>
                // the image remains, the text alone is updated

                // panel border
                // panel3.setBorder(BorderFactory.createEmptyBorder());
                // panel.setBackground()

                // ------------------------------------------------------------
                // JTextField
                JTextField field = new JTextField();
                //field.setFont();
                //field.setFore/Backgroud
                // field.setToolTipText
                // .setMargin(new Insets())

                // action listener reacts to the user hitting enter whilst inside the field
                field.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {

                    }
                });

                // label.setText()
                // field.getText()
                // field.setText

                // -------------------------------------------------------------------------
                // ActionListener
                // an interface; component defined action has occured
                // a component REQUIRES an action listener to be added to it; the action performed by the action listener is then performed when the signal is received


            }

        });
    }
}

// Integer.toString
// function
// demonstration
