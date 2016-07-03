/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.chain;

import java.awt.event.KeyEvent;
import javagame.mediador.IMediador;
import javagame.model.Personagem_Enum;
import javagame.view.Cenario;

public class FecharHelp extends AbstractTratador {

    @Override
    public boolean accept(KeyEvent e) {
        return e.getKeyChar() == Personagem_Enum.KEY_SAIR_AJUDA;
    }

    @Override
    public boolean tratar(KeyEvent e, IMediador mediador) {
        Cenario cenario = mediador.getCenario();
        cenario.getPlacar().setShowHelp(false);
        return false;
    }

}
