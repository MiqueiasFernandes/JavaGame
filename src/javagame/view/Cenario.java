/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import javagame.mediador.Placar;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author mfernandes
 */
public class Cenario extends JPanel {

    private Image background;
    private Personagem personagemA, personagemB;
    private Placar placar;
    private MediaTracker media;
    private int id = 0;
    private boolean freeze = false;
    private BufferedImage imageFrozen;

    public Cenario(Image background, RingView ringView, Personagem a, Personagem b, Placar placar) throws InterruptedException {
        this.personagemA = a;
        this.personagemB = b;
        this.background = background;
        this.placar = placar;
        setBackground(Color.WHITE);
        setBounds(ringView.getBounds());
        ringView.add(this);
        encaixar(ringView);

        media = new MediaTracker(this);
        media.addImage(background, 0);
        media.waitForID(0);

        this.setDoubleBuffered(true);
        imageFrozen = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
    }

    public void encaixar(JFrame view) {
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(view.getContentPane());
        view.getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(this, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(this, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1000, Short.MAX_VALUE)
        );
        view.pack();
    }

    public int addImageTracker(Image image) {
        id++;
        media.addImage(image, id);
        return id;
    }

    public void freeze() {
        if (!freeze) {
            Graphics g = imageFrozen.createGraphics();
            paint(g);
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, imageFrozen.getWidth(), imageFrozen.getHeight());
            String img = (Personagem_Enum.cenarios_path + "gameover.png");
            Image im = new ImageIcon(img).getImage();
            if (im != null) {
                g.drawImage(im, 0, 0, imageFrozen.getWidth(), imageFrozen.getHeight(), this);
            }
            freeze = true;
        }
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (freeze) {
            g.drawImage(imageFrozen, 0, 0, null);
            return;
        }

        if (background != null) {
            g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
        }

        personagemA.setY(getHeight() - 500);
        personagemB.setY(getHeight() - 500);

        if (personagemA.getEstrategia() != null) {
            personagemA.getEstrategia().desenhar_componente(
                    g.create(), this);
        }
        if (personagemB.getEstrategia() != null) {
            personagemB.getEstrategia().desenhar_componente(
                    g.create(), this);
        }

        placar.pintar(g, this);

    }

}
