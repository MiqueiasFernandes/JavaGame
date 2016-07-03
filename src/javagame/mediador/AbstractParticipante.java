/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.mediador;

import java.awt.event.MouseEvent;
import javagame.model.Personagem;

/**
 *
 * @author mfernandes
 */
public abstract class AbstractParticipante {

    protected IMediador mediador;

    public AbstractParticipante(IMediador mediador) {
        this.mediador = mediador;
    }

    public IMediador getMediador() {
        return mediador;
    }

    public abstract void computaPrejuizo(Personagem de, Personagem para);

    public abstract void mouseEvent(MouseEvent e);

}
