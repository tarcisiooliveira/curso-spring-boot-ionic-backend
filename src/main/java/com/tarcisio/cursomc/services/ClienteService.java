/*package com.tarcisio.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tarcisio.cursomc.domain.Cliente;
import com.tarcisio.cursomc.repositories.ClienteRepository;
import com.tarcisio.cursomc.services.exceptions.ObjectNotFoundException;

@Service // para informar que é da categoria service
public class ClienteService {

	@Autowired // essa desgraça faz instancia em tempo de execução a classe de acesso ao
				// repositorio
	private ClienteRepository repo;

	public Cliente buscar(Integer id) {
		Cliente obj = repo.findOne(id);// se o id existe, retorna o objeto, senão, retorna nulo
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo " + Cliente.class.getName());// nome da classe no
																								// final com o parametro
																								// para o erro
		}
		return obj;
	}
}
*/
// Reource é a classe chamada pela requisição da pagina web, ela chama Service 
package com.tarcisio.cursomc.services;
/**
 * 	Reource é a classe chamada pela requisição da pagina web, ela chama Service que ele então tem acesso ao repository
 *	Pagina->Resource->Service->Repository
 */
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.tarcisio.cursomc.domain.Cliente;
import com.tarcisio.cursomc.domain.Cliente;
import com.tarcisio.cursomc.dto.ClienteDTO;
import com.tarcisio.cursomc.repositories.ClienteRepository;
import com.tarcisio.cursomc.services.exceptions.DataIntegrityException;
import com.tarcisio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj=find(obj.getId());//para garantir que o objeto existe
		updateData(newObj, obj);
		
		return repo.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		
		
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
		
	}

	public void delete(Integer id) {

		find(id);
		try {
			repo.delete(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possível exclui porque há entidades relacionadas");
		}

	}

	public List<Cliente> findAll() {
		return repo.findAll();
	}

	/**
	 * retorna uma pagina de categorias encapsula operações e informações
	 * 
	 * @page retorna o numero da pagina, 0 é a pagina inicial
	 * @linesPerPage já é autoexplicativo
	 * @orderBy é o campo que vai ordenar a tabela
	 * @direction é ascendente ou descendente
	 * 
	 *            Porque
	 */
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);

		return repo.findAll(pageRequest);

	}

	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(),obj.getNome(),obj.getEmail(),null,null);
	}
}