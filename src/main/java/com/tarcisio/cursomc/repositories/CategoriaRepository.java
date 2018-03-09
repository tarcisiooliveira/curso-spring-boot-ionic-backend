package com.tarcisio.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tarcisio.cursomc.domain.Categoria;

/*
 * Essa é a classe que faz acesso a dados
 * 
 */

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

	

}
