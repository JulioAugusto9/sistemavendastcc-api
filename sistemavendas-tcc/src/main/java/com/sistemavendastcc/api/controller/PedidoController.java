package com.sistemavendastcc.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sistemavendastcc.api.model.PedidoPagination;
import com.sistemavendastcc.domain.repository.PedidoRepository;

@RestController
@CrossOrigin
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	PedidoRepository pedidoRepo;
	
	@GetMapping
	public PedidoPagination listar() {
		PedidoPagination pedidoPagination = new PedidoPagination();
		pedidoPagination.setTotalPages(1L);
		pedidoPagination.setPedidos(pedidoRepo.findAll());
		return pedidoPagination;
	}
}
