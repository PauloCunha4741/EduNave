package com.cesar.edunave.eletiva;

import java.util.List;

import com.cesar.edunave.enums.Turma;
import com.cesar.edunave.usuario.Estudante;
import com.cesar.edunave.usuario.Professor;

public class Eletiva {
    private int id;
    private String titulo;
    private String ementa;
    private Professor docenteResponsavel;
    private List<String> bibliografia;
    private Turma turma;
    private List<Estudante> estudantesCadastrados;

    public Eletiva(int id, String titulo, String ementa, Professor docenteResponsavel, List<String> bibliografia, Turma turma, List<Estudante> estudantesCadastrados) {
        this.id = id;
        this.titulo = titulo;
        this.ementa = ementa;
        this.docenteResponsavel = docenteResponsavel;
        this.bibliografia = bibliografia;
        this.turma = turma;
        this.estudantesCadastrados = estudantesCadastrados;
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitulo() {
		return this.titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEmenta() {
		return this.ementa;
	}

	public void setEmenta(String ementa) {
		this.ementa = ementa;
	}

	public Professor getDocenteResponsavel() {
		return this.docenteResponsavel;
	}

	public void setDocenteResponsavel(Professor docenteResponsavel) {
		this.docenteResponsavel = docenteResponsavel;
	}

	public List<String> getBibliografia() {
		return this.bibliografia;
	}

	public void setBibliografia(List<String> bibliografia) {
		this.bibliografia = bibliografia;
	}

	public Turma getTurma() {
		return this.turma;
	}

	public void setTurma(Turma turma) {
		this.turma = turma;
	}

	public List<Estudante> getEstudantesCadastrados() {
		return this.estudantesCadastrados;
	}

	public void setEstudantesCadastrados(List<Estudante> estudantesCadastrados) {
		this.estudantesCadastrados = estudantesCadastrados;
	}


    public boolean cadastrarEstudante(Estudante estudante) {
        // Implementação do método
        return false;
    }

    public int verificarVagasDisponiveis() {
        // Implementação do método
        return 0;
    }

    private void bloquearEscolha(Eletiva eletiva) {
        // Implementação do método
    }
}
