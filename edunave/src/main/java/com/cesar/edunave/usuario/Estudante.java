package com.cesar.edunave.usuario;

import java.util.Arrays;
import java.util.List;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.TipoAcesso;

public class Estudante extends Usuario {

    public Estudante(String nome, String email, String turma) {
        super(nome, email, TipoAcesso.Estudante.name(), turma);
    }

    public List<Eletiva> verificarEletivasDisponiveis() {
        // Implementação do método
        return null;
    }

    public boolean inscreverNaEletiva(String tituloEletiva) {
        // Implementação do método
        return false;
    }
}
