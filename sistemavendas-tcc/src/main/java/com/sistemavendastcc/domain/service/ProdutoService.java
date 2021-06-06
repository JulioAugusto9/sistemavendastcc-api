package com.sistemavendastcc.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistemavendastcc.api.model.ProdutoDTO;
import com.sistemavendastcc.domain.exception.NegocioException;
import com.sistemavendastcc.domain.model.Preco;
import com.sistemavendastcc.domain.model.Produto;
import com.sistemavendastcc.domain.repository.PrecoRepository;
import com.sistemavendastcc.domain.repository.ProdutoRepository;

@Service
@Transactional
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PrecoRepository precoRepository;
	
	public ProdutoDTO criar(Produto produto, BigDecimal precoProduto) {
		produto.setDataInclusao(LocalDate.now());
		Produto produtoCriado = produtoRepository.save(produto);
		Preco preco = new Preco(null, LocalDate.now(), precoProduto);
		preco.setProduto(produtoCriado);
		System.out.println(produtoCriado.getId());
		Preco precoCriado = precoRepository.save(preco);
		System.out.println(precoCriado.getProduto().getId());
		return ProdutoDTO.from(precoCriado.getProduto());
	}
	
	public Preco novoPreco(Long produtoId, Preco novoPreco) {
		Produto produto = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new NegocioException("Produto não encontrado"));
		
		novoPreco.setDataInclusao(LocalDate.now());
		novoPreco.setProduto(produto);
		
		return precoRepository.save(novoPreco);
	}
	
}
