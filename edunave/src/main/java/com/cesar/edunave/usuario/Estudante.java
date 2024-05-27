package com.cesar.edunave.usuario;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.TipoAcesso;
import com.cesar.edunave.enums.Turma;

public class Estudante extends Usuario {
    private Turma turma;

    public Estudante(String nome, String email, Turma turma) {
        super(nome, email, TipoAcesso.Estudante.name());
        if (validarTurma(turma)) {
            this.turma = turma;
        } else {
            throw new IllegalArgumentException("A tumra fornecida é inválida.");
        }
    }

	public Turma getTurma() {
		return this.turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

    private boolean validarTurma(Turma turma) {
        List<Turma> turmas = Arrays.asList(Turma.values());
        return turmas.contains(turma);
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
