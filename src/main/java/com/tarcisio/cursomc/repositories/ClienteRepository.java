package com.tarcisio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tarcisio.cursomc.domain.Cliente;

/*
 * Essa é a classe que faz acesso a dados
 * 
 */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
