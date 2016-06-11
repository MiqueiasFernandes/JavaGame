/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.chain;

import java.awt.event.KeyEvent;
import javagame.estrategia.Avancando;
import javagame.mediador.IMediador;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;

/**
 *
 * @author mfernandes
 */
public class AvancaPersonagem extends AbstractTratador {

    @Override
    public boolean accept(KeyEvent e) {
        return e.getKeyChar() == Personagem_Enum.KEY_PERSONAGEM_A_AVANCA
                || e.getKeyChar() == Personagem_Enum.KEY_PERSONAGEM_B_AVANCA;
    }

    @Override
    public boolean tratar(KeyEvent e, IMediador mediador) {
        Personagem personagem = getPersonagemBasedLeter(mediador, e, Personagem_Enum.KEY_PERSONAGEM_A_AVANCA);

        ///mudar estrategia
        personagem.setEstrategia(new Avancando(mediador.getComponenteBasedOnPersonagem(personagem)));
        ////avancar em x
        personagem.getPoint().x += 10;

        return false;
    }
}
