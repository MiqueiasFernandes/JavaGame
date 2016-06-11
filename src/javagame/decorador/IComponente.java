/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.decorador;

import java.awt.Graphics;
import java.awt.image.ImageObserver;

/**
 *
 * @author mfernandes
 */
public interface IComponente {

    public void ocioso(Graphics graphics, ImageObserver imageObserver);

    public void agredindo(Graphics graphics, ImageObserver imageObserver);

    public void defendendo(Graphics graphics, ImageObserver imageObserver);

    public void avancando(Graphics graphics, ImageObserver imageObserver);

    public void recuando(Graphics graphics, ImageObserver imageObserver);

}
