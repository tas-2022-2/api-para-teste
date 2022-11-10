package br.edu.ifrs.riogrande.tads.cobaia.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifrs.riogrande.tads.cobaia.app.model.Doacao;
import br.edu.ifrs.riogrande.tads.cobaia.app.model.Pessoa;
import br.edu.ifrs.riogrande.tads.cobaia.app.services.DoacaoService;
import br.edu.ifrs.riogrande.tads.cobaia.app.services.PessoaService;
import br.edu.ifrs.riogrande.tads.cobaia.app.services.dto.DoacaoRequest;
import br.edu.ifrs.riogrande.tads.cobaia.app.services.dto.PessoaRequest;
import br.edu.ifrs.riogrande.tads.cobaia.app.services.dto.SituacaoRequest;
import lombok.RequiredArgsConstructor;

// para "puristas" de rest (restafarian)
// path é para um recurso (subrecurso)
// www.dominio/v1/pessoas/12345678901

@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/api/v1") // solução pragmática para versionamento
public class PessoaController { // definir o resource: Pessoa (api Pessoa)

	private final PessoaService service;
	private final DoacaoService doacaoService;

	// /pessoas/12345678901/doar <- Não se coloca ações/verbos no PATH (viola REST)
	// GET/POST/PUT/DELETE/PATCH
	// /pedido/123434/cancelar    {status:cancelado}

	// Verbo/Método POST não é seguro não idempotente (não pode ir de novo)
	// idempotência: execução "duplicada" refletir no estado servidor
	@PostMapping(path = "/pessoas", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> nova(
			@RequestBody @Valid PessoaRequest body) {

		service.salvar(body);
		return ResponseEntity.status(HttpStatus.OK).build();
	}

	@PutMapping(path = "/pessoas/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(code = HttpStatus.OK)
	public void atualizar(
		@PathVariable(name = "id") String id,
		@RequestBody @Valid PessoaRequest body) {

			UUID uuid = UUID.fromString(id);

			service.update(uuid, body);

	}




	@GetMapping(path = "/pessoas", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Pessoa>> listar() {

		List<Pessoa> pessoas = service.listar();

		return ResponseEntity.ok(pessoas);
	}

	@GetMapping(path = "/pessoas/{cpf}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> ler(
		@Pattern(regexp = "^\\d{11}$", message = "CPF inválido: deve ter 11 dígitos")
		@CPF(message = "CPF inválido: número inválido") // vendor lock-in (atrelando o código a uma lib específica de um fornecedor)
		@PathVariable
		String cpf) {

		return ResponseEntity.ok(service.load(cpf));
	}

	@DeleteMapping(path = "/pessoas/{cpf}")
	@ResponseStatus(code = HttpStatus.OK)
	public void excluir(
		@Pattern(regexp = "^\\d{11}$", message = "CPF inválido: deve ter 11 dígitos")
		@CPF(message = "CPF inválido: número inválido")
		@PathVariable
		String cpf) {

			service.delete(cpf);

	}

	@PostMapping(path = "/pessoas/{cpf}/doacoes")
	public ResponseEntity<?> doar(@RequestBody DoacaoRequest doacao,
		@CPF(message = "CPF inválido: número inválido") @PathVariable String cpf) {

			Doacao persistida = doacaoService.submeter(cpf, doacao);

			URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(persistida.getId()).toUri();

			return ResponseEntity.created(location).build(); // 201
	}

	// FIXME: não retornar a Entidade, em vez retornar um DoacaoDTO
	@GetMapping(path = "/pessoas/{cpf}/doacoes/{id}")
	public ResponseEntity<Doacao> lerDoacao(
		@CPF(message = "CPF inválido: número inválido") @PathVariable String cpf,
		@PathVariable(name = "id") Integer idDoacao) {

		return ResponseEntity.ok(doacaoService.loadDoacao(cpf, idDoacao));
	}

	// PUT, PATCH
	@PutMapping(path = "/pessoas/{cpf}/doacoes/{id}")
	@ResponseStatus(code = HttpStatus.OK)
	public void atualizarDoacao(@RequestBody DoacaoRequest doacao,
			@CPF(message = "CPF inválido: número inválido") @PathVariable String cpf,
			@PathVariable(name = "id") Integer idDoacao) {

		doacaoService.atualizar(cpf, idDoacao, doacao);
	}

	@PatchMapping(path = "/pessoas/{cpf}/doacoes/{id}")
	public ResponseEntity<Doacao> patchDoacao(
		@RequestBody SituacaoRequest novaSituacao,
		@CPF(message = "CPF inválido: número inválido") @PathVariable String cpf,
			@PathVariable(name = "id") Integer idDoacao
	) {

		Doacao atualizada = doacaoService.atualizarSituacao(cpf, idDoacao, novaSituacao);

		return ResponseEntity.ok(atualizada);
	}



}
