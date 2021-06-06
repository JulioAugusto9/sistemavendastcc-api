package com.sistemavendastcc.api.model;

import java.util.List;

public class ProdutoPagination {
	Long totalPages;
	List<ProdutoDTO> produtos;
	
	public Long getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(Long totalPages) {
		this.totalPages = totalPages;
	}
	public List<ProdutoDTO> getProdutos() {
		return produtos;
	}
	public void setProdutos(List<ProdutoDTO> produtos) {
		this.produtos = produtos;
	}
	
	
}
