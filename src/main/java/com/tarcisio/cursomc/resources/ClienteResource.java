/* package com.tarcisio.cursomc.Resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tarcisio.cursomc.domain.Cliente;
import com.tarcisio.cursomc.services.ClienteService;

@RestController
@RequestMapping(value = "/clientes") // esse controlador irá responder na pagina web através desse "value"
public class ClienteResource {
	//aqui é a parte de controlador REST
	@Autowired
	ClienteService service; //aqui é a instanncia de acesso aclasse serviço

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {
																 * ResponseEntity encapsula varias informaçoes de uma
																 * requisiçao http para um serviço rest
																 
		//um handler vai interceptar essa classe e tratar as informaççoes com erro
		Cliente obj=service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
}
*/
package com.tarcisio.cursomc.resources;
/**
 * 	Reource é a classe chamada pela requisição da pagina web, ela chama Service que ele então tem acesso ao repository
 *	Pagina->Resource->Service->Repository
 */
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tarcisio.cursomc.domain.Cliente;
import com.tarcisio.cursomc.domain.Cliente;
import com.tarcisio.cursomc.dto.ClienteDTO;
import com.tarcisio.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente> find(@PathVariable Integer id) {
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}
	/**
	 * ReponseEntity porque é o retorno para a tela do navegador retorno de um corpo
	 * vazio para a tela
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDTO, @PathVariable Integer id) {
		Cliente obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	/**
	 * ReponseEntity porque é o retorno para a tela do navegador Os erros são
	 * tratados pela classe essa classe chama ClienteServiço, onde são realizadas
	 * as operações de CRUD retorno de um corpo vazio para a tela
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);//
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<ClienteDTO>> findAll() {// aqui vou

		List<Cliente> listCliente = service.findAll();
		List<ClienteDTO> categoriaDTO = listCliente.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList());;
		return ResponseEntity.ok().body(categoriaDTO);
	}

	/**
	 * value = "/page" é a nova pagina dentro de categoria O metodo será criado com
	 * parametro opcionais, não sendo necessários criar a url com os
	 * atributos/variaveis do path separados por "/", dessa forma, ficará os
	 * parametros opcionais separado a ur por "?pages=0&linesPerPage=24...."
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<ClienteDTO>> findPage(@RequestParam(value = "nome", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<ClienteDTO> listDTO = list.map(obj -> new ClienteDTO(obj));

		return ResponseEntity.ok().body(listDTO);
	}

}