/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.mediador;

import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class EfeitosAcusticos extends AbstractParticipante {

    public EfeitosAcusticos(IMediador mediador) throws JavaLayerException, FileNotFoundException {
        super(mediador);
    }

    @Override
    public void computaPrejuizo(Personagem de, Personagem para) {

        new Thread(() -> {
            try {
                new Player(new FileInputStream(Personagem_Enum.sounds_path + "soco.mp3")).play();
            } catch (JavaLayerException | IOException ex) {
                System.err.println("erro ao executar audio: " + ex);
            }
        }).start();

    }

    @Override
    public void mouseEvent(MouseEvent e) {

    }

}
