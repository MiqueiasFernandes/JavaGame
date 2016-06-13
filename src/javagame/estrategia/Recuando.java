/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.estrategia;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import javagame.decorador.IComponente;
import javagame.model.Personagem;

public class Recuando extends Estrategia {

    public Recuando(IComponente componente) {
        super(componente);
    }

    @Override
    public void desenhar_componente(Graphics graphics, ImageObserver imageObserver) {
        componente.recuando(graphics, imageObserver);
    }

    @Override
    public int calculaPrejuizo() {
        return 1;
    }

}
