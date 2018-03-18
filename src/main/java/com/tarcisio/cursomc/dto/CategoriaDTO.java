package com.tarcisio.cursomc.dto;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.tarcisio.cursomc.domain.Categoria;

/**
 * Porque as regras de validação estão nessa classe? Poruqe como Nelio Alves
 * disse, o framework exige que as validações sejam colocadas na classe de
 * instanciaçao e se a classe, faz sentido porque precisam serem validandos
 * antes de enviar, a classe irá trafegar com os dados, e também precisa de segurança na
 * requisição 
 * 
 */
public class CategoriaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private Integer id;

	@NotEmpty(message = "Preenchimento obrigatorio")
	@Length(min = 5, max = 80, message = "O tamanho tem que ser entre 5 e 80")
	private String nome;
	@Email
	
	public CategoriaDTO() {

	}

	public CategoriaDTO(Categoria categoria) {
		id = categoria.getId();
		nome = categoria.getNome();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
