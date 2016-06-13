/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.builder;

import java.io.IOException;
import javagame.decorador.IComponente;
import javagame.mediador.IMediador;
import javagame.model.Personagem_Enum;

/**
 *
 * @author mfernandes
 */
public class Diretor {

    private Builder builder;
    private IMediador mediador;

    public Diretor(Builder builder, IMediador mediador) {
        this.builder = builder;
        this.mediador = mediador;
    }

    public IComponente criaComponentePersonagem(String nome, String personagem, Personagem_Enum.Lado lado) throws IOException {
        builder.criaPersonagem(mediador, nome, personagem, lado);
        return builder.getComponente();
    }

    public IComponente criaComponentePersonagem(String nome, String personagem, Builder builder, Personagem_Enum.Lado lado) throws IOException {

        if (builder != null) {
            this.builder = builder;
        }

        this.builder.criaPersonagem(mediador, nome, personagem, lado);
        return this.builder.getComponente();
    }

}
