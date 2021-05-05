package com.sistemavendastcc.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistemavendastcc.api.model.PrecoDTO;
import com.sistemavendastcc.api.model.ProdutoDTO;
import com.sistemavendastcc.domain.exception.EntidadeNaoEncontradaException;
import com.sistemavendastcc.domain.model.Preco;
import com.sistemavendastcc.domain.model.Produto;
import com.sistemavendastcc.domain.repository.ProdutoRepository;
import com.sistemavendastcc.domain.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping
	List<ProdutoDTO> listar() {
		List<ProdutoDTO> produtos = new ArrayList<>();
		for (Produto prod :  produtoRepository.findAll()) {
			produtos.add(ProdutoDTO.from(prod));
		}
		return produtos;
	}
	
	@GetMapping("/{produtoId}")
	public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long produtoId) {
		Produto prod = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado"));
		
		return ResponseEntity.ok(ProdutoDTO.from(prod));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ProdutoDTO criar(@RequestBody ProdutoDTO produtoInput) {
		return produtoService.criar(new Produto(null, produtoInput.getDescricao(), produtoInput.getTipoUnidade()), 
				produtoInput.getPreco());
	}
	
	@PutMapping("/{produtoId}")
	public ResponseEntity<Produto> atualizar(@PathVariable Long produtoId, 
			@RequestBody Produto produto){
		
		if (!produtoRepository.existsById(produtoId)) {
			ResponseEntity.notFound().build();
		}
		
		produto.setId(produtoId);
		Produto produtoAlterado = produtoRepository.save(produto);
		
		return ResponseEntity.ok(produtoAlterado);
	}
	
	@PostMapping("/{produtoId}")
	@ResponseStatus(HttpStatus.CREATED)
	PrecoDTO novoPreco(@PathVariable Long produtoId, @RequestBody PrecoDTO preco) {
		return PrecoDTO.from(produtoService.novoPreco(produtoId, new Preco(null, preco.getDataCriacao(), preco.getPreco())));
	}
	
}
