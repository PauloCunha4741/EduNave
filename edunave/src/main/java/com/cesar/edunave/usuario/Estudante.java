package com.cesar.edunave.usuario;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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

    public boolean inscreverNaEletiva(Eletiva eletiva) {
        return eletiva.cadastrarEstudante(this);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Estudante estudante = (Estudante) obj;
        return this.getEmail().equals(estudante.getEmail()); // Exemplo de comparação, ajuste conforme necessário
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEmail()); // Correspondente ao método equals
    }
}
