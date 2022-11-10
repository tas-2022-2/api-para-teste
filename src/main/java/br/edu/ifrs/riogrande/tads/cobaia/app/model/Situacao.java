package br.edu.ifrs.riogrande.tads.cobaia.app.model;

// Processo / Estados
public enum Situacao {

	DISPONIVEL, // 0
	SUSPENSA,   // 1
	CANCELADA,  // 2
	RESERVADA,  // 3
	CONCLUIDA;  // 4

	public static Situacao inicial() {
		return Situacao.DISPONIVEL;
	}
}
