package com.example.demo.service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProdutoServiceTest {

    @Mock
    private ProdutoRepository produtoRepository;

    @InjectMocks
    private ProdutoService produtoService;

    @Test
    void deveRetornarProdutoQuandoIdExistir() {
        // implemente
        Produto prod = new Produto(Long.valueOf(1), "camiseta", 0);
        when(produtoRepository.findById(anyLong())).thenReturn(Optional.of(prod));
        Produto produto = produtoService.buscarPorId(Long.valueOf(1));
        assertNotNull(produto);
        assertEquals("camiseta", produto.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoProdutoNaoExistir() {
        // implemente
        assertThrows(RuntimeException.class, () -> produtoService.buscarPorId(anyLong()));
    }
}