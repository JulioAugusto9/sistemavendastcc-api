package com.sistemavendastcc.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemavendastcc.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
	List<Produto> findByDescricaoContaining(String descricao, Pageable pageable);
	
	Long countByDescricaoContaining(String descricao);
	
}
