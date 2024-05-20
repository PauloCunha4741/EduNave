package com.cesar.edunave.usuario;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.cesar.edunave.enums.Diretorio;
import com.cesar.edunave.enums.TipoAcesso;
import com.cesar.edunave.enums.Turma;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.core.type.TypeReference;

public class Gestor extends Usuario {

    public Gestor() {
        super();
    }

    public Gestor(String nome, String email) {
        super(nome, email, TipoAcesso.Gestor.name());
    }

    private static final String USUARIO_JSON_FILE_PATH = Diretorio.UsuarioJson.getCaminho();

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
        Usuario usuario;
        if (tipoAcesso == TipoAcesso.Gestor) {
            usuario = new Gestor(nome, email);
        } else {
            usuario = new Professor(nome, email);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(USUARIO_JSON_FILE_PATH);

        if (file.exists() && file.length() != 0) {
            try {
                usuarios = objectMapper.readValue(file, new TypeReference<List<Usuario>>(){});
                System.out.println(usuarios);
            } catch (IOException e) {
                System.out.println("Erro ao ler usuários existentes: " + e.getMessage());
                return false;
            }
        }

        usuarios.add(usuario);

        try {
            objectMapper.writeValue(file, usuarios);
            System.out.println("Usuario cadastrado com sucesso.");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao salvar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean cadastrarEstudante(String nome, String email, Turma turma) {
        Estudante usuario = new Estudante(nome, email, turma);

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            objectMapper.writeValue(new File(USUARIO_JSON_FILE_PATH), usuario);
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
