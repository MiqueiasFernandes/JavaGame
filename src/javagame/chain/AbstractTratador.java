/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.chain;

import java.awt.event.KeyEvent;
import javagame.mediador.IMediador;
import javagame.model.Personagem;

/**
 *
 * @author mfernandes
 */
public abstract class AbstractTratador {

    AbstractTratador proximo = null;

    public void setProximo(AbstractTratador proximo) {
        if (this.proximo == null) {
            this.proximo = proximo;
        } else {
            this.proximo.setProximo(proximo);
        }
    }

    public boolean verificar(KeyEvent e, IMediador mediador) {
        if (accept(e)) {
            return tratar(e, mediador);
        } else if (proximo != null) {
            return proximo.verificar(e, mediador);
        }
        return false;
    }

    public Personagem getPersonagemBasedLeter(IMediador mediador,
            KeyEvent e, char char_personagem_a) {
        return (e.getKeyChar() == char_personagem_a) ? mediador.getPersonagemA() : mediador.getPersonagemB();
    }

    public abstract boolean accept(KeyEvent e);

    public abstract boolean tratar(KeyEvent e, IMediador mediador);

}
