/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.chain;

import java.awt.event.KeyEvent;
import javagame.estrategia.Agredindo;
import javagame.mediador.IMediador;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;

/**
 *
 * @author mfernandes
 */
public class AtacaPersonagem extends AbstractTratador {

    @Override
    public boolean accept(KeyEvent e) {
        return e.getKeyChar() == Personagem_Enum.KEY_PERSONAGEM_A_ATAQUE
                || e.getKeyChar() == Personagem_Enum.KEY_PERSONAGEM_B_ATAQUE;
    }

    @Override
    public boolean tratar(KeyEvent e, IMediador mediador) {
        Personagem personagem = getPersonagemBasedLeter(mediador, e,
                Personagem_Enum.KEY_PERSONAGEM_A_ATAQUE);

        ////mudar estrategia
        personagem.setEstrategia(new Agredindo(mediador.getComponenteBasedOnPersonagem(personagem)));

        return true;
    }

}
