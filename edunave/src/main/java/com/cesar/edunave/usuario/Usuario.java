package com.cesar.edunave.usuario;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.Diretorio;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private String tipoAcesso;
    private String turma = "";

    private static final String USUARIO_JSON_FILE_PATH = Diretorio.UsuarioJson.getCaminho();

    public Usuario() {}

    public Usuario(String nome, String email, String tipoAcesso, String turma) {
        this.id = retorneProximoId();
        this.nome = nome;
        if (validarEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("O e-mail fornecido é inválido.");
        }
        this.senha = gerarSenhaAleatoria();
        if (validarTipoAcesso(tipoAcesso)) {
            this.tipoAcesso = tipoAcesso;
        } else {
            throw new IllegalArgumentException("O tipo de acesso fornecido é inválido.");
        }
        if (validarTurma(turma)) {
            this.turma = turma;
        } else {
            throw new IllegalArgumentException("A turma fornecida é inválida.");
        }
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
        this.email = email;
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getTipoAcesso() {
		return this.tipoAcesso;
	}

	public void setTipoAcesso(String tipoAcesso) {
		if (validarTipoAcesso(tipoAcesso)) {
            this.tipoAcesso = tipoAcesso;
        } else {
            throw new IllegalArgumentException("O tipo de acesso fornecido é inválido.");
        }
	}

    public String getTurma() {
		return this.turma;
	}

	public void setTurma(String turma) {
		this.turma = turma;
	}

    private boolean validarEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            System.out.println("O email é inválido.");
            return false;
        }
        return true;
    }

    public boolean validarTipoAcesso(String tipoAcesso) {
        List<String> tiposAcesso = Arrays.asList("Gestor", "Professor", "Estudante");
        return tiposAcesso.contains(tipoAcesso);
    }

    public boolean validarTurma(String turma) {
        List<String> turmas = Arrays.asList("PrimeiroAno", "SegundoAno", "TerceiroAno");
        return turmas.contains(turma) || turma == "";
    }

    private int retorneProximoId() {
        File file = new File(USUARIO_JSON_FILE_PATH);
        if (file.exists() && !file.isDirectory()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                List<Usuario> usuarios = mapper.readValue(file, new TypeReference<List<Usuario>>(){});
                if (!usuarios.isEmpty()) {
                    return usuarios.get(usuarios.size() - 1).getId() + 1;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 1;
    }

    private String gerarSenhaAleatoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder senhaAleatoria = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(caracteres.length());
            senhaAleatoria.append(caracteres.charAt(index));
        }
        return senhaAleatoria.toString();
    }

    public boolean login(String email, String senha) {
        // Implementação do método
        return false;
    }

    public void modificarSenha(String novaSenha) {
        try {
            String content = new String(Files.readAllBytes(Paths.get(USUARIO_JSON_FILE_PATH)));

            JSONArray jsonArray = new JSONArray(content);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject user = jsonArray.getJSONObject(i);
                if (user.getString("email").equals(this.email)) {
                    user.put("senha", novaSenha);
                    break;
                }
            }

            Files.write(Paths.get(USUARIO_JSON_FILE_PATH), jsonArray.toString(4).getBytes());
            System.out.println("Senha modificada com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Eletiva> listaEletivasCadastradas() {
        // Implementação do método
        return null;
    }
}
