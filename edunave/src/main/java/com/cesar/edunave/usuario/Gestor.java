package com.cesar.edunave.usuario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

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
        super(nome, email, TipoAcesso.Gestor.name(), "");
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
        if (!validarEmailParaCadastro(email)) {
            return false;
        }
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
        if (!validarEmailParaCadastro(email)) {
            return false;
        }
        Estudante usuario = new Estudante(nome, email, turma.name());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<Usuario> usuarios = new ArrayList<>();
        File file = new File(USUARIO_JSON_FILE_PATH);

        if (file.exists() && file.length() != 0) {
            try {
                usuarios = objectMapper.readValue(file, new TypeReference<List<Usuario>>(){});
            } catch (IOException e) {
                System.out.println("Erro ao ler usuários existentes: " + e.getMessage());
                return false;
            }
        }

        usuarios.add(usuario);

        try {
            objectMapper.writeValue(file, usuarios);
            System.out.println("Estudante cadastrado com sucesso.");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao salvar estudante: " + e.getMessage());
            return false;
        }
    }

    private boolean validarEmailParaCadastro(String email) {
        File file = new File(USUARIO_JSON_FILE_PATH);
        if (file.exists() && !file.isDirectory()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Usuario> usuarios = mapper.readValue(file, new TypeReference<List<Usuario>>(){});
                for (Usuario usuario : usuarios) {
                    if (usuario.getEmail().equals(email)) {
                        System.out.println("O email já foi cadastrado.");
                        return false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    public boolean editarUsuario(String email, String nome, String tipoAcesso, String turma) {
        if (!validarTipoAcesso(tipoAcesso)) {
            return false;
        }
        if (!validarTurma(turma)) {
            return false;
        }
        try {
            String content = new String(Files.readAllBytes(Paths.get(USUARIO_JSON_FILE_PATH)));

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                if (user.getString("email").equals(email)) {
                    if (user.getString("tipoAcesso").equals("Estudante") && turma == ""){
                        System.out.println("Estudante não pode ficar sem turma definida.");
                        return false;
                    }
                    if (!user.getString("tipoAcesso").equals("Estudante") && !(turma == "")){
                        System.out.println("Usuários do tipo Professor ou Gestor não podem ter turma definida.");
                        return false;
                    }
                    user.put("nome", nome);
                    user.put("tipoAcesso", tipoAcesso);
                    user.put("turma", turma);
                    break;
                }
            }

            Files.write(Paths.get(USUARIO_JSON_FILE_PATH), jsonArray.toString(4).getBytes());
            System.out.println("Usuário modificado com sucesso.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletarUsuario(String email) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(USUARIO_JSON_FILE_PATH)));

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                if (user.getString("email").equals(email)) {
                    jsonArray.remove(i);
                    break;
                }
            }

            Files.write(Paths.get(USUARIO_JSON_FILE_PATH), jsonArray.toString(4).getBytes());
            System.out.println("Usuário excluído com sucesso.");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
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
