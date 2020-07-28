package org.g2jl.views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

/**
 * Class
 *
 * @author Scoowy
 * @version 2020.07.28.1014
 */
public class Principal {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Principal");
        frame.setContentPane(new Principal().pnlRoot);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private JPanel pnlRoot;
    private JPanel pnlForm;
    private JPanel pnlDiagram;
    private JTextField tfdReferenceString;
    private JSpinner spnNumPaginas;
    private JPanel pnlActions;
    private JButton btnPlay;
    private JButton resetButton;
    private JButton clearButton;

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
