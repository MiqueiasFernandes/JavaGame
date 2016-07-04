/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javagame.builder;

import javagame.decorador.Fogo;

public class King_Flame extends Builder {

    @Override
    public void equipaCabeca() {

    }

    @Override
    public void equipaCorpo() {

    }

    @Override
    public void equipaMao() {
        componente = new Fogo(componente, personagem);
    }

    @Override
    public void equipaPe() {

    }

}
