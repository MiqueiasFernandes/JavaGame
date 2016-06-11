/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.builder;

import java.io.IOException;
import javagame.decorador.Corpo;
import javagame.decorador.IComponente;
import javagame.mediador.Mediador;
import javagame.model.Personagem;
import javagame.model.Personagem_Enum;

/**
 *
 * @author mfernandes
 */
public abstract class Builder {

    protected IComponente componente;
    protected Personagem personagem;

    public void criaPersonagem(Mediador mediator, String nome, String _personagem, Personagem_Enum.Lado lado) throws IOException {

        personagem = new Personagem(mediator, nome, _personagem, lado);

        componente = personagem;
        componente = new Corpo(componente, personagem);

        equipaCabeca();
        equipaCorpo();
        equipaMao();
        equipaPe();

    }

    public abstract void equipaCabeca();

    public abstract void equipaCorpo();

    public abstract void equipaMao();

    public abstract void equipaPe();

    public IComponente getComponente() {
        return this.componente;
    }

    public Personagem getPersonagem() {
        return personagem;
    }

}
