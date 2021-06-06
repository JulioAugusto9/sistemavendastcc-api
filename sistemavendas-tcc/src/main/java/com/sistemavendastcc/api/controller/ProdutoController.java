package com.sistemavendastcc.api.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.sistemavendastcc.api.model.PrecoDTO;
import com.sistemavendastcc.api.model.ProdutoDTO;
import com.sistemavendastcc.api.model.ProdutoPagination;
import com.sistemavendastcc.domain.exception.EntidadeNaoEncontradaException;
import com.sistemavendastcc.domain.model.Preco;
import com.sistemavendastcc.domain.model.Produto;
import com.sistemavendastcc.domain.repository.PrecoRepository;
import com.sistemavendastcc.domain.repository.ProdutoRepository;
import com.sistemavendastcc.domain.service.ProdutoService;

@RestController
@CrossOrigin
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private PrecoRepository precoRepo;
	
	@GetMapping
	ProdutoPagination listar(@RequestParam(required = false) String descricao, 
			@RequestParam(required = false) Integer page,
			@RequestParam(required = false) Integer itemsPerPage) {
		if (descricao == null) descricao = "";
		if (page == null) page = 0;
		else page--;
		if (itemsPerPage == null) itemsPerPage = 6;
		Pageable pageable = PageRequest.of(page, itemsPerPage);
		List<ProdutoDTO> produtos = new ArrayList<>();
		for (Produto prod :  produtoRepository.findByDescricaoContaining(descricao, pageable)) {
			produtos.add(ProdutoDTO.from(prod));
		}
		Long totalPages = produtoRepository.countByDescricaoContaining(descricao);
		totalPages = totalPages / itemsPerPage + (totalPages % itemsPerPage > 0 ? 1 : 0);
		if (totalPages == 0) totalPages = 1L;
		ProdutoPagination produtosPaginados =  new ProdutoPagination();
		produtosPaginados.setProdutos(produtos);
		produtosPaginados.setTotalPages(totalPages);
		return produtosPaginados;
	}
	
	@GetMapping("/{produtoId}")
	public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long produtoId) {
		Produto prod = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado"));
		
		return ResponseEntity.ok(ProdutoDTO.from(prod));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	ProdutoDTO criar(@Valid @RequestBody ProdutoDTO produtoInput) {
		return produtoService.criar(new Produto(null, produtoInput.getDescricao(), produtoInput.getTipoUnidade()), 
				produtoInput.getPreco());
	}
	
	@PutMapping("/{produtoId}")
	public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long produtoId, 
			@Valid @RequestBody Produto produto){
		Produto prod = produtoRepository.findById(produtoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado"));
		
		prod.setId(produtoId);
		prod.setDescricao(produto.getDescricao());
		prod.setTipoUnidade(produto.getTipoUnidade());
		
		Produto produtoAlterado = produtoRepository.save(prod);
		
		return ResponseEntity.ok(ProdutoDTO.from(produtoAlterado));
	}
	
	@DeleteMapping("/{produtoId}")
	public ResponseEntity<Long> excluir(@PathVariable Long produtoId) {
		if (!produtoRepository.existsById(produtoId)) {
			return ResponseEntity.notFound().build();
		}
		
		produtoRepository.deleteById(produtoId);
		
		return ResponseEntity.ok(produtoId);
	}
	
	@PostMapping("/{produtoId}/precos")
	@ResponseStatus(HttpStatus.CREATED)
	PrecoDTO novoPreco(@PathVariable Long produtoId, @RequestBody PrecoDTO preco) {
		return PrecoDTO.from(produtoService.novoPreco(produtoId, new Preco(null, preco.getDataCriacao(), preco.getPreco())));
	}
	
	@GetMapping("/{produtoId}/precos")
	List<PrecoDTO> listarPrecos(@PathVariable Long produtoId) {
		List<Preco> precos = (produtoRepository.findById(produtoId)
				.orElseThrow(() -> new EntidadeNaoEncontradaException("Produto não encontrado")))
				.getPrecosAsList();
		var precosDTO = new ArrayList<PrecoDTO>();
		for (Preco p : precos) {
			precosDTO.add(PrecoDTO.from(p));
		}
		return precosDTO;
	}
	
	@DeleteMapping("/{produtoId}/precos/{precoId}")
	ResponseEntity<Long> excluirPreco(@PathVariable Long produtoId, @PathVariable Long precoId) {
		if (!produtoRepository.existsById(produtoId)) {
			return ResponseEntity.notFound().build();
		}
		
		precoRepo.deleteById(precoId);
		
		return ResponseEntity.ok(precoId);
	}
}
