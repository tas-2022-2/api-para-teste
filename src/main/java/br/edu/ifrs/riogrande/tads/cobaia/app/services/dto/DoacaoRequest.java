package br.edu.ifrs.riogrande.tads.cobaia.app.services.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DoacaoRequest { // DTO: Data Transfer Object

	String descricao;

	// FIXME: torne-me um enum pls.
	Integer idCategoria;
}
