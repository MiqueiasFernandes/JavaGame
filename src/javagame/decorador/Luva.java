/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.decorador;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import javagame.model.Personagem;

public class Luva extends AbstractDecorador {

    public Luva(IComponente decorado, Personagem personagem) {
        super(decorado, personagem);
        ocioso = getImage("luva_ocioso.gif");
        agredindo = getImage("luva_agredindo.gif");
        avancando = getImage("luva_avancando.gif");
        recuando = getImage("luva_recuando.gif");
    }

    @Override
    public void ocioso(Graphics graphics, ImageObserver imageObserver) {
        decorado.ocioso(graphics, imageObserver);
        if (ocioso != null) {
            graphics.drawImage(ocioso, personagem.getX(), personagem.getY(), imageObserver);
        }
    }

    @Override
    public void agredindo(Graphics graphics, ImageObserver imageObserver) {
        decorado.agredindo(graphics, imageObserver);
        if (agredindo != null) {
            graphics.drawImage(agredindo, personagem.getX(), personagem.getY(), imageObserver);
        }
    }

    @Override
    public void defendendo(Graphics graphics, ImageObserver imageObserver) {
        decorado.defendendo(graphics, imageObserver);
    }

    @Override
    public void avancando(Graphics graphics, ImageObserver imageObserver) {
        decorado.avancando(graphics, imageObserver);
        if (avancando != null) {
            graphics.drawImage(avancando, personagem.getX(), personagem.getY(), imageObserver);
        }
    }

    @Override
    public void recuando(Graphics graphics, ImageObserver imageObserver) {
        decorado.recuando(graphics, imageObserver);
        if (recuando != null) {
            graphics.drawImage(recuando, personagem.getX(), personagem.getY(), imageObserver);
        }
    }

}
