package org.g2jl.models;

import org.g2jl.utils.AlignmentText;
import org.g2jl.utils.UtilGraphics;

import java.awt.Graphics;
import java.awt.Point;

/**
 * Clase que representa el conjunto de paginas por cada interacción.
 *
 * @author Scoowy
 * @version 2020.07.28.2005
 */
public class Pagination {

    private final char[] VALUES;
    private final Point INIT_POINT;

    public Pagination(char[] values, Point initPoint) {
        this.VALUES = values;
        this.INIT_POINT = initPoint;
    }

    public void paintPagination(Graphics g) {
        Point topLeft = INIT_POINT;
        Point bottomRight = new Point(UtilGraphics.MIN_SIZE_CELL.width, UtilGraphics.MIN_SIZE_CELL.height);

        for (char value : VALUES) {
            g.setColor(UtilGraphics.BACKGROUND_COLOR_PAGE);
            g.fillRect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);
            g.setColor(UtilGraphics.STROKE_COLOR);
            g.drawRect(topLeft.x, topLeft.y, bottomRight.x, bottomRight.y);

            if (value != '\u0000') {
                g.setFont(UtilGraphics.FONT_PAGE);

                Point textPoint = UtilGraphics.alignText(g, topLeft, bottomRight, String.valueOf(value), AlignmentText.CENTER);
                g.setColor(UtilGraphics.TEXT_COLOR_PAGE);
                g.drawString(String.valueOf(value), textPoint.x, textPoint.y);
            }

            topLeft.y += bottomRight.y;
        }
    }

    public boolean isEmpty() {
        return VALUES == null;
    }
}
