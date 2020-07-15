package org.g2jl.simarp;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

import javax.swing.*;

/**
 * Main Class
 *
 * @author Scoowy
 * @version 2020.07.12.2135
 */
public class SimARP {
    public static void main(String[] args) {
        System.out.println("Aplicación iniciada");

        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println("Fallo al cargar el tema.");
        }

    }
}
