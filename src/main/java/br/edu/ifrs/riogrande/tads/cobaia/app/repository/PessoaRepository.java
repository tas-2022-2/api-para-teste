package br.edu.ifrs.riogrande.tads.cobaia.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.Repository;

import br.edu.ifrs.riogrande.tads.cobaia.app.model.Pessoa;
// Spring Data JPA
public interface PessoaRepository extends Repository<Pessoa, UUID> {

// CRUD, Create, Read, Update, Delete

	Pessoa save(Pessoa p);

	List<Pessoa> findAll();

	Optional<Pessoa> findByCpf(String cpf);

	void delete(Pessoa pessoa);

	Optional<Pessoa> findById(UUID uuid);

	boolean existsByCpf(String cpf);

	// É possível usar query nativa
	// @Query(value = "select a, b, c ....", nativeQuery = true)
	// Pessoa findPessoaAlgumaCoisa();

}

// CoC // Convention Over Configuration

/*
@Component
class PessoaRepositoryH2Concreta implements PessoaRepository {

	@Autowired
	EntityManager em;

	@Override
	public Pessoa save(Pessoa p) {
		em.persist(p);
		return p;
	}

	@Override
	public List<Pessoa> findAll() {
		return em.createQuery("FROM Pessoa p").getResultList();
	}

	@Override
	public Optional<Pessoa> findByCpf(String cpf) {
		return Optional.ofNullable(em.createQuery("FROM Pessoa p WHERE p.cpf = :cpf", Pessoa.class)
		.setParameter("cpf", cpf)
		.getSingleResult());
	}

}
*/