package com.cesar.edunave.usuario;

import java.util.List;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.TipoAcesso;

public class Professor extends Usuario {

    public Professor() {
        super();
    }

    public Professor(String nome, String email) {
        super(nome, email, TipoAcesso.Professor.name(), "", false);
    }

    public List<Estudante> visualizarEstudantesCadastradosEletiva(Eletiva eletiva) {
        return eletiva.getEstudantesCadastrados();
    }
}
