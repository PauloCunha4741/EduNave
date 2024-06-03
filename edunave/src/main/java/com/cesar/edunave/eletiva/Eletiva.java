package com.cesar.edunave.eletiva;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cesar.edunave.enums.Diretorio;
import com.cesar.edunave.usuario.Estudante;
import com.cesar.edunave.usuario.Professor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Eletiva {
    private int id;
    private String titulo;
    private String ementa;
    private Professor docenteResponsavel;
    private List<String> bibliografia;
    private String turma;
    private List<Estudante> estudantesCadastrados;
    private int limiteVagas;
    private boolean bloqueada;

    public Eletiva() {}

    public Eletiva(String titulo, String ementa, Professor docenteResponsavel, List<String> bibliografia, String turma) {
			this.id = retorneProximoId();
			this.titulo = titulo;
			this.ementa = ementa;
			this.docenteResponsavel = docenteResponsavel;
			this.bibliografia = bibliografia;
			this.turma = turma;
			this.estudantesCadastrados = new ArrayList<>();
			this.bloqueada = false;
      this.limiteVagas = 40;
    }

    private static final String ELETIVA_JSON_FILE_PATH = Diretorio.EletivaJson.getCaminho();

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

    public String getTurma() {
      return turma;
    }

    public void setTurma(String turma) {
      this.turma = turma;
    }

    public List<Estudante> getEstudantesCadastrados() {
      return estudantesCadastrados;
    }

    public void setEstudantesCadastrados(List<Estudante> estudantesCadastrados) {
      this.estudantesCadastrados = estudantesCadastrados;
    }

    public boolean getBloqueada() {
      return bloqueada;
    }

    public void setBloqueada(boolean bloqueada) {
      this.bloqueada = bloqueada;
    }

    private int retorneProximoId() {
        File file = new File(ELETIVA_JSON_FILE_PATH);
        if (file.exists() && !file.isDirectory()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Eletiva> eletivas = mapper.readValue(file, new TypeReference<List<Eletiva>>(){});
                if (!eletivas.isEmpty()) {
                    return eletivas.get(eletivas.size() - 1).getId() + 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    public int verificarVagasDisponiveis() {
      return limiteVagas - estudantesCadastrados.size();
    }

}
