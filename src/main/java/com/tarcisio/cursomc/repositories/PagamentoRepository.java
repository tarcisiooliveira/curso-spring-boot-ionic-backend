package com.tarcisio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.tarcisio.cursomc.domain.Pagamento;

/*
 * Essa é a classe que faz acesso a dados
 * 
 */

@Repository
public interface PagamentoRepository extends JpaRepository<Pagamento, Integer>{

}
