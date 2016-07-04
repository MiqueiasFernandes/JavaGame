/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import javagame.mediador.Mediador;
import javagame.model.Personagem_Enum;
import javagame.view.MainView;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.JFrame;

/**
 *
 * @author mfernandes
 */
public class JavaGame {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        MainView mainView = new MainView();

        try {
            Sequence sequence = MidiSystem.getSequence(new File(Personagem_Enum.sounds_path + "onestop.mid"));
            Sequencer sequencer = MidiSystem.getSequencer();
            sequencer.open();
            sequencer.setSequence(sequence);
            sequencer.setLoopCount(1000);
            sequencer.start();
        } catch (MalformedURLException e) {
            System.err.println("erro ao executar som: " + e);
        } catch (IOException e) {
            System.err.println("erro ao executar som: " + e);
        } catch (MidiUnavailableException | InvalidMidiDataException e) {
            System.err.println("erro ao executar som: " + e);
        }

        mainView.getJogarBtn().addActionListener((ActionEvent e) -> {
            mainView.setVisible(false);

            try {
                new Mediador(mainView.getNomeATxt().getText(),
                        mainView.getNomeBTxt().getText());
            } catch (IOException ex) {
                System.err.println("erro ao carregar audio: " + e);
            }
        });

        mainView.setTitle("Insira o nome dos jogadores");
        mainView.setLocationRelativeTo(null);
        mainView.setState(JFrame.MAXIMIZED_BOTH);
        mainView.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainView.setVisible(true);

    }

}
