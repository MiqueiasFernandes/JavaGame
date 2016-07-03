/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.mediador;

import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import javagame.builder.Builder;
import javagame.builder.Diretor;
import javagame.builder.King;
import javagame.builder.King_especial;
import javagame.decorador.IComponente;
import javagame.estrategia.Ocioso;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;
import javagame.presenter.ChoosePresenter;
import javagame.view.Cenario;
import javagame.view.RingView;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author mfernandes
 */
public class Mediador implements IMediador {

    private final String nome_personagem_A, nome_personagem_B;
    private Cenario cenario;
    private Personagem personagem_A, personagem_B;
    private IComponente componenteA, componenteB;
    private RingView ringView;
    private ArrayList<AbstractParticipante> participantes;

    public Mediador(String nome_personagem_A, String nome_personagem_B) {
        this.nome_personagem_A = nome_personagem_A;
        this.nome_personagem_B = nome_personagem_B;
        participantes = new ArrayList<>();
        new ChoosePresenter(nome_personagem_A, nome_personagem_B, this);
    }

    public void inicializar(String personagem_a, String personagem_b, Image background) throws IOException, InterruptedException, JavaLayerException {
        Builder buildA = getBuilderBasedOnName(personagem_a);
        Builder buildB = getBuilderBasedOnName(personagem_b);

        Diretor diretor = new Diretor(buildA, this);

        componenteA = diretor.criaComponentePersonagem(nome_personagem_A, personagem_a, Personagem_Enum.Lado.ESQUERDA);
        personagem_A = buildA.getPersonagem();

        componenteB = diretor.criaComponentePersonagem(nome_personagem_B, personagem_b, buildB, Personagem_Enum.Lado.DIREITA);
        personagem_B = buildB.getPersonagem();

        ringView = new RingView();

        Placar placar = new Placar(this);
        cenario = new Cenario(background, ringView, personagem_A, personagem_B, placar);

        personagem_A.setEstrategia(new Ocioso(componenteA));
        personagem_B.setEstrategia(new Ocioso(componenteB));

        ringView.setVisible(true);

        Input input = new Input(this);

        participantes.add(new EfeitosAcusticos(this));
        participantes.add(personagem_A);
        participantes.add(personagem_B);
        participantes.add(placar);
        participantes.add(input);

        personagem_B.setX(cenario.getWidth());
        personagem_A.setX(cenario.getWidth() / 3);

        ringView.setTitle("JavaGame - " + personagem_A.getNome() + " X " + personagem_B.getNome());
    }

    Builder getBuilderBasedOnName(String tipo) throws IOException {

        if ("king".equalsIgnoreCase(tipo)) {
            return new King();
        }
        if ("king_especial".equalsIgnoreCase(tipo)) {
            return new King_especial();
        }
        return null;
    }

    @Override
    public Cenario getCenario() {
        return cenario;
    }

    @Override
    public Personagem getPersonagemA() {
        return personagem_A;
    }

    @Override
    public Personagem getPersonagemB() {
        return personagem_B;
    }

    @Override
    public RingView getRingView() {
        return ringView;
    }

    @Override
    public IComponente getComponenteA() {
        return componenteA;
    }

    @Override
    public IComponente getComponenteB() {
        return componenteB;
    }

    @Override
    public IComponente getComponenteBasedOnPersonagem(Personagem personagem) {
        return personagem == personagem_A ? componenteA
                : personagem == personagem_B ? componenteB : null;
    }

    @Override
    public Personagem getOutroPersonagem(Personagem personagem) {
        return personagem == personagem_A ? personagem_B
                : personagem == personagem_B ? personagem_A : null;
    }

    @Override
    public void setAtaque(Personagem de, Personagem para) {
        for (AbstractParticipante participante : participantes) {
            participante.computaPrejuizo(de, para);
        }
    }

    @Override
    public void gameOver(Personagem_Enum.ModoGameOver modo) {
        switch (modo) {
            case A_GANHOU:
                System.out.println("fim de jogo A - " + personagem_A.getNome() + " ganhou");
                break;
            case B_GANHOU:
                System.out.println("fim de jogo B - " + personagem_B.getNome() + " ganhou");
                break;
            case TIME_OUT:
                System.out.println("fim de jogo tempo esgotado");
                break;
            case DESISTENCIA:
                System.out.println("fim de jogo desistencia");
                break;
        }

        System.out.println("pontos A (" + personagem_A.getNome() + "): " + personagem_A.getVida());
        System.out.println("pontos B (" + personagem_B.getNome() + "): " + personagem_B.getVida());

        cenario.freeze();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    System.err.print("Erro ao esperar por terminar " + ex);
                }
                System.exit(0);
            }
        }).start();

    }

    @Override
    public void mouseEvent(MouseEvent e) {
        for (AbstractParticipante participante : participantes) {
            participante.mouseEvent(e);
        }
    }

}
