package com.sistemavendastcc.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemavendastcc.domain.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	Usuario findByLogin(String login);
	
	List<Usuario> findByNomeContaining(String nome, Pageable pageable);
	
	Long countByNomeContaining(String nome);
}
