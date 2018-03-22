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

import com.tarcisio.cursomc.domain.Cidade;
import com.tarcisio.cursomc.domain.Cliente;
import com.tarcisio.cursomc.domain.Endereco;
import com.tarcisio.cursomc.domain.enums.TipoCliente;
import com.tarcisio.cursomc.dto.ClienteDTO;
import com.tarcisio.cursomc.dto.ClienteNewDTO;
import com.tarcisio.cursomc.repositories.CidadeRepository;
import com.tarcisio.cursomc.repositories.ClienteRepository;
import com.tarcisio.cursomc.repositories.EnderecoRepository;
import com.tarcisio.cursomc.services.exceptions.DataIntegrityException;
import com.tarcisio.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;

	public Cliente find(Integer id) {
		System.out.println("APassou aqui");
		Cliente obj = repo.findOne(id);
		System.out.println("APassou aqui TMB" + "");
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName());
		}
		return obj;
	}

	// Metodo POST
	/**
	 * garante que o objeto que vai ser inserido é unico, caso houvesse uma
	 * informação no campo, a informaçao do id seria atualizado no banco
	 * 
	 * @param obj
	 *            do tipo Cliente
	 * @return A confirmação do objeto salvo no banco
	 */
	public Cliente insert(Cliente obj) {
		obj.setId(null);
		obj=repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());
		return obj;
	}

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());// para garantir que o objeto existe, o objeto instaciado está monitorado
											// pelo JPA
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
			System.out.println("passou por aqui");
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
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO obj) {
		
		Cliente cliente = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(),
				TipoCliente.toEnum(1));
		System.out.println("texto:======================================="+obj.getCidadeId());
		Cidade cidade = cidadeRepository.findOne(obj.getCidadeId());
		System.out.println("texto:======================================="+obj.getCidadeId());
		Endereco endereco = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(),
				obj.getBairro(), obj.getCep(), cliente, cidade);
		cliente.getEnderecos().add(endereco);
		cliente.getTelefones().add(obj.getTelefone1());
		if (obj.getTelefone2() != null) {
			cliente.getTelefones().add(obj.getTelefone2());
		}
		if (obj.getTelefone3() != null) {
			cliente.getTelefones().add(obj.getTelefone3());
		}
		
		return cliente;
	}

}