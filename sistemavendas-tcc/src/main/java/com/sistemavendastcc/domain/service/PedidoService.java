package com.sistemavendastcc.domain.service;

import java.math.BigDecimal;
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
import com.sistemavendastcc.domain.model.NotaFiscal;
import com.sistemavendastcc.domain.model.Pedido;
import com.sistemavendastcc.domain.repository.ClienteRepository;
import com.sistemavendastcc.domain.repository.ItemPedidoRepository;
import com.sistemavendastcc.domain.repository.NotaFiscalRepository;
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
	
	@Autowired
	NotaFiscalRepository notaRepo;
	
	@Autowired
	ItemPedidoRepository itemPedRepo;
	
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
	
	public Pedido adicionarItemPedido(Long pedidoId, ItemPedidoInput ipi) {
		Pedido pedido = pedidoRepo.findById(pedidoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"));
		
		if (pedido.getEstado().equals(EstadoPedido.FATURADO))
			throw new NegocioException("Pedido já faturado, não pode ser alterado");
		
		var ip = new ItemPedido();
		ip.setQtde(ipi.getQtde());
		ip.setProduto(produtoRepo.findById(ipi.getProdutoId())
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado")));
		ip.setPreco(ip.getProduto().getPrecoAtual());
		
		ip.setPedido(pedido);
		pedido.getItensPedido().add(ip);
		
		return pedidoRepo.save(pedido);
	}
	
	public Pedido removerItemPedido(Long pedidoId, Long itemPedId) {
		Pedido pedido = pedidoRepo.findById(pedidoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"));
		
		if (pedido.getEstado().equals(EstadoPedido.FATURADO))
			throw new NegocioException("Pedido já faturado, não pode ser alterado");
		
		var ip = new ItemPedido();
		ip.setId(itemPedId);
		pedido.getItensPedido().remove(ip);
		
		return pedidoRepo.save(pedido);
	}
	
	public Pedido abrir(Long id) {
		Pedido pedido = pedidoRepo.findById(id)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"));
		if (!pedido.getEstado().equals(EstadoPedido.ORCAMENTO))
			throw new NegocioException("Pedido já está " + pedido.getEstado().toString());
		pedido.setEstado(EstadoPedido.ABERTO);
		return pedidoRepo.save(pedido);
	}
	
	public NotaFiscal faturar(Long id) {
		Pedido pedido = pedidoRepo.findById(id) 
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não encontrado"));
		if (!pedido.getEstado().equals(EstadoPedido.ABERTO))
			throw new NegocioException("Pedido precisa estar ABERTO");
		
		NotaFiscal nf = new NotaFiscal();
		nf.setCfop(5102L);
		nf.setNatOp("Venda de mercadorias");
		nf.setUniao(new BigDecimal(2.86));
		nf.setIcms(new BigDecimal(1.36));
		nf.setSubTriIcms(new BigDecimal(2.02));
		nf.setDataInclusao(LocalDate.now());
		
		nf.setId(pedido.getId());
		nf.setPreco(pedido.getPrecoTotal());
		pedido.setEstado(EstadoPedido.FATURADO);
		pedidoRepo.save(pedido);
		return notaRepo.save(nf);
	}
	
	public Long excluir(Long pedidoId) {
		Pedido pedido = pedidoRepo.findById(pedidoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Pedido não existe"));
		if (pedido.getEstado().equals(EstadoPedido.FATURADO))
			throw new NegocioException("Pedido já faturado, não pode ser excluído");
		pedidoRepo.deleteById(pedidoId);
		return pedidoId;
	}
}	
