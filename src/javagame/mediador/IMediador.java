/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.mediador;

import java.awt.Point;
import java.awt.event.MouseEvent;
import javagame.decorador.IComponente;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;
import javagame.view.Cenario;
import javagame.view.RingView;

/**
 *
 * @author mfernandes
 */
public interface IMediador {

    public Cenario getCenario();

    public Personagem getPersonagemA();

    public Personagem getPersonagemB();

    public RingView getRingView();

    public IComponente getComponenteA();

    public IComponente getComponenteB();

    public IComponente getComponenteBasedOnPersonagem(Personagem personagem);

    public Personagem getOutroPersonagem(Personagem personagem);

    public void setAtaque(Personagem de, Personagem para);

    public void gameOver(Personagem_Enum.ModoGameOver modo);

    public void mouseEvent(MouseEvent e);

}
