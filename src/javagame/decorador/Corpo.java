/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.decorador;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;

public class Corpo extends AbstractDecorador {

    public Corpo(IComponente decorado, Personagem personagem) {
        super(decorado, personagem);

        ocioso = getImage(getPropriedade("ocioso"));
        defendendo = getImage(getPropriedade("defendendo"));
        agredindo = getImage(getPropriedade("agredindo"));
        avancando = getImage(getPropriedade("avancando"));
        recuando = getImage(getPropriedade("recuando"));
    }

    @Override
    public void ocioso(Graphics graphics, ImageObserver imageObserver) {
        decorado.ocioso(graphics, imageObserver);
        if (ocioso != null) {
            graphics.translate(personagem.getX(), 0);
            getOrientedGraphics(graphics).drawImage(ocioso, 
                     personagem.getLado() == Personagem_Enum.Lado.DIREITA ? personagem.getX() : 0, 
                    personagem.getY(), imageObserver);
        }
    }

    @Override
    public void agredindo(Graphics graphics, ImageObserver imageObserver) {
        decorado.agredindo(graphics, imageObserver);
        if (agredindo != null) {
            graphics.translate(personagem.getX(), 0);
            getOrientedGraphics(graphics).drawImage(agredindo, 
                    personagem.getLado() == Personagem_Enum.Lado.DIREITA ? personagem.getX() : 0, 
                    personagem.getY(), imageObserver);

        }
    }

    @Override
    public void defendendo(Graphics graphics, ImageObserver imageObserver) {
        decorado.defendendo(graphics, imageObserver);
        if (defendendo != null) {
            graphics.translate(personagem.getX(), 0);
            getOrientedGraphics(graphics).drawImage(defendendo, 
                    personagem.getLado() == Personagem_Enum.Lado.DIREITA ? personagem.getX() : 0, 
                    personagem.getY(), imageObserver);

        }
    }

    @Override
    public void avancando(Graphics graphics, ImageObserver imageObserver) {
        decorado.avancando(graphics, imageObserver);
        if (avancando != null) {
            graphics.translate(personagem.getX(), 0);
            getOrientedGraphics(graphics).drawImage(avancando, 
                    personagem.getLado() == Personagem_Enum.Lado.DIREITA ? personagem.getX() : 0, 
                    personagem.getY(), imageObserver);

        }
    }

    @Override
    public void recuando(Graphics graphics, ImageObserver imageObserver) {
        decorado.recuando(graphics, imageObserver);
        if (recuando != null) {
            graphics.translate(personagem.getX(), 0);
            getOrientedGraphics(graphics).drawImage(recuando, 
                    personagem.getLado() == Personagem_Enum.Lado.DIREITA ? personagem.getX() : 0, 
                    personagem.getY(), imageObserver);

        }
    }

}
