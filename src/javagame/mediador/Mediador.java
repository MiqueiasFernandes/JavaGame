/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.mediador;

import java.awt.Image;
import java.awt.Toolkit;
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

    public void inicializar(String personagem_a, String personagem_b, String b_cenario) throws IOException {

        Builder buildA = getBuilderBasedOnName(personagem_a);
        Builder buildB = getBuilderBasedOnName(personagem_b);

        Diretor diretor = new Diretor(buildA, this);

        componenteA = diretor.criaComponentePersonagem(nome_personagem_A, personagem_a, Personagem_Enum.Lado.ESQUERDA);
        personagem_A = buildA.getPersonagem();

        componenteB = diretor.criaComponentePersonagem(nome_personagem_B, personagem_b, buildB, Personagem_Enum.Lado.DIREITA);
        personagem_B = buildB.getPersonagem();

        ringView = new RingView();

        Image im = null;
        String background = (Personagem_Enum.cenarios_path + b_cenario);
        im = Toolkit.getDefaultToolkit().getImage(background);

        Placar placar = new Placar(this);
        cenario = new Cenario(im, ringView, personagem_A, personagem_B, placar);

        personagem_A.setEstrategia(new Ocioso(componenteA));
        personagem_B.setEstrategia(new Ocioso(componenteB));

        ringView.setVisible(true);

        Input input = new Input(this);

        participantes.add(personagem_A);
        participantes.add(personagem_B);
        participantes.add(placar);
        participantes.add(input);

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

}
