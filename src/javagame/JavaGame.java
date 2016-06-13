/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javagame.mediador.Mediador;
import javagame.view.MainView;

/**
 *
 * @author mfernandes
 */
public class JavaGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     
        MainView mainView = new MainView();

        mainView.getJogarBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                mainView.setVisible(false);

                new Mediador(mainView.getNomeATxt().getText(),
                        mainView.getNomeBTxt().getText());

            }
        });

        mainView.setVisible(true);

    }

}
