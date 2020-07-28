package org.g2jl.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Clase que contiene métodos y variables estáticas para trabajar con elementos gráficos.
 *
 * @author Juan Gahona
 * @version 2020.07.17.2221
 */
public class UtilGraphics {
    public static final Color STROKE_COLOR = Color.BLACK;
    public static final Color BACKGROUND_COLOR_PAGE = Color.WHITE;
    public static final Color TEXT_COLOR_PAGE = Color.DARK_GRAY;

    private static final double HEIGHT_FONT_CELL = 0.6;

    public static Font fontPages;
    public static final Font FONT_PAGE = new Font("Segoe UI", Font.PLAIN, 14);

    public static final int MIN_NUM_CELLS_ROW = 2;
    public static final int MAX_NUM_CELLS_ROW = 20;
    public static final int MIN_NUM_PAGES = 1;
    public static final int MAX_NUM_PAGES = 10;
    public static final Dimension MIN_SIZE_CELL = new Dimension(30, 30);
    public static final Dimension MAX_SIZE_CELL = new Dimension(80, 80);

    /**
     * Método que establece una fuente por defecto para las paginas.
     */
    public static void setFontPages() {
        fontPages = FONT_PAGE;
    }

    /**
     * Método que establece una fuente por defecto para las paginas.
     *
     * @param size tamaño de la fuente
     */
    private static void setFontPages(int size) {
        fontPages = new Font(FONT_PAGE.getName(), FONT_PAGE.getStyle(), size);
    }

    /**
     * Método que calcula el tamaño de la fuente respecto al tamaño de la celda de pagina.
     *
     * @param cellH height de la celda
     */
    public static void calcFontSize(int cellH) {
        int size = (int) Math.floor(cellH * HEIGHT_FONT_CELL);
        setFontPages(size);
    }

    /**
     * Método que centra un texto vertical y horizontalmente, dentro de un area definida por dos puntos.
     *
     * @param g           Contexto gráfico
     * @param topLeft     Punto inicial del area
     * @param bottomRight Punto final del area
     * @param text        Numero a centrar
     * @param align       alineación respecto de la celda
     * @return Point punto donde dibujarlo
     */
    public static Point alignText(Graphics g, Point topLeft, Point bottomRight, int text, AlignmentText align) {
        return alignText(g, topLeft, bottomRight, Integer.toString(text), align);
    }

    /**
     * Método que alinea un texto vertical y horizontalmente, dentro de un area definida por dos puntos, en relación a una celda de pagina.
     *
     * @param g           Contexto gráfico
     * @param topLeft     Punto inicial del area
     * @param bottomRight Punto final del area
     * @param text        Texto a centrar
     * @param align       alineación respecto de la celda
     * @return Point punto donde dibujarlo
     */
    public static Point alignText(Graphics g, Point topLeft, Point bottomRight, String text, AlignmentText align) {
        FontMetrics fm = g.getFontMetrics(g.getFont());
        int widthText = fm.stringWidth(text);
        int heightText = fm.getHeight();

        int pX;
        int pY;

        switch (align) {
            case LEFT:
                pX = topLeft.x + bottomRight.x - widthText;
                pY = topLeft.y + bottomRight.y + heightText;
                return new Point(pX, pY);

            case RIGHT:
                pX = topLeft.x - widthText;
                pY = topLeft.y + bottomRight.y + heightText;
                return new Point(pX, pY);

            case CENTER:
                pX = topLeft.x + ((bottomRight.x - widthText) / 2);
                pY = topLeft.y + ((bottomRight.y - heightText) / 2) + fm.getAscent();
                return new Point(pX, pY);

            default:
                return new Point(0, 0);
        }
    }

}

