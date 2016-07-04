/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.view;

import java.awt.Color;
import java.awt.Font;
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
    private boolean freeze = false, placarfinal = false;
    private BufferedImage imageFrozen;
    private String resultado;
    private Image trofeu;

    public Cenario(Image background, RingView ringView, Personagem a, Personagem b, Placar placar) throws InterruptedException {
        this.personagemA = a;
        this.personagemB = b;
        this.background = background;
        this.placar = placar;
        setBackground(Color.WHITE);
        setBounds(ringView.getBounds());
        ringView.add(this);
        encaixar(ringView);

        String img = (Personagem_Enum.cenarios_path + "trofeu.gif");
        trofeu = new ImageIcon(img).getImage();

        media = new MediaTracker(this);
        media.addImage(background, 0);
        media.addImage(trofeu, 1);
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

    public void placarFinal(Graphics g) {
        if (g == null) {
            placarfinal = true;
        } else {
            int x = getWidth() / 4,
                    y = getHeight() / 2,
                    w = getWidth() / 2,
                    h = getHeight() / 4;

            g.setColor(Color.LIGHT_GRAY);
            g.fill3DRect(x, y, w, h, true);
            g.drawImage(trofeu,
                    x + w - (trofeu.getWidth(this) / 2),
                    y - (trofeu.getHeight(this) / 2), this);
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 48));
            g.translate(10, -20);
            for (String linha : resultado.split("\n")) {
                g.translate(0, 48);
                g.drawString(linha, x + 10, y + (h / 5));
            }
        }
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public void freeze(Personagem vencedor, Personagem perdedor) {
        if (!freeze) {
            placarfinal = false;
            Graphics g = imageFrozen.createGraphics();
            paint(g);
            g.setColor(new Color(0, 0, 0, 100));
            g.fillRect(0, 0, imageFrozen.getWidth(), imageFrozen.getHeight());
            String img = (Personagem_Enum.cenarios_path + "gameover.png");
            Image im = new ImageIcon(img).getImage();
            if (im != null) {
                g.drawImage(im,
                        getWidth() / 10, getHeight() / 10,
                        getWidth() / 2, getHeight() / 2,
                        this);
            }

            setFont(new Font(getFont().getName(), Font.BOLD, 48));
            setForeground(Color.LIGHT_GRAY);
            freeze = true;
        }
    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        if (freeze) {
            g.drawImage(imageFrozen, 0, 0, getWidth(), getHeight(), this);
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
        g.setColor(Color.CYAN);
        g.drawString("miqueiasfernandes.com.br 13/06/16", getWidth() / 2, getHeight() - 5);

        if (placarfinal) {
            placarFinal(g);
        }

    }

    public Placar getPlacar() {
        return placar;
    }

}
