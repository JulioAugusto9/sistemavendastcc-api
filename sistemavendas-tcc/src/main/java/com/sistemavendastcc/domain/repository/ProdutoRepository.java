package com.sistemavendastcc.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemavendastcc.domain.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	
}