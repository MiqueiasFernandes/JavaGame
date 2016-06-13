/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.chain;


import java.awt.event.KeyEvent;
import javagame.mediador.IMediador;

/**
 *
 * @author mfernandes
 */
public class InputChain {

    private AbstractTratador primeiro;
    private IMediador mediador;

    public InputChain(AbstractTratador primeiro, IMediador mediador) {
        this.primeiro = primeiro;
        this.mediador = mediador;
    }

    public void addTratador(AbstractTratador tratador) {
        primeiro.setProximo(tratador);
    }

    public boolean tratar(KeyEvent e) {
        System.out.println("tratando: " + e.getKeyChar());
        return primeiro.verificar(e, mediador);
    }

}
