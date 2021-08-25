package com.hit.view;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.Object;
import java.beans.*;
import java.util.Scanner;

/**
 * displays user interface for sending requests to server
 * property : transmits requests to server
 * frame : user interface window
 * button : button to choose an action
 * textArea : area to write responses to client
 */
public class CacheUnitView extends Object {

    private PropertyChangeSupport property;
    private JFrame frame;
    private Button button;
    private JTextArea textArea;

    /**
     * class to display the buttons
     */
    public class Button extends JPanel implements ActionListener {
        protected JButton b1, b2;
        private JFrame frame;

        /**
         * constructor
         */
        public Button() {
            super(new FlowLayout());
            frame = new JFrame();
            b1 = new JButton(" 📂 Load a Request");
            b1.setVerticalTextPosition(AbstractButton.CENTER);
            b1.setHorizontalTextPosition(AbstractButton.LEADING); //aka LEFT, for left-to-right locales
            b1.setActionCommand("load");

            b2 = new JButton(" 📊 Show Statistic");
            b2.setActionCommand("show");

            ButtonActionListener buttonActionListener;
            buttonActionListener = new ButtonActionListener();
            //Listen for actions on buttons 1 and 2.
            b1.addActionListener(this);
            b1.addActionListener(buttonActionListener);
            b2.addActionListener(this);
            b2.addActionListener(buttonActionListener);

            add(b1);
            add(b2);
        }

        /**
         * transmits the requests received in the interface to the server
         *
         * @param e : button command received
         */
        @Override
        public void actionPerformed(ActionEvent e) {
            if ("load".equals(e.getActionCommand())) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("json files", "json"));
                chooser.showOpenDialog(frame);
                File file = chooser.getSelectedFile();
                try {
                    Scanner scanner = new Scanner(new FileReader(file.getAbsolutePath()));
                    property.firePropertyChange("json", null, scanner);

                } catch (FileNotFoundException fileNotFoundException) {
                    System.out.println("File not found");
                } catch (Exception exception) {
                    System.out.println("File loading exception: " + exception);
                }

            } else {
                // show statistic
                property.firePropertyChange("request", null, "statistic");
            }
        }

        public class ButtonActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("show".equals(e.getActionCommand())) {
                    System.out.println("click on show");
                } else {
                    System.out.println("click on load");
                }
            }
        }
    }

    /**
     * constructor
     */
    public CacheUnitView() {

        frame = new JFrame("UI");
        frame.setLayout(new FlowLayout(3, 50, 4));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setSize(450, 450);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        button = new Button();
        textArea = new JTextArea("");
        frame.add(button);
        frame.add(textArea);
        property = new PropertyChangeSupport(this);
    }

    /**
     * open UI
     */
    public void start() {

        frame.setVisible(true);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {

        property.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {

        property.removePropertyChangeListener(pcl);
    }

    /**
     * update UI data to string describing t parameter
     *
     * @param <T> : type of t data
     */
    public <T> void updateUIData(T t) {
        textArea.setText(t.toString());
    }
}











