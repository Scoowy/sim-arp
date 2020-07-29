package org.g2jl.models;

import org.g2jl.utils.AlignmentText;
import org.g2jl.utils.UtilGraphics;

import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar el modelo de negocio de la aplicación.
 * <p>
 * En esta clase se implementa el algoritmo de LRU, en el método calculatePages.
 *
 * @author Scoowy
 * @version 2020.07.28.1854
 */
public class Diagram extends JPanel {
    private String refString;
    private int numPages;
    private int numPageErrors;
    private ArrayList<Pagination> paginations;

    private final int marginPagination = 5;

    private Point canvasTopLeft;
    private Dimension canvasDimension;

    public Diagram() {
        this.refString = "";
        this.numPages = 0;
        this.numPageErrors = 0;
        this.paginations = new ArrayList<>();
        this.canvasTopLeft = new Point(0, 0);
        this.canvasDimension = new Dimension(0, 0);
    }

    public void calculatePages() {
        Point topLeft = new Point(UtilGraphics.PADDING_CANVAS, UtilGraphics.PADDING_CANVAS * 3);

        char[] refString = this.refString.toCharArray();
        char[] pages = new char[numPages];
        int[] pageAccess = new int[numPages];

        // INICIO del algoritmo LRU
        // Recorremos la cadena de referencia
        for (int i = 0; i < refString.length; i++) {
            // Obtenemos el carácter en la posición actual
            char ref = refString[i];

            // Buscamos el valor en las paginas
            int index = findValue(ref, pages);

            if (index != -1) {
                updatePageAccess(index, pageAccess);
                paginations.add(new Pagination(null, topLeft.getLocation()));

                // Desplazamos a la derecha las paginas
                topLeft.x += (UtilGraphics.MIN_SIZE_CELL.width + marginPagination);
            } else {
                index = placePage(ref, pages);

                if (index != -1) {
                    updatePageAccess(index, pageAccess);
                    paginations.add(new Pagination(pages.clone(), topLeft.getLocation()));

                    // Desplazamos a la derecha las paginas
                    topLeft.x += (UtilGraphics.MIN_SIZE_CELL.width + marginPagination);
                } else {
                    deletePage(pages, pageAccess);
                    i -= 1;
                }
            }
            // FIN del algoritmo LRU
        }
    }

    /**
     * Método que establece el valor como parámetro, en la primera posición vacía que encuentre de la lista de paginas.
     *
     * @param value carácter a colocar.
     * @param pages lista de caracteres
     * @return el indice donde se coloco el valor, si no se encontró espacios vacíos devuelve -1.
     */
    private int placePage(char value, char[] pages) {
        for (int i = 0; i < pages.length; i++) {
            if (pages[i] == '\u0000') {
                pages[i] = value;
                // Si se dio un error de pagina se suma 1 a numPageErrors
                numPageErrors += 1;
                return i;
            }
        }
        return -1;
    }

    /**
     * Método que busca un valor dentro de la lista de paginas.
     *
     * @param value valor a buscar.
     * @param pages lita de paginas donde buscar.
     * @return devuelve la posición del valor en la lista o -1 si no se encuentra el valor.
     */
    private int findValue(char value, char[] pages) {
        for (int i = 0; i < pages.length; i++) {
            if (pages[i] == value) return i;
        }
        return -1;
    }

    /**
     * Método que actualiza la lista de accesos a las paginas cuanto mayor sea el valor mas antiguo es su acceso.
     *
     * @param lastAccess indice del ultimo acceso correcto
     * @param pageAccess list de accesos a pagina
     */
    private void updatePageAccess(int lastAccess, int[] pageAccess) {
        pageAccess[lastAccess] = 0;
        for (int i = 0; i < pageAccess.length; i++) {
            if (i != lastAccess) {
                pageAccess[i] += 1;
            }
        }
    }

