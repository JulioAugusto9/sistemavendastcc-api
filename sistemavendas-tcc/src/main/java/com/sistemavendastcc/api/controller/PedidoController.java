package com.sistemavendastcc.api.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

import com.sistemavendastcc.api.model.ItemPedidoInput;
import com.sistemavendastcc.api.model.PedidoDTO;
import com.sistemavendastcc.api.model.PedidoInput;
import com.sistemavendastcc.api.model.PedidoPagination;
import com.sistemavendastcc.domain.exception.EntidadeNaoEncontradaException;
import com.sistemavendastcc.domain.model.Empresa;
import com.sistemavendastcc.domain.model.EstadoPedido;
import com.sistemavendastcc.domain.model.NotaFiscal;
import com.sistemavendastcc.domain.repository.NotaFiscalRepository;
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
	
	@Autowired
	NotaFiscalRepository notaRepo;
	
	@GetMapping
	public PedidoPagination listar(@RequestParam(required = false) EstadoPedido estado,
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer itemsPerPage) {
		if (page == null) page = 0;
		else page--;
		if (itemsPerPage == null) itemsPerPage = 6;
		Long totalPages;
		
		if (estado == null) totalPages = pedidoRepo.countByEstadoNot(EstadoPedido.ORCAMENTO);
		else totalPages = pedidoRepo.countByEstado(estado);
		
		totalPages = totalPages / itemsPerPage + (totalPages % itemsPerPage > 0 ? 1 : 0);
		if (totalPages == 0) totalPages = 1L;
		PedidoPagination pedidoPagination = new PedidoPagination();
		pedidoPagination.setTotalPages(totalPages);
		Pageable pageable = PageRequest.of(page, itemsPerPage);
		
		if (estado == null) 
			pedidoPagination.setPedidos(PedidoDTO.fromMany(pedidoRepo.findByEstadoNot(EstadoPedido.ORCAMENTO ,pageable)));
		else 
			pedidoPagination.setPedidos(PedidoDTO.fromMany(pedidoRepo.findByEstado(estado, pageable)));
		
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
	
	@PostMapping("/{pedidoId}/itempedido")
	public PedidoDTO adicionarItemPedido(@PathVariable Long pedidoId,
			@Valid @RequestBody ItemPedidoInput ipi) {
		return PedidoDTO.from(pedidoService.adicionarItemPedido(pedidoId, ipi));
	}
	
	@DeleteMapping("/{pedidoId}/itempedido/{ipId}")
	public PedidoDTO removerItemPedido(@PathVariable Long pedidoId, 
			@PathVariable Long ipId) {
		return PedidoDTO.from(pedidoService.removerItemPedido(pedidoId, ipId));
	}
	
	@PostMapping("/{pedidoId}/abertura")
	public PedidoDTO abrirPedido(@PathVariable Long pedidoId) {
		return PedidoDTO.from(pedidoService.abrir(pedidoId));
	}
	
	@DeleteMapping("/{pedidoId}")
	public Long excluir(@PathVariable Long pedidoId) {
		return pedidoService.excluir(pedidoId);
	}
	
	@GetMapping("/{pedidoId}/notafiscal")
	public NotaFiscal verNotaFiscal(@PathVariable Long pedidoId) {
//		NotaFiscal nf = new NotaFiscal();
//		nf.setCfop(5102L);
//		nf.setNatOp("Venda de mercadorias");
//		nf.setUniao(new BigDecimal(2.86));
//		nf.setIcms(new BigDecimal(1.36));
//		nf.setSubTriIcms(new BigDecimal(2.02));
//		nf.setPreco(new BigDecimal(85.26));
		return notaRepo.findById(pedidoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Nota Fiscal não encontrada"));
	}
	
	@PostMapping("/{pedidoId}/fatura")
	public NotaFiscal faturar(@PathVariable Long pedidoId) {
		return pedidoService.faturar(pedidoId);
	}
	
	@GetMapping("/empresa")
	public Empresa verEmpresa() {
		Empresa emp = new Empresa();
		emp.setNome("ALPES MATERIAIS DE CONSTRUÇÃO");
		emp.setEndereco1("RUA AQUIDABAN, 660");
		emp.setEndereco2("VILA LEÃO - SOROCABA - SP");
		emp.setTelefone("(15)4200-000");
		emp.setCNPJ("23456172854");
		return emp;
	}
}
