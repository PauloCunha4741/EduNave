package com.cesar.edunave.usuario;

import java.util.Arrays;
import java.util.List;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.TipoAcesso;
import com.cesar.edunave.enums.Turma;

public class Estudante extends Usuario {
    private Turma turma;

    public Estudante(String nome, String email, TipoAcesso tipoAcesso, Turma turma) {
        super(nome, email, tipoAcesso);
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

    public boolean inscreverNaEletiva(String tituloEletiva) {
        // Implementação do método
        return false;
    }
}
