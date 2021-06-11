package com.sistemavendastcc.domain.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sistemavendastcc.domain.model.EstadoPedido;
import com.sistemavendastcc.domain.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	List<Pedido> findByEstado(EstadoPedido estado, Pageable pageable);
	Long countByEstado(EstadoPedido estado);
	
	List<Pedido> findByEstadoNot(EstadoPedido estado, Pageable pageable);
	Long countByEstadoNot(EstadoPedido estado);
}
