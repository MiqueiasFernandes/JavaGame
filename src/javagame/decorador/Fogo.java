/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.decorador;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import javagame.estrategia.Estrategia;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;

public class Fogo extends AbstractDecorador {

    public Fogo(IComponente decorado, Personagem personagem) {
        super(decorado, personagem);
        ocioso = agredindo = avancando = recuando = defendendo = getImage("fogo.gif");
    }

    void preparar(Graphics graphics, ImageObserver imageObserver) {
        graphics.translate(i
                + (personagem.getLado() == Personagem_Enum.Lado.ESQUERDA ? 200 : -200),
                ocioso.getHeight(imageObserver) - 100);
        ((Graphics2D) graphics).rotate(-0.4, 300, 300);
    }

    void parado(Graphics graphics, ImageObserver imageObserver) {
        graphics.drawImage(ocioso, 
                (personagem.getLado() == Personagem_Enum.Lado.ESQUERDA ? 50 : -250)
                , 450, 200, 300, imageObserver);
    }

    @Override
    public void ocioso(Graphics graphics, ImageObserver imageObserver) {
        decorado.ocioso(graphics, imageObserver);
        parado(graphics, imageObserver);
    }

    int i = 0;

    @Override
    public void agredindo(Graphics graphics, ImageObserver imageObserver) {
        decorado.agredindo(graphics, imageObserver);
        preparar(graphics, imageObserver);
        i += 30;
        if (i > 300) {
            i = 0;
        }
        pintarImagem(agredindo, imageObserver, graphics, false);
    }

    @Override
    public void defendendo(Graphics graphics, ImageObserver imageObserver) {
        decorado.defendendo(graphics, imageObserver);
        preparar(graphics, imageObserver);
        pintarImagem(defendendo, imageObserver, graphics, false);
    }

    @Override
    public void avancando(Graphics graphics, ImageObserver imageObserver) {
        decorado.avancando(graphics, imageObserver);
        parado(graphics, imageObserver);
    }

    @Override
    public void recuando(Graphics graphics, ImageObserver imageObserver) {
        decorado.recuando(graphics, imageObserver);
        parado(graphics, imageObserver);
    }

    @Override
    public int pontosAdescontar(Estrategia estrategia) {
        return decorado.pontosAdescontar(estrategia) * 5;
    }

}
