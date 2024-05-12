package com.cesar.edunave.usuario;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;

import com.cesar.edunave.eletiva.Eletiva;
import com.cesar.edunave.enums.TipoAcesso;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract class Usuario {
    private int id;
    private String nome;
    private String email;
    private String senha;
    private TipoAcesso tipoAcesso;

    public Usuario(String nome, String email, TipoAcesso tipoAcesso) {
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
        if (validarEmail(email)) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("O e-mail fornecido é inválido.");
        }
	}

	public String getSenha() {
		return this.senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public TipoAcesso getTipoAcesso() {
		return this.tipoAcesso;
	}

	public void setTipoAcesso(TipoAcesso tipoAcesso) {
		if (validarTipoAcesso(tipoAcesso)) {
            this.tipoAcesso = tipoAcesso;
        } else {
            throw new IllegalArgumentException("O tipo de acesso fornecido é inválido.");
        }
	}

    private boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    private boolean validarTipoAcesso(TipoAcesso tipoAcesso) {
        List<TipoAcesso> tiposAcesso = Arrays.asList(TipoAcesso.values());
        return tiposAcesso.contains(tipoAcesso);
    }

    private int retorneProximoId() {
        File file = new File("resources/data/usuario.json");
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
        return 1; // Se o arquivo não existir ou não conter nenhum usuário, retorna 1 como ID padrão
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

    public void modificarSenha(String senhaNova) {
        // Implementação do método
    }

    public List<Eletiva> listaEletivasCadastradas() {
        // Implementação do método
        return null;
    }
}
