/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.decorador;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import javagame.mediador.IMediador;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;
import javagame.view.Cenario;

/**
 *
 * @author mfernandes
 */
public abstract class AbstractDecorador implements IComponente {

    protected IComponente decorado;
    protected Image ocioso, agredindo, defendendo, avancando, recuando;
    protected Personagem personagem;

    public AbstractDecorador(IComponente decorado, Personagem personagem) {
        this.decorado = decorado;
        this.personagem = personagem;
    }

    String getPropriedade(String propriedade) {
        return personagem.getCaracteristicas().getProperty(propriedade);
    }

    Image getImage(String imagem) {
        Image im = null;
        String nome_perso = (Personagem_Enum.personagens_path + imagem);
        im = Toolkit.getDefaultToolkit().getImage(nome_perso);
        IMediador mediador = personagem.getMediador();
        Cenario cenario = mediador.getCenario();
        if (cenario != null) {
            cenario.addImageTracker(im);
        }
        return im;
    }

    Graphics getOrientedGraphics(Graphics g) {
        if (personagem.getLado() == Personagem_Enum.Lado.ESQUERDA) {
            return g;
        }
        g = g.create();
        int w = personagem.getMediador().getCenario().getWidth();
        Graphics2D e = (Graphics2D) g;
        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-w
                - personagem.getX(), 0);
        e.setTransform(tx);
        e.translate(w - personagem.getX(), 0);
        return g;
    }

    public void pintarImagem(Image imagem, ImageObserver imageObserver, Graphics graphics, boolean transladar) {
        if (imagem != null) {
            if (transladar) {
                graphics.translate(personagem.getX(), 0);
            }
            getOrientedGraphics(graphics).drawImage(imagem,
                    personagem.getLado() == Personagem_Enum.Lado.DIREITA ? personagem.getX() : 0,
                    personagem.getY(), imageObserver);

        }
    }

}
