package com.cesar.edunave.usuario;

import java.util.List;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.TipoAcesso;

public class Professor extends Usuario {
    public Professor(String nome, String email, TipoAcesso tipoAcesso) {
        super(nome, email, tipoAcesso);
    }

    public List<Estudante> visualizarEstudantesCadastradosEletiva(Eletiva eletiva) {
        // Implementação do método
        return null;
    }
}