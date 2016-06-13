/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.mediador;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
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
    private Color va = Color.cyan, vb = Color.GREEN, tp = Color.DARK_GRAY;
    private Image img_placar;

    public Placar(IMediador mediador) {
        super(mediador);
        String nome_perso = (Personagem_Enum.cenarios_path + "score.png");
        img_placar = Toolkit.getDefaultToolkit().getImage(nome_perso);

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

//        setTempo((tempo % 100) + 1);
//        setVidaA((vidaA % 100) + 1);
//        setVidaB( (vidaB % 100) + 1);
//        setTempo(tempo-=1);
//        setVidaA(vidaA-=1);
//        setVidaB(vidaB-=1);
        ///imagem principal
        int x = cenario.getWidth() / 2 - ((cenario.getWidth() / 3) / 2);
        int y = cenario.getHeight() / 50;
        int w = cenario.getWidth() / 3;
        int h = cenario.getHeight() / 6;

        if (img_placar != null) {
            g.drawImage(img_placar, x, y, w, h, cenario);
        }

        g.setColor(tp);
        ////progressbar tempo
        g.fillRect(x + (w / 20), (y + (h / 2) + (h / 4) + (h / 30)),
                ((w - (w / 6)) * tempo) / (100),
                h / 15);

        ///progressbar vida a 
        g.setColor(va);
        g.fillRect(x + (w / 25), (y + (h / 2) + (h / 7)),
                ((w / 3) * getVidaA()) / (100),
                h / 30);
        g.drawString(mediador.getPersonagemA().getNome(), x + (w / 5), (y + (h / 2) + (h / 13)));

        ///progressbar vida b
        g.setColor(vb);
        g.fillRect(x + (w / 2) + (w / 16), (y + (h / 2) + (h / 7)),
                ((w / 3) * getVidaB()) / (100),
                h / 30);
        g.drawString(mediador.getPersonagemB().getNome(), x + (w / 2) + (w / 5), (y + (h / 2) + (h / 13)));
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

}
