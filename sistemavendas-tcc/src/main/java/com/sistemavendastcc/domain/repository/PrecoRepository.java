package com.sistemavendastcc.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.sistemavendastcc.domain.model.Preco;

@Repository
public interface PrecoRepository extends JpaRepository<Preco, Long> {
	
	@Query(value = "select p from Preco p where p.id = ?1 and p.dataCriacao <= ?2")
	List<Preco> findByProdutoAndDataCriacao(Long produtoId, LocalDate dataLimite,  Pageable pageable);
}
