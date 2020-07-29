package org.g2jl.simarp;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import org.g2jl.controllers.C_Main;
import org.g2jl.models.Diagram;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Clase encargada de gestionar la vista, ademas de contener el método main de la aplicación.
 * <p>
 * Para esta se utiliza el IDE IntelliJ IDEA, que provee un form builder, simple pero potente.
 *
 * @author Scoowy
 * @version 2020.07.28.1014
 */
public class SimARP {
    private JPanel pnlRoot;
    private JPanel pnlForm;
    private JPanel pnlDiagram;
    private JTextField tfdReferenceString;
    private JSpinner spnNumPaginas;
    private JPanel pnlActions;
    private JButton btnPlay;
    private JButton btnClear;
    private JTextField tfdPageErrors;

    private C_Main controller;

    /**
     * Constructor de clase
     */
    public SimARP() {
        // Asigno comandos y el controlador a los botones
        btnPlay.setActionCommand("PLAY");
        btnPlay.addActionListener(controller);
        btnClear.setActionCommand("CLEAR");
        btnClear.addActionListener(controller);

        // Se establece un modelo de tipo numérico con características de ser entero, 0 >= n <= 50, y con un paso de 1
        this.spnNumPaginas.setModel(new SpinnerNumberModel(0, 0, 50, 1));
    }

    /**
     * Método main que ejecuta la aplicación.
     *
     * @param args argumentos de linea de comandos.
     */
    public static void main(String[] args) {
        // Se establece el uso de la librería FlatLaf para una mejor experiencia visual
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Fallo al cargar el tema.");
        }

        // Se inicializa el Frame contenedor de la aplicacion
        JFrame frame = new JFrame("SimARP - Simulador de sustitución de paginas - LRU"); // titulo
        frame.setContentPane(new SimARP().pnlRoot);
        frame.setResizable(false); // No redimensionable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Finaliza la aplicación correctamente al terminar
        frame.setLocation(100, 100); // Establece que la ventana aparezca en una posición concreta
        frame.pack(); // Empaqueta todos los componentes en la ventana
        frame.setVisible(true); // Muestra la ventana
    }

    /**
     * Método en cual podemos inicializar ciertos componentes de la interfaz.
     */
    private void createUIComponents() {
        this.controller = new C_Main(this);
        this.pnlDiagram = new Diagram();
    }

    /**
     * Método que cambia el estado de los componentes acorde al estado de la aplicación.
     *
     * @param enabled parámetro de activación.
     */
    public void setChangeState(boolean enabled) {
        this.btnClear.setEnabled(enabled);
        this.btnPlay.setEnabled(enabled);
        this.spnNumPaginas.setEnabled(enabled);
        this.tfdReferenceString.setEnabled(enabled);
    }

    // Getters and Setters
    public Diagram getPnlDiagram() {
        return (Diagram) pnlDiagram;
    }

    public JTextField getTfdReferenceString() {
        return tfdReferenceString;
    }

    public JTextField getTfdPageErrors() {
        return tfdPageErrors;
    }

    public JSpinner getSpnNumPaginas() {
        return spnNumPaginas;
    }

}