    /**
     * Método que borra el valor de una pagina, dejando libre para su uso.
     *
     * @param pages      lista de paginas.
     * @param pageAccess lista de accesos a paginas.
     */
    private void deletePage(char[] pages, int[] pageAccess) {
        int olderAccess = -1;
        int indice = -1;

        for (int i = 0; i < pageAccess.length; i++) {
            if (pageAccess[i] > olderAccess) {
                olderAccess = pageAccess[i];
                indice = i;
            }
        }

        if (indice != -1) {
            pages[indice] = '\u0000';
        }
    }

    /**
     * Método que calcula un area de dibujo dentro del panel descontando los padding.
     */
    public void calcSurfaceDraw() {
        Dimension sizePnlDiagram = this.getSize();
        canvasTopLeft.x = UtilGraphics.PADDING_CANVAS;
        canvasTopLeft.y = UtilGraphics.PADDING_CANVAS;

        canvasDimension.setSize(sizePnlDiagram.width - UtilGraphics.PADDING_CANVAS * 2, sizePnlDiagram.height - UtilGraphics.PADDING_CANVAS * 2);
    }

    /**
     * Método encargado de dibujar el diagrama.
     *
     * @param g referencia al entorno grafico.
     */
    public void paintDiagram(Graphics g) {
        // Calculamos el area de dibujo
        calcSurfaceDraw();

        // Si no existe nada que graficar se muestra un mensaje en el area de dibujo
        if (paginations.isEmpty()) {
            // Se establece la funee de texto
            g.setFont(UtilGraphics.FONT_PAGE);
            String message = "Ingrese una cadena de referencia...";

            // Se calcula la posición central respecto del area de dibujo
            Point topLeftText = UtilGraphics.alignText(g, canvasTopLeft, new Point(canvasDimension.width - canvasTopLeft.x, canvasDimension.height - canvasTopLeft.y), message, AlignmentText.CENTER);
            g.setColor(UtilGraphics.TEXT_COLOR_PAGE); // Color del texto
            g.drawString(message, topLeftText.x, topLeftText.y); // Se dibuja el texto
        }

        // Se recorre la lista de paginaciones y se dibujan.
        for (Pagination pagination : paginations) {
            // Se comprueba si la paginacion tiene valores null
            if (!pagination.isEmpty()) {
                pagination.paintPagination(g);
            }
        }
        // Se calculan la posición inicia donde comenzara a dibujarse la lista de caracteres que compone la cadena de referencia
        Point topLeft = new Point(canvasTopLeft.x, canvasTopLeft.y);
        Point bottomRight = new Point(UtilGraphics.MIN_SIZE_CELL.width, UtilGraphics.MIN_SIZE_CELL.height);

        // Se recorre cada caracter y se lo dibuja centrándolo respecto a las paginas
        for (char letter : refString.toCharArray()) {
            g.setFont(UtilGraphics.FONT_PAGE);
            Point topLeftText = UtilGraphics.alignText(g, topLeft, bottomRight, String.valueOf(letter), AlignmentText.CENTER);
            g.drawString(String.valueOf(letter), topLeftText.x, topLeftText.y);

            // Se augmenta en Y para que los caracteres se encuentren listados de manera horizontal
            topLeft.x += UtilGraphics.MIN_SIZE_CELL.width + marginPagination;
        }
    }

    /**
     * Método sobre escrito.
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        g.setColor(UtilGraphics.BACKGROUND_COLOR_CANVAS);
        g.fillRect(canvasTopLeft.x, canvasTopLeft.y, canvasDimension.width, canvasDimension.height);
        g.drawRect(canvasTopLeft.x, canvasTopLeft.y, canvasDimension.width, canvasDimension.height);
        paintDiagram(g);
    }

    // Getters and Setters
    public void setRefString(String refString) {
        this.refString = refString;
    }

    public void setNumPages(int numPages) {
        this.numPages = numPages;
    }

    public int getNumPageErrors() {
        return numPageErrors;
    }

    public Point getCanvasTopLeft() {
        return canvasTopLeft;
    }

    public Dimension getCanvasDimension() {
        return canvasDimension;
    }
}
