package com.cesar.edunave.usuario;

import java.util.List;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.TipoAcesso;

public class Professor extends Usuario {

    public Professor(String nomeProfessor, String emailProfessor, TipoAcesso professor) {
        super();
    }

    public Professor(String nome, String email) {
        super(nome, email, TipoAcesso.Professor.name(), "");
    }

    public List<Estudante> visualizarEstudantesCadastradosEletiva(Eletiva eletiva) {
        return eletiva.getEstudantesCadastrados();
    }
}
