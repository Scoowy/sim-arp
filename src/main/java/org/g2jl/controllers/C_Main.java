package org.g2jl.controllers;

import org.g2jl.models.Diagram;
import org.g2jl.simarp.SimARP;

import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Clase encargada de controlar los eventos realizados por el usuario.
 *
 * @author Scoowy
 * @version 2020.07.18.0000
 */
public class C_Main implements ActionListener {
    private final SimARP view;

    /**
     * Constructor de clase
     *
     * @param view clase de la vista
     */
    public C_Main(SimARP view) {
        this.view = view;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Obtenemos el comando del botón que lanzo la acción
        String command = e.getActionCommand();

        // Según que comando se realiza una u otra acción
        switch (command) {
            case "PLAY": // Acción principal
                // Obtenemos los valores ingresados por el usuario
                String referenceString = view.getTfdReferenceString().getText();
                int numPages = (int) view.getSpnNumPaginas().getValue();

                // Comprobamos que la cadena de referencia no este vacía
                if (!referenceString.equals("")) {
                    // se comprueba que el numero de paginas sea superior a 0
                    if (numPages != 0) {
                        // Cambiamos el estado de los componentes en la ventana
                        view.setChangeState(false);

                        // Obtenemos el diagrama para trabajar con el
                        Diagram diagrama = this.view.getPnlDiagram();
                        // Establecemos el numero de paginas y la cadena de referencia
                        diagrama.setNumPages((int) view.getSpnNumPaginas().getValue());
                        diagrama.setRefString(referenceString);
                        // Ejecutamos el calculo de las paginas usando LRU
                        diagrama.calculatePages();
                        // Re-dibujamos el componente del diagrama especificando solo la zona del area de dibujo
                        diagrama.repaint(diagrama.getCanvasTopLeft().x, diagrama.getCanvasTopLeft().y, diagrama.getCanvasDimension().width, diagrama.getCanvasDimension().height);

                        // Mostramos el numero de errores de pagina que han ocurrido
                        view.getTfdPageErrors().setText(String.valueOf(diagrama.getNumPageErrors()));
                    } else {
                        // Mensaje de alerta
                        JOptionPane.showMessageDialog(null, "Numero de paginas debe ser mayor a 0", "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                    // Mensaje de alerta
                    JOptionPane.showMessageDialog(null, "Cadena de referencia vacía", "Aviso", JOptionPane.WARNING_MESSAGE);
                }
                break;

            case "CLEAR": // Acción de limpiar el formulario
                view.getTfdReferenceString().setText("");
                view.getSpnNumPaginas().setValue(0);
                break;
        }
    }
}
