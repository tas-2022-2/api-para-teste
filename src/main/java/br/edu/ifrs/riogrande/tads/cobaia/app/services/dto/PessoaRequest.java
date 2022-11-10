package br.edu.ifrs.riogrande.tads.cobaia.app.services.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;

// @Validated
public class PessoaRequest {

	@NotNull(message = "O CPF é obrigatório")
	@CPF(message = "CPF inválido: deve ter 11 dígitos com dígito verificador correto")
	private String cpf; // chave

	@NotNull(message = "O Nome é obrigatório")
	@Pattern(regexp = "^\\w{2,}.*", message = "Nome inválido: deve ter pelo menos duas letras")
	private String nome;

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Pessoa " + cpf + " " + nome;
	}

}
