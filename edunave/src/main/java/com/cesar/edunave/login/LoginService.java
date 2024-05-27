package com.cesar.edunave.login;
import com.cesar.edunave.enums.Diretorio;
import com.cesar.edunave.usuario.Usuario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoginService {
    private List<Usuario> usuarios;

    private static final String USUARIO_JSON_FILE_PATH = Diretorio.UsuarioJson.getCaminho();

    public LoginService() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            usuarios = objectMapper.readValue(new File(USUARIO_JSON_FILE_PATH), new TypeReference<List<Usuario>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean validarCredenciais(String email, String senha) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email) && usuario.getSenha().equals(senha)) {
                return true;
            }
        }
        return false;
    }
}
