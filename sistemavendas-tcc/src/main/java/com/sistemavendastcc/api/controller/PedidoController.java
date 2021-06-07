package com.sistemavendastcc.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistemavendastcc.api.model.PedidoDTO;
import com.sistemavendastcc.api.model.PedidoInput;
import com.sistemavendastcc.api.model.PedidoPagination;
import com.sistemavendastcc.domain.exception.EntidadeNaoEncontradaException;
import com.sistemavendastcc.domain.repository.PedidoRepository;
import com.sistemavendastcc.domain.service.PedidoService;

@RestController
@CrossOrigin
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	PedidoRepository pedidoRepo;
	
	@Autowired
	PedidoService pedidoService;
	
	@GetMapping
	public PedidoPagination listar() {
		PedidoPagination pedidoPagination = new PedidoPagination();
		pedidoPagination.setTotalPages(1L);
		pedidoPagination.setPedidos(PedidoDTO.fromMany(pedidoRepo.findAll()));
		return pedidoPagination;
	}
	
	@GetMapping("/{pedidoId}")
	public PedidoDTO achar(@PathVariable Long pedidoId) {
		return PedidoDTO.from(
				pedidoRepo.findById(pedidoId)
					.orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"))
				);
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PedidoDTO criar(@RequestParam(required = false) Boolean orcamento,
			@Valid @RequestBody PedidoInput pedidoInput) {
		if (orcamento == null) orcamento = false;
		return PedidoDTO.from(pedidoService.criar(pedidoInput, orcamento));
	}
	
	@PostMapping("/{pedidoId}/abertura")
	public PedidoDTO abrirPedido(@PathVariable Long pedidoId) {
		return PedidoDTO.from(pedidoService.abrir(pedidoId));
	}
	
	@DeleteMapping("/{pedidoId}")
	public Long excluir(@PathVariable Long pedidoId) {
		if (!pedidoRepo.existsById(pedidoId))
			throw new EntidadeNaoEncontradaException("Pedido não existe");
		pedidoRepo.deleteById(pedidoId);
		return pedidoId;
	}
}
