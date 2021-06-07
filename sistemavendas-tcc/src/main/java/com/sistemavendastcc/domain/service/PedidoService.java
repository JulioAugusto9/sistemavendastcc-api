package com.sistemavendastcc.domain.service;

import java.time.LocalDate;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemavendastcc.api.model.ItemPedidoInput;
import com.sistemavendastcc.api.model.PedidoInput;
import com.sistemavendastcc.domain.exception.EntidadeNaoEncontradaException;
import com.sistemavendastcc.domain.exception.NegocioException;
import com.sistemavendastcc.domain.model.EstadoPedido;
import com.sistemavendastcc.domain.model.ItemPedido;
import com.sistemavendastcc.domain.model.Pedido;
import com.sistemavendastcc.domain.repository.ClienteRepository;
import com.sistemavendastcc.domain.repository.PedidoRepository;
import com.sistemavendastcc.domain.repository.ProdutoRepository;
import com.sistemavendastcc.domain.repository.UsuarioRepository;

@Service
@Transactional
public class PedidoService {
	
	@Autowired
	ProdutoRepository produtoRepo;
	
	@Autowired
	PedidoRepository pedidoRepo;
	
	@Autowired
	UsuarioRepository usuarioRepo;
	
	@Autowired
	ClienteRepository clienteRepo;
	
	public Pedido criar(PedidoInput pedidoInput, Boolean ehOrcamento) {
		var pedido = new Pedido();
		pedido.setDataCriacao(LocalDate.now());
		pedido.setDesconto(pedidoInput.getDesconto());
		
		if (ehOrcamento) pedido.setEstado(EstadoPedido.ORCAMENTO);
		else pedido.setEstado(EstadoPedido.ABERTO);
		
		pedido.setUsuario(usuarioRepo.findById(pedidoInput.getUsuarioId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado")));
		
		pedido.setCliente(clienteRepo.findById(pedidoInput.getClienteId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Cliente não encontrado")));
		
		pedido.setItensPedido(new ArrayList<ItemPedido>());
		
		for (ItemPedidoInput ipi : pedidoInput.getItensPedido()) {
			var ip = new ItemPedido();
			ip.setQtde(ipi.getQtde());
			ip.setProduto(produtoRepo.findById(ipi.getProdutoId())
					.orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado")));
			ip.setPreco(ip.getProduto().getPrecoAtual());
			
			ip.setPedido(pedido);
			pedido.getItensPedido().add(ip);
		}
		
		return pedidoRepo.save(pedido);
	}
	
	public Pedido abrir(Long id) {
		Pedido pedido = pedidoRepo.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"));
		if (!pedido.getEstado().equals(EstadoPedido.ORCAMENTO))
			throw new NegocioException("Pedido já está " + pedido.getEstado().toString());
		pedido.setEstado(EstadoPedido.ABERTO);
		return pedido;
	}
}	
