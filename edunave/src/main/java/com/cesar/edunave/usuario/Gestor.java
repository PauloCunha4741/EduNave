package com.cesar.edunave.usuario;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.cesar.edunave.enums.TipoAcesso;
import com.cesar.edunave.enums.Turma;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Gestor extends Usuario {
    public Gestor(String nome, String email, TipoAcesso tipoAcesso) {
        super(nome, email, tipoAcesso);
    }

    private static final String JSON_FILE_PATH = "src/main/resources/data/usuario.json";

    public void cadastrarEletiva(String titulo, String ementa, Professor docenteResponsavel, List<String> bibliografia, Turma turma) {
        // Implementação do método
    }

    public void editarEletiva(int idEletiva, String titulo, String ementa, String docenteResponsavel, List<String> bibliografia) {
        // Implementação do método
    }

    public void deletarEletiva(int idEletiva) {
        // Implementação do método
    }

    public boolean cadastrarUsuario(String nome, String email, TipoAcesso tipoAcesso) {
        Usuario usuario = new Professor(nome, email, tipoAcesso);
        if (tipoAcesso == TipoAcesso.Gestor) {
            usuario = new Gestor(nome, email, tipoAcesso);
        }
    
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), usuario);
            System.out.println("Usuario cadastrado com sucesso.");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean cadastrarUsuario(String nome, String email, TipoAcesso tipoAcesso, Turma turma) {
        Estudante usuario = new Estudante(nome, email, tipoAcesso, turma);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(JSON_FILE_PATH), usuario);
            System.out.println("Estudante cadastrado com sucesso.");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean editarUsuario(int idUsuario, String nome, String email, String tipoAcesso) {
        // Implementação do método
        return false;
    }

    public boolean deletarUsuario(int idUsuario) {
        // Implementação do método
        return false;
    }

    public void alocarEstudanteAusente(Estudante estudante) {
        // Implementação do método
    }

    public void alocarTodosEstudantesAusentes() {
        // Implementação do método
    }

    public List<Estudante> listaEstudantesCadastrados() {
        // Implementação do método
        return null;
    }

    public List<Estudante> listaEstudantesAusentes() {
        // Implementação do método
        return null;
    }

    public List<Estudante> listaGestoresCadastrados() {
        // Implementação do método
        return null;
    }
}
