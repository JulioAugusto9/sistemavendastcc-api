package com.sistemavendastcc.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sistemavendastcc.domain.model.Preco;
import com.sistemavendastcc.domain.model.Produto;
import com.sistemavendastcc.repository.PrecoRepository;
import com.sistemavendastcc.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PrecoRepository precoRepository;
	
	public List<Produto> listar() {
		List<Produto> produtos = produtoRepository.findAll();
		for (Produto p : produtos) {
			Preco preco = buscarPrecoAtual(p.getId());
			p.setPreco(preco.getPreco());
		}
		return produtos;
	}
	
	public Optional<Produto> buscar(Long produtoId) {
		Optional<Produto> produto = produtoRepository.findById(produtoId);
		
		if (produto.isPresent()) {
			Preco preco = buscarPrecoAtual(produtoId);
			produto.get().setPreco(preco.getPreco());
		}
		
		return produto;
	}
	
	public Produto criar(Produto produto) {
		Produto produtoCriado = produtoRepository.save(produto);
		Preco preco = new Preco(); 
		preco.setDataCriacao(LocalDate.now()); 
		preco.setProduto(produtoCriado);
		preco.setPreco(produto.getPreco());
		Preco precoCriado = precoRepository.save(preco);
		produtoCriado.setPreco(precoCriado.getPreco());
		return produtoCriado;
	}
	
	private Preco buscarPrecoAtual(Long produtoId) {
		return precoRepository.findByProdutoAndDataCriacao(produtoId, LocalDate.now(), 
				PageRequest.of(0, 1, Sort.by("dataCriacao").descending())).get(0);
	}
	
}
