/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.geom.AffineTransform;
import javagame.mediador.Placar;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author mfernandes
 */
public class Cenario extends JPanel {

    Image background;
    RingView ringView;
    Personagem personagemA, personagemB;
    Placar placar;
    MediaTracker media;
    int id = 0;

    public Cenario(Image background, RingView ringView, Personagem a, Personagem b, Placar placar) {
        this.personagemA = a;
        this.personagemB = b;
        this.background = background;
        this.ringView = ringView;
        this.placar = placar;
        setBackground(Color.WHITE);
        setBounds(ringView.getBounds());
        ringView.add(this);
        encaixar(ringView);

        media = new MediaTracker(this);
        media.addImage(background, 0);

        try {
            media.waitForID(0);
        } catch (Exception e) {
        }

        this.setDoubleBuffered(true);

    }

    public void encaixar(JFrame view) {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(view.getContentPane());
        view.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(this, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(this, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        view.pack();
    }

    public int addImageTracker(Image image) {
        id++;
        media.addImage(image, id);
        return id;
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }

        personagemA.getPoint().y = getHeight() - 500;
        personagemB.getPoint().y = getHeight() - 500;
        
        
        if (personagemA.getEstrategia() != null) {
            personagemA.getEstrategia().desenhar_componente(
                    getOrientedGraphics(g.create(), personagemA), this);
        }
        if (personagemB.getEstrategia() != null) {
            personagemB.getEstrategia().desenhar_componente(
                    getOrientedGraphics(g.create(), personagemB), this);
        }

        placar.pintar(g, this);

    }

    Graphics getOrientedGraphics(Graphics g, Personagem personagem) {
        if (personagem.getLado() == Personagem_Enum.Lado.ESQUERDA) {
            return g;
        }

        g = g.create();

        Graphics2D e = (Graphics2D) g;

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-getWidth() - personagem.getPoint().x, 0);
        e.setTransform(tx);

        return g;

    }

}
