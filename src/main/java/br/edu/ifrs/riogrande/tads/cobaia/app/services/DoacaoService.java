package br.edu.ifrs.riogrande.tads.cobaia.app.services;

import org.springframework.stereotype.Service;

import br.edu.ifrs.riogrande.tads.cobaia.app.exceptions.NotFoundException;
import br.edu.ifrs.riogrande.tads.cobaia.app.model.Categoria;
import br.edu.ifrs.riogrande.tads.cobaia.app.model.Doacao;
import br.edu.ifrs.riogrande.tads.cobaia.app.model.Pessoa;
import br.edu.ifrs.riogrande.tads.cobaia.app.model.Situacao;
import br.edu.ifrs.riogrande.tads.cobaia.app.repository.DoacaoRepository;
import br.edu.ifrs.riogrande.tads.cobaia.app.repository.PessoaRepository;
import br.edu.ifrs.riogrande.tads.cobaia.app.services.dto.DoacaoRequest;
import br.edu.ifrs.riogrande.tads.cobaia.app.services.dto.SituacaoRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoacaoService {

	private final PessoaRepository pessoaRepository;
	private final DoacaoRepository doacaoRepository;

	public Doacao submeter(String cpf, DoacaoRequest req) {

		Pessoa doador = pessoaRepository.findByCpf(cpf)
			.orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));

		Doacao doacao = Doacao.builder()
			.descricao(req.getDescricao())
			.categoria(Categoria.fromInteger(req.getIdCategoria()))
			.doador(doador)
			.situacao(Situacao.inicial())
			.build();

		// sem builder:
		// Doacao doacao = new Doacao();
		//doacao.setDoador(doador);
		//doacao.setDescricao(req.getDescricao());
		//doacao.setCategoria(Categoria.fromInteger(req.getIdCategoria()));

		return doacaoRepository.save(doacao);
	}

	public Doacao loadDoacao(String cpf, Integer idDoacao) {

		Pessoa doador = pessoaRepository.findByCpf(cpf)
				.orElseThrow(() -> new NotFoundException("Pessoa não encontrada"));

		Doacao doacao = doacaoRepository.findById(idDoacao)
				.orElseThrow(() -> new NotFoundException("Doação não encontrada"));

		if ( ! doacao.getDoador().getId().equals(doador.getId())) {
			// FIXME: criar exceção específica
			throw new IllegalStateException("Doação não pertence ao CPF");
		}

		return doacao;
	}



	public Doacao atualizar(String cpf, Integer idDoacao, DoacaoRequest req) {

			if (! pessoaRepository.existsByCpf(cpf)) {
				throw new NotFoundException("Pessoa não encontrada");
			}
			// FIXME: verificar se a doação é do usuário
			Doacao doacao = doacaoRepository.findById(idDoacao)
				.orElseThrow(() -> new NotFoundException("Doação não encontrada"));

			doacao.setDescricao(req.getDescricao());
			doacao.setCategoria(Categoria.fromInteger(req.getIdCategoria()));

			return doacaoRepository.save(doacao);
	}

	public Doacao atualizarSituacao(String cpf, Integer idDoacao,
		SituacaoRequest req) {
			if (!pessoaRepository.existsByCpf(cpf)) {
			throw new NotFoundException("Pessoa não encontrada");
		}

		Doacao doacao = doacaoRepository.findById(idDoacao)
				.orElseThrow(() -> new NotFoundException("Doação não encontrada"));

		Situacao novaSituacao = Situacao.valueOf(req.getSituacao());
		if (novaSituacao.equals(Situacao.CANCELADA)) {
			// FIXME: fazer esta validação no próprio enum:
			// doacao.getSituacao().permite(novaSituacao)
			switch (doacao.getSituacao()) {
				case CANCELADA: throw new IllegalArgumentException("Já cancelada");
				case CONCLUIDA: throw new IllegalArgumentException("Já concluída");
			}
			doacao.setSituacao(novaSituacao);
			return doacaoRepository.save(doacao);
		}
		// TODO: fazer as demais situações
		throw new UnsupportedOperationException("Não implementado");
	}

}
