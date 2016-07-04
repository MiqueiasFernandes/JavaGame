/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.mediador;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;
import javagame.view.Cenario;

/**
 *
 * @author mfernandes
 */
public class Placar extends AbstractParticipante {

    private int tempo = 100;
    private Color va = Color.cyan, vb = Color.GREEN, tp = Color.WHITE;
    private final Image img_placar, help, ajuda;
    private int tamHelp = 30;
    private Rectangle helpTangle;
    private boolean showHelp = false;

    public Placar(IMediador mediador) {
        super(mediador);
        String placar = (Personagem_Enum.cenarios_path + "placar.png");
        String _help = (Personagem_Enum.cenarios_path + "help.png");
        String _ajuda = (Personagem_Enum.cenarios_path + "ajuda.png");
        img_placar = Toolkit.getDefaultToolkit().getImage(placar);
        help = Toolkit.getDefaultToolkit().getImage(_help);
        ajuda = Toolkit.getDefaultToolkit().getImage(_ajuda);

        new Thread(() -> {

            while (tempo > 0) {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Placar.class.getName()).log(Level.SEVERE, null, ex);
                }

                setTempo(tempo - 1);

            }

            mediador.gameOver(Personagem_Enum.ModoGameOver.TIME_OUT);

        }).start();

    }

    public void pintar(Graphics g, Cenario cenario) {

        int x = cenario.getWidth() / 4;
        int y = cenario.getHeight() / 50;
        int w = cenario.getWidth() / 2;
        int h = cenario.getHeight() / 6;

        if (img_placar != null) {
            g.drawImage(img_placar, x, y, w, h, cenario);
        }

        g.setColor(tp);
        ////progressbar tempo
        g.fillRect(x + (w / 7),
                (y + (h / 2) + (h / 4) + (h / 10)),
                ((w - (w / 6)) * tempo) / (100),
                h / 12);

        Font f = new Font("Arial", Font.BOLD, 48);
        Font old = g.getFont();

        ///progressbar vida a 
        g.setColor(va);
        g.fillRect(x + (w / 15),
                (y + (h / 2) - (h / 40)),
                ((((w / 3) - (w / 25)) * getVidaA()) / (100)),
                h / 9);
        g.setFont(f);
        int pontos = mediador.getPersonagemA().getPontos();
        g.drawString(String.valueOf(pontos),
                (w - (w / 9)) + (pontos < 10 ? (w / 50) : (-w / 80)), (y + (h / 2) + (h / 16)));
        g.setFont(old);
        g.drawString(mediador.getPersonagemA().getNome().toUpperCase(),
                x + (w / 5),
                (y + (h / 5) + (h / 6)));

        ///progressbar vida b
        g.setColor(vb);
        g.fillRect(x + (w / 2) + (w / 7) + (w / 300),
                (y + (h / 2) - (h / 24)),
                (((w / 3) - (w / 25)) * getVidaB()) / (100),
                h / 9);
        g.setFont(f);
        pontos = mediador.getPersonagemB().getPontos();
        g.drawString(String.valueOf(pontos),
                (w + (w / 50) + (pontos < 10 ? (w / 50) : (-w / 80))), (y + (h / 2) + (h / 16)));
        g.setFont(old);
        g.drawString(mediador.getPersonagemB().getNome().toUpperCase(),
                x + (w / 2) + (w / 4),
                (y + (h / 5) + (h / 6)));

        if (helpTangle == null) {
            helpTangle = new Rectangle(x + w, y, tamHelp, tamHelp);
        }
        g.drawImage(help, x + w, y, tamHelp, tamHelp, cenario);

        if (showHelp) {
            g.drawImage(ajuda, 0, 0, cenario.getWidth(), cenario.getHeight(), cenario);
        }
    }

    public int getVidaA() {
        setVidaA(mediador.getPersonagemA().getVida());
        return mediador.getPersonagemA().getVida();
    }

    public int getVidaB() {
        setVidaB(mediador.getPersonagemB().getVida());
        return mediador.getPersonagemB().getVida();
    }

    public int getTempo() {
        return tempo;
    }

    public void setVidaA(int vidaA) {
        int val = getInterval(vidaA);
        va = setCor(val, va);
    }

    public void setVidaB(int vidaB) {
        int val = getInterval(vidaB);
        vb = setCor(val, vb);
    }

    public void setTempo(int tempo) {
        int val = getInterval(tempo);
        tp = setCor(val, tp);
        this.tempo = val;
    }

    Color setCor(int val, Color def) {

        if (val > 50) {
            return def;
        }

        if (val > 25) {
            return Color.ORANGE;
        }
        return Color.RED;
    }

    int getInterval(int val) {
        if (val < 0) {
            return 0;
        }
        if (val > 100) {
            return 100;
        }
        return val;
    }

    @Override
    public void computaPrejuizo(Personagem de, Personagem para) {
        setVidaA(mediador.getPersonagemA().getVida());
        setVidaB(mediador.getPersonagemB().getVida());
    }

    @Override
    public void mouseEvent(MouseEvent e) {

        if (e.getClickCount() < 1) {
            if (helpTangle != null && helpTangle.contains(e.getPoint())) {
                tamHelp = 45;
            } else {
                tamHelp = 30;
            }
        } else if (helpTangle != null && helpTangle.contains(e.getPoint())) {
            showHelp = !showHelp;
        }
    }

    public void setShowHelp(boolean showHelp) {
        this.showHelp = showHelp;
    }

}
