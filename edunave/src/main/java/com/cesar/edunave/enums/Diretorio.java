package com.cesar.edunave.enums;

public enum Diretorio {
    UsuarioJson("edunave/src/main/resources/data/usuario.json"),
    EletivaJson("edunave/src/main/resources/data/eletiva.json");

    private final String caminho;

    Diretorio(String caminho) {
        this.caminho = caminho;
    }

    public String getCaminho() {
        return caminho;
    }
}
