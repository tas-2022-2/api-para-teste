package br.edu.ifrs.riogrande.tads.cobaia.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

// lombok
@Getter @Setter @ToString(onlyExplicitlyIncluded = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder @AllArgsConstructor @NoArgsConstructor
@Entity
@Table(name = "doacoes")
public class Doacao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ToString.Include
	Integer id;

	@Column(name = "descricao", nullable = false)
	@ToString.Include
	String descricao;

	@Column(name = "categoria", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	Categoria categoria;

	@Column(name = "situacao", nullable = false)
	@Enumerated(EnumType.ORDINAL)
	Situacao situacao;

	@JoinColumn(name = "pessoas_id", nullable = false)
	@ManyToOne(fetch = FetchType.LAZY)
	Pessoa doador;

}
