/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.mediador;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javagame.chain.AtacaPersonagem;
import javagame.chain.AvancaPersonagem;
import javagame.chain.DefendePersonagem;
import javagame.chain.InputChain;
import javagame.chain.Ociosopersonagem;
import javagame.chain.RecuaPersonagem;
import javagame.chain.SairDoJogo;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;
import javagame.view.Cenario;
import javagame.view.RingView;

/**
 *
 * @author mfernandes
 */
public class Input extends AbstractParticipante {

    public Input(IMediador mediador) {
        super(mediador);

        InputChain inputChain = new InputChain(new Ociosopersonagem(), mediador);

        inputChain.addTratador(new AvancaPersonagem());
        inputChain.addTratador(new RecuaPersonagem());
        inputChain.addTratador(new AtacaPersonagem());
        inputChain.addTratador(new DefendePersonagem());
        inputChain.addTratador(new SairDoJogo());

        RingView ringView = mediador.getRingView();
        Cenario cenario = mediador.getCenario();

        if (ringView != null) {
            addListener(ringView, inputChain);
        }
        if (cenario != null) {
            addListener(cenario, inputChain);
        }

    }

    void addListener(Component component, InputChain inputChain) {

        component.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e); //To change body of generated methods, choose Tools | Templates.

                inputChain.tratar(e);

            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e); //To change body of generated methods, choose Tools | Templates.

                if (Personagem_Enum.pertence_A(e.getKeyChar())) {
                    e.setKeyChar(Personagem_Enum.KEY_PERSONAGEM_A_OCIOSO);
                }
                if (Personagem_Enum.pertence_B(e.getKeyChar())) {
                    e.setKeyChar(Personagem_Enum.KEY_PERSONAGEM_B_OCIOSO);
                }
                inputChain.tratar(e);
            }

        });

        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                mediador.mouseEvent(e);
            }

        });

    }

    @Override
    public void computaPrejuizo(Personagem de, Personagem para) {

        if (mediador.getPersonagemA().getVida() <= 0 && mediador.getPersonagemB().getVida() > 0) {
            mediador.gameOver(Personagem_Enum.ModoGameOver.B_GANHOU);
        }

        if (mediador.getPersonagemB().getVida() <= 0 && mediador.getPersonagemA().getVida() > 0) {
            mediador.gameOver(Personagem_Enum.ModoGameOver.A_GANHOU);
        }

        if (mediador.getPersonagemB().getVida() <= 0 && mediador.getPersonagemA().getVida() <= 0) {
            mediador.gameOver(Personagem_Enum.ModoGameOver.EMPATOU);
        }

    }

    @Override
    public void mouseEvent(MouseEvent e) {

    }

}
