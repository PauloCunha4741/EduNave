package com.cesar.edunave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cesar.edunave.enums.TipoAcesso;
import com.cesar.edunave.usuario.Gestor;
import com.cesar.edunave.usuario.Professor;

@SpringBootApplication
public class EdunaveApplication {

	public static void main(String[] args) {
		String nome = "Paulo Cunha";
		String email = "paulo.cunha1@cesar.school";
		Gestor usuario = new Gestor(nome, email);
		usuario.cadastrarUsuario(nome, email, TipoAcesso.Gestor);
		SpringApplication.run(EdunaveApplication.class, args);
	}

}
