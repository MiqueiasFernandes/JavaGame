/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.model;

import java.awt.Graphics;
import java.awt.Point;
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
    private int vida;

    public Personagem(IMediador mediador, String nome, String personagem, Personagem_Enum.Lado lado) throws IOException {
        super(mediador);
        this.nome = nome;
        this.vida = 100;
        caracteristicas = new Properties();
        caracteristicas.load(new FileReader("src/javagame/personagens/" + personagem + ".properties"));
        point = new Point(0, 0);
        this.lado = lado;
    }

    public Properties getCaracteristicas() {
        return caracteristicas;
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

    public void setX(int x) {

        int oldX = point.x;

        point.x = x;

        System.out.println(nome + " > " + point);
        System.out.println(nome + " max " + getPontXmax());

        if ((lado == Personagem_Enum.Lado.ESQUERDA
                && getPontXmax() > mediador.getOutroPersonagem(this).getPontXmax())
                || x < 10 || x > mediador.getCenario().getWidth()) {
            point.x = oldX;
        }
        if ((lado == Personagem_Enum.Lado.DIREITA
                && getPontXmax() < mediador.getOutroPersonagem(this).getPontXmax())
                || x < 10 || x > mediador.getCenario().getWidth()) {
            point.x = oldX;
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

    @Override
    public void ocioso(Graphics graphics, ImageObserver imageObserver) {

    }

    @Override
    public void agredindo(Graphics graphics, ImageObserver imageObserver) {
    }

    @Override
    public void defendendo(Graphics graphics, ImageObserver imageObserver) {

    }

    @Override
    public void avancando(Graphics graphics, ImageObserver imageObserver) {

    }

    @Override
    public void recuando(Graphics graphics, ImageObserver imageObserver) {

    }

    @Override
    public void computaPrejuizo(Personagem de, Personagem para) {

        if (de == this) {
            return;
        }

        //   vida -= estrategia.calculaPrejuizo(de);
    }

    public int getVida() {
        return this.vida;
    }

    public int getPontXmax() {

        ImageIcon ic = new ImageIcon(Personagem_Enum.personagens_path + caracteristicas.getProperty("ico"));

        if (lado == Personagem_Enum.Lado.ESQUERDA) {
            return point.x + (ic.getIconWidth() / 2);
        }

        return mediador.getCenario().getWidth() - point.x - (ic.getIconWidth() / 2);
    }

}
