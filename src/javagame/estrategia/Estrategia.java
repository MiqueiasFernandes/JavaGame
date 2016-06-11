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

/**
 *
 * @author mfernandes
 */
public abstract class Estrategia {

    IComponente componente;

    public Estrategia(IComponente componente) {
        this.componente = componente;
    }

    public abstract void desenhar_componente(Graphics graphics, ImageObserver imageObserver);

    public abstract int calculaPrejuizo(Personagem de);

}
