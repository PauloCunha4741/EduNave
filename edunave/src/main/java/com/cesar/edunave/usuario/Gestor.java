package com.cesar.edunave.usuario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.Diretorio;
import com.cesar.edunave.enums.TipoAcesso;
import com.cesar.edunave.enums.Turma;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Gestor extends Usuario {

    public Gestor() {
        super();
    }

    public Gestor(String nome, String email) {
        super(nome, email, TipoAcesso.Gestor.name(), "", false);
    }

    private static final String USUARIO_JSON_FILE_PATH = Diretorio.UsuarioJson.getCaminho();
    private static final String ELETIVA_JSON_FILE_PATH = Diretorio.EletivaJson.getCaminho();

    public boolean cadastrarEletiva(String titulo, String ementa, Professor docenteResponsavel, List<String> bibliografia, Turma turma) {
        if (!verificarTituloEletivaDuplicado(titulo)) {
            return false;
        }
        
        if (!validarProfessorCadastrado(docenteResponsavel.getEmail())) {
            System.out.println("Não encontrado cadastro para professor(a) selecionado(a).");
            return false;
        }

        if (!validarDisponibilidadeEletivasParaTurma(turma)) {
            return false;
        }

        Eletiva eletiva = new Eletiva(titulo, ementa, docenteResponsavel, bibliografia, turma.name());

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<Eletiva> eletivas = new ArrayList<>();
        File file = new File(ELETIVA_JSON_FILE_PATH);

        if (file.exists() && file.length() != 0) {
            try {
                eletivas = objectMapper.readValue(file, new TypeReference<List<Eletiva>>(){});
            } catch (IOException e) {
                System.out.println("Erro ao ler eletivas existentes: " + e.getMessage());
                return false;
            }
        }

        eletivas.add(eletiva);

        try {
            objectMapper.writeValue(file, eletivas);
            System.out.println("Eletiva cadastrado com sucesso.");
            return true;
        } catch (IOException e) {
            System.out.println("Erro ao salvar eletiva: " + e.getMessage());
            return false;
        }
    }

    public boolean editarEletiva(String titulo, String ementa, Professor docenteResponsavel, List<String> bibliografia, Turma turma) {
        if (!validarTurma(turma.name())) {
            return false;
        }

        if (!validarProfessorCadastrado(docenteResponsavel.getEmail())) {
            System.out.println("Não encontrado cadastro para professor(a) selecionado(a).");
            return false;
        }

        try {
            String content = new String(Files.readAllBytes(Paths.get(ELETIVA_JSON_FILE_PATH)));

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject eletiva = jsonArray.getJSONObject(i);
                if (eletiva.getString("titulo").equals(titulo)) {
                    if (turma.name() == ""){
                        System.out.println("A Eletiva não pode ficar sem turma definida.");
                        return false;
                    }
                    if (!eletiva.getString("turma").equals(turma.name())) {
                        if (!validarDisponibilidadeEletivasParaTurma(turma)) {
                            return false;
                        }
                    }
                    eletiva.put("titulo", titulo);
                    eletiva.put("ementa", ementa);
                    eletiva.put("docenteResponsavel", docenteResponsavel);
                    eletiva.put("bibliografia", docenteResponsavel);
                    eletiva.put("turma", turma.name());
                    break;
                }
            }

            Files.write(Paths.get(ELETIVA_JSON_FILE_PATH), jsonArray.toString(4).getBytes());
            System.out.println("Eletiva modificada com sucesso.");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletarEletiva(String titulo) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(ELETIVA_JSON_FILE_PATH)));

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject eletiva = jsonArray.getJSONObject(i);
                if (eletiva.getString("titulo").equals(titulo)) {
                    jsonArray.remove(i);
                    break;
                }
            }

            Files.write(Paths.get(ELETIVA_JSON_FILE_PATH), jsonArray.toString(4).getBytes());
            System.out.println("Eletiva excluída com sucesso.");
            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Processo de exclusão de eletiva não foi concluído.");
        return false;
    }

    public boolean validarProfessorCadastrado(String email) {
        List<Usuario> todosUsuarios = new ArrayList<>();
        List<Usuario> professor = new ArrayList<>();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);

        File file = new File(USUARIO_JSON_FILE_PATH);

        if (file.exists() && file.length() != 0) {
            try {
                todosUsuarios = objectMapper.readValue(file, new TypeReference<List<Usuario>>(){});
                
                for (Usuario usuario : todosUsuarios) {
                    if ("Professor".equals(usuario.getTipoAcesso()) && email.equals(usuario.getEmail())) {
                        professor.add(usuario);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (professor.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    private boolean verificarTituloEletivaDuplicado(String titulo) {
        File file = new File(ELETIVA_JSON_FILE_PATH);
        if (file.exists() && file.length() != 0) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Eletiva> eletivas = mapper.readValue(file, new TypeReference<List<Eletiva>>(){});
                for (Eletiva eletiva : eletivas) {
                    if (eletiva.getTitulo().equals(titulo)) {
                        System.out.println("A eletiva já foi cadastrada.");
                        return false;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    private boolean validarDisponibilidadeEletivasParaTurma(Turma turma) {
        File file = new File(ELETIVA_JSON_FILE_PATH);
        int counter = 0;
        if (file.exists() && !file.isDirectory() && file.length() != 0) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Eletiva> eletivas = mapper.readValue(file, new TypeReference<List<Eletiva>>(){});
                for (Eletiva eletiva : eletivas) {
                    if (eletiva.getTurma().equals(turma.name())) {
                        counter += 1;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (turma.name().equals("SegundoAno") && counter <= 7) {
                return true;
            } else if ((turma.name().equals("PrimeiroAno") || turma.name().equals("TerceiroAno")) && counter <= 3) {
                return true;
            } else {
                System.out.println("A quantidade limite de eletivas cadastradas para a turma selecionada já foi alcançada.");
                return false;
            }
        }
        return true;
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
        Estudante usuario = new Estudante(nome, email, turma.name(), false);

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

    public boolean editarUsuario(String email, String nome, String tipoAcesso, String turma, Boolean cadastradoEmEletiva) {
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
                    user.put("cadastradoEmEletiva", cadastradoEmEletiva);
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
        System.out.println("Processo de exclusão de usuário não foi concluído.");
        return false;
    }

    public List<Estudante> listaEstudantesAusentes() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(USUARIO_JSON_FILE_PATH)));
            JSONArray jsonArray = new JSONArray(content);
            List<Estudante> estudantes = new ArrayList<>();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject usuario = jsonArray.getJSONObject(i);
                if ("Estudante".equals(usuario.getString("tipoAcesso")) && usuario.getBoolean("cadastradoEmEletiva") == false) {
                    Estudante estudante = new Estudante(
                        usuario.getString("nome"),
                        usuario.getString("email"),
                        usuario.getString("turma"),
                        usuario.optBoolean("cadastradoEmEletiva", false)
                    );
    
                    estudantes.add(estudante);
                }
            }

            List<Estudante> estudantesNaoCadastradosEmEletiva = estudantes.stream()
                .collect(Collectors.toList());
            estudantesNaoCadastradosEmEletiva.forEach(System.out::println);
            return estudantesNaoCadastradosEmEletiva;

        } catch (IOException e) {
            return null;
        }
    }

    public void alocarEstudanteAusente(Estudante estudante, String tituloEletiva) {        
        estudante.inscreverNaEletiva(tituloEletiva);
    }

    public void alocarTodosEstudantesAusentes() {
        try{
            List<Estudante> estudantesAusentes = this.listaEstudantesAusentes();
            int indexEletiva = 0;

            // Fazer um enquanto para ser executado enquanto length maior que 0;
            // dentro for do estudantes
            // Após isso Pegar JSON da eletiva

            while(estudantesAusentes.size() > 0) {
                String content = new String(Files.readAllBytes(Paths.get(ELETIVA_JSON_FILE_PATH)));
    
                JSONArray eletivaArray = new JSONArray(content);
                JSONObject eletiva = eletivaArray.getJSONObject(indexEletiva);
                for (int i = 0; i < estudantesAusentes.size(); i++) {
                    System.out.println(estudantesAusentes);
                    if (eletiva.getJSONArray("estudantesCadastrados").length() < 44) {
                        estudantesAusentes.get(i).inscreverNaEletiva(eletiva.getString("titulo"));
                        estudantesAusentes.remove(i);
                        // Adicionar estudante 
                    } else {
                        indexEletiva++;
                    }
                }
            }

            System.out.println(estudantesAusentes);


            // String content = new String(Files.readAllBytes(Paths.get(ELETIVA_JSON_FILE_PATH)));
    
            // JSONArray jsonArray = new JSONArray(content);
            // for (int i = 0; i < jsonArray.length(); i++) {
            //     JSONObject eletiva = jsonArray.getJSONObject(i);
            //     if (eletiva.getBoolean("bloqueada")==true){
            //         return;
            //     }
            // }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public List<Usuario> listaEstudantesCadastrados() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Usuario> todosUsuarios = new ArrayList<>();
        List<Usuario> usuariosEstudantes = new ArrayList<>();

        try {
            todosUsuarios = objectMapper.readValue(new File(USUARIO_JSON_FILE_PATH), new TypeReference<List<Usuario>>(){});
            
            for (Usuario usuario : todosUsuarios) {
                if ("Estudante".equals(usuario.getTipoAcesso())) {
                    usuariosEstudantes.add(usuario);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuariosEstudantes;
    }

    public List<Usuario> listaGestoresCadastrados() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Usuario> todosUsuarios = new ArrayList<>();
        List<Usuario> usuariosGestores = new ArrayList<>();

        try {
            todosUsuarios = objectMapper.readValue(new File(USUARIO_JSON_FILE_PATH), new TypeReference<List<Usuario>>(){});
            
            for (Usuario usuario : todosUsuarios) {
                if ("Gestor".equals(usuario.getTipoAcesso())) {
                    usuariosGestores.add(usuario);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return usuariosGestores;
    }
}
