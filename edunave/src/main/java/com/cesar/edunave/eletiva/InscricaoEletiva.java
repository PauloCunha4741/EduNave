package com.cesar.edunave.eletiva;

import com.cesar.edunave.usuario.Estudante;

public class InscricaoEletiva {
    private int id;
    private Eletiva eletiva;
    private Estudante estudante;

    public InscricaoEletiva(int id, Eletiva eletiva, Estudante estudante) {
        this.id = id;
        this.eletiva = eletiva;
        this.estudante = estudante;
    }

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Eletiva getEletiva() {
		return this.eletiva;
	}

	public void setEletiva(Eletiva eletiva) {
		this.eletiva = eletiva;
	}

	public Estudante getEstudante() {
		return this.estudante;
	}

	public void setEstudante(Estudante estudante) {
		this.estudante = estudante;
	}

}
