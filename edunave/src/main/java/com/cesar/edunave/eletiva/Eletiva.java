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
    private int limiteVagas;
    private boolean bloqueada;

    public Eletiva(int id, String titulo, String ementa, Professor docenteResponsavel, List<String> bibliografia, Turma turma, List<Estudante> estudantesCadastrados, int limiteVagas) {
			this.id = id;
			this.titulo = titulo;
			this.ementa = ementa;
			this.docenteResponsavel = docenteResponsavel;
			this.bibliografia = bibliografia;
			this.turma = turma;
			this.estudantesCadastrados = estudantesCadastrados;
			this.limiteVagas = limiteVagas;
			this.bloqueada = false;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getTitulo() {
      return titulo;
    }

    public void setTitulo(String titulo) {
      this.titulo = titulo;
    }

    public String getEmenta() {
      return ementa;
    }

    public void setEmenta(String ementa) {
      this.ementa = ementa;
    }

    public Professor getDocenteResponsavel() {
      return docenteResponsavel;
    }

    public void setDocenteResponsavel(Professor docenteResponsavel) {
      this.docenteResponsavel = docenteResponsavel;
    }

    public List<String> getBibliografia() {
      return bibliografia;
    }

    public void setBibliografia(List<String> bibliografia) {
      this.bibliografia = bibliografia;
    }

    public Turma getTurma() {
      return turma;
    }

    public void setTurma(Turma turma) {
      this.turma = turma;
    }

    public List<Estudante> getEstudantesCadastrados() {
      return estudantesCadastrados;
    }

    public void setEstudantesCadastrados(List<Estudante> estudantesCadastrados) {
      this.estudantesCadastrados = estudantesCadastrados;
    }

    public boolean isBloqueada() {
      return bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
      this.bloqueada = bloqueada;
    }

    public boolean cadastrarEstudante(Estudante estudante) {
			// Não realiza o cadastro do estudante se a eletiva estiver: bloqueada ou estudante já cadastrado ou a quantidade de estudantes já cadastrados exceder o limite de vagas
			if (bloqueada || estudantesCadastrados.contains(estudante) || estudantesCadastrados.size() >= limiteVagas) {
				return false;
			}

			// Cadastrando de fato o estudante na eletiva
			estudantesCadastrados.add(estudante);

			// Verifica se após adicionar o estudante, a eletiva atingiu seu limite
			if (estudantesCadastrados.size() >= limiteVagas) {
				bloquearEscolha();
			}

			return true; // Estudante cadastrado com sucesso
    }

    public int verificarVagasDisponiveis() {
      return limiteVagas - estudantesCadastrados.size();
    }

    private void bloquearEscolha() {
     this.bloqueada = true;
    }
}
