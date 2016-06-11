/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.chain;

import java.awt.event.KeyEvent;
import javagame.estrategia.Defendendo;
import javagame.mediador.IMediador;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;

/**
 *
 * @author mfernandes
 */
public class DefendePersonagem extends AbstractTratador {

    @Override
    public boolean accept(KeyEvent e) {
        return e.getKeyChar() == Personagem_Enum.KEY_PERSONAGEM_A_DEFESA
                || e.getKeyChar() == Personagem_Enum.KEY_PERSONAGEM_B_DEFESA;
    }

    @Override
    public boolean tratar(KeyEvent e, IMediador mediador) {
        Personagem personagem = getPersonagemBasedLeter(mediador, e, Personagem_Enum.KEY_PERSONAGEM_A_DEFESA);
        personagem.setEstrategia(new Defendendo(mediador.getComponenteBasedOnPersonagem(personagem)));
        return true;
    }
}
