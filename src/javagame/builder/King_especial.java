/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.builder;

import javagame.decorador.Luva;

public class King_especial extends Builder {

    @Override
    public void equipaCabeca() {

    }

    @Override
    public void equipaCorpo() {

    }

    @Override
    public void equipaMao() {
        componente = new Luva(componente, personagem);
    }

    @Override
    public void equipaPe() {

    }

}
