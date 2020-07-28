package org.g2jl.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class
 *
 * @author Scoowy
 * @version 2020.07.18.0000
 */
public class C_Main implements ActionListener {
//    private final V_Main view;
//    private M_Main model;

//    public C_Main(V_Main view) {
//        this.view = view;
////        this.model = null;
//    }

//    public V_Main getView() {
//        return view;
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "CASO1":
                break;

            case "CASO2":
                break;

            case "CASO3":
                break;
        }
    }
}
