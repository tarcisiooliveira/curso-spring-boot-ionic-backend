package com.tarcisio.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.tarcisio.cursomc.domain.Categoria;
import com.tarcisio.cursomc.repositories.CategoriaRepository;
import com.tarcisio.cursomc.services.exceptions.DataIntegrityException;
import com.tarcisio.cursomc.services.exceptions.ObjectNotFoundException;

@Service // para informar que é da categoria service
public class CategoriaService {

	@Autowired // essa desgraça faz instancia em tempo de execução a classe de acesso ao
				// repositorio
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);// se o id existe, retorna o objeto, senão, retorna nulo
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo " + Categoria.class.getName());// nome da classe no
																								// final com o parametro
																								// para o erro
		}
		return obj;
	}

	public Categoria insert(Categoria obj) {
		obj.setId(null);// garante que o objeto que vai ser inserido é unico, caso houvesse uma
						// informação no campo, a informaçao do id seria atualizado no bano
		return repo.save(obj);
	}

	public Categoria update(Categoria obj) {
		find(obj.getId());
		return repo.save(obj);
	}

	public void delete(Integer id) {

		find(id);
		try {
			repo.delete(id);	
		}catch (DataIntegrityViolationException e){
			throw new DataIntegrityException("Não é possível exclui uma categoria que possui produto");
		}
		
	}

}
