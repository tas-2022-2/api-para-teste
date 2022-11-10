package br.edu.ifrs.riogrande.tads.cobaia.app.repository;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import br.edu.ifrs.riogrande.tads.cobaia.app.model.Doacao;

public interface DoacaoRepository extends Repository<Doacao, Integer> {

	Doacao save(Doacao doacao);

	Optional<Doacao> findById(Integer id);

}
