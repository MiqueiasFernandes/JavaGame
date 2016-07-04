/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.image.ImageObserver;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import javagame.decorador.IComponente;
import javagame.estrategia.Estrategia;
import javagame.mediador.AbstractParticipante;
import javagame.mediador.IMediador;
import javax.swing.ImageIcon;

/**
 *
 * @author mfernandes
 */
public class Personagem extends AbstractParticipante implements IComponente {

    private Properties caracteristicas;
    private Point point;
    private Estrategia estrategia;
    private String nome;
    private final Personagem_Enum.Lado lado;
    private int vida, pontos = 0;
    private ImageIcon icon;

    public Personagem(IMediador mediador, String nome, String personagem, Personagem_Enum.Lado lado) throws IOException {
        super(mediador);
        this.nome = nome;
        this.vida = 100;
        caracteristicas = new Properties();
        caracteristicas.load(new FileReader(Personagem_Enum.personagens_path + personagem + ".properties"));
        point = new Point(0, 0);
        this.lado = lado;
        this.icon = new ImageIcon(Personagem_Enum.personagens_path + caracteristicas.getProperty("ico"));
        if (lado == Personagem_Enum.Lado.DIREITA) {
            point.x = icon.getIconWidth() + 50;
        }
    }

    public Properties getCaracteristicas() {
        return caracteristicas;
    }

    public ImageIcon getIcon() {
        return this.icon;
    }

    public void setCaracteristicas(Properties caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public int getY() {
        return point.y;
    }

    public void setY(int y) {
        point.y = y;
    }

    public int getX() {
        return point.x;

    }

    public int getX(boolean convergido) {
        if (!convergido) {
            return point.x;
        }
        int iw = icon.getIconWidth() / 2;
        return lado == Personagem_Enum.Lado.ESQUERDA
                ? (point.x + iw)
                : (point.x - iw);
    }

    public void setX(int x) {
        int oldx = getX();
        int w = mediador.getCenario().getWidth();
        point.x = x;
        if (lado == Personagem_Enum.Lado.ESQUERDA) {

            if (getX(true) > w && x > oldx) {
                point.x = oldx;
            }

            if (getX() < 0 && x < oldx) {
                point.x = oldx;
            }

            if (getX(true) > mediador.getOutroPersonagem(this).getX(true) && x > oldx) {
                point.x = oldx;
            }

        } else {

            if (getX() > w && x > oldx) {
                point.x = oldx;
            }

            if (getX(true) < 0 && x < oldx) {
                point.x = oldx;
            }

            if (getX(true) < mediador.getOutroPersonagem(this).getX(true) && x < oldx) {
                point.x = oldx;
            }
        }
    }

    public Estrategia getEstrategia() {
        return estrategia;
    }

    public void setEstrategia(Estrategia estrategia) {
        this.estrategia = estrategia;
    }

    public Personagem_Enum.Lado getLado() {
        return lado;
    }

    public String getNome() {
        return nome;
    }

    void mostrap(Graphics graphics) {
        //  graphics.fillRect(getX(true), point.y, 10, 10);
    }

    @Override
    public void ocioso(Graphics graphics, ImageObserver imageObserver) {
        mostrap(graphics);

    }

    @Override
    public void agredindo(Graphics graphics, ImageObserver imageObserver) {
        mostrap(graphics);
    }

    @Override
    public void defendendo(Graphics graphics, ImageObserver imageObserver) {
        mostrap(graphics);
    }

    @Override
    public void avancando(Graphics graphics, ImageObserver imageObserver) {
        mostrap(graphics);
    }

    @Override
    public void recuando(Graphics graphics, ImageObserver imageObserver) {
        mostrap(graphics);
    }

    @Override
    public void computaPrejuizo(Personagem de, Personagem para) {
        //  System.out.println("esta encostando " + estaEncostando(para) + " vida " + vida + " prej " + estrategia.calculaPrejuizo());
        if (de == this) {
            return;
        }
        if (estaEncostando(de /* ESTE É O OUTRO PERSONAGEM */)) {
            vida -= estrategia.calculaPrejuizo();
            de.addPontos();
        }
    }

    public boolean estaEncostando(Personagem outro) {

        return (lado == Personagem_Enum.Lado.ESQUERDA)
                ? (getX(true) + 130) >= outro.getX(true) && getX(true) < outro.getX()
                : (getX(true) - 130) <= outro.getX(true) && getX(true) > outro.getX();

    }

    public int getPontos() {
        return pontos;
    }

    public void addPontos() {
        this.pontos++;
    }

    public void addPontos(int pontos) {
        this.pontos += pontos;
    }

    public int getVida() {
        return this.vida;
    }

    public void setVida15() {
        this.vida += 15;//((vida / 100) * 15);
        if (vida > 100) {
            vida = 100;
            pontos++;
        }
    }

    @Override
    public void mouseEvent(MouseEvent e) {

    }

    @Override
    public int pontosAdescontar(Estrategia estrategia) {
        return 0;
    }

}
