package com.tarcisio.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.tarcisio.cursomc.domain.Cliente;

/*
 * Essa é a classe que faz acesso a dados
 * 
 */

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
	/**
	 * Faz uma pesquisa personalizada, onde o findBy no nome do metodo é um prefixo
	 * e o Email é o sufixo que representa o mesmo nome do atributo ele retorna do
	 * banco a informação se o atributo passado é um atributo unico ou não
	 * 
	 * @param email
	 * @return
	 */
	@Transactional(readOnly=true)
	Cliente findByEmail(String email);
}
