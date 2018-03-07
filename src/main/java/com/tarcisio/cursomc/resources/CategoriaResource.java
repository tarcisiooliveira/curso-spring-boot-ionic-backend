package com.tarcisio.cursomc.resources;
// essa classe contem os metodos get, set, post, put, delete
import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tarcisio.cursomc.domain.Categoria;
import com.tarcisio.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias") // esse controlador irá responder na pagina web através desse "value"
public class CategoriaResource {
	// aqui é a parte de controlador REST
	@Autowired
	CategoriaService service; // aqui é a instanncia de acesso aclasse serviço

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> listar(
			@PathVariable Integer id) {/*
										 * @PathVariable Integer id é a variavel passada na url do navegado
										 * ResponseEntity encapsula varias informaçoes de uma requisiçao http para um
										 * serviço rest
										 */
		// um handler vai interceptar essa classe e tratar as informaççoes com erro
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {// RequestBody faz o json ser coonvertido para java,
																	// podendo acessar os dados para manipulação
		obj = service.insert(obj);// nesse momento é feita a iserção no banco de dados
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();

	}

	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {// ReponseEntity porque é
																								// o retorno para a tela
																								// do navegador
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build(); // retorno de um corpo vazio para a tela
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {// ReponseEntity porque é o retorno
																	// para a tela do navegador
		service.delete(id);//Os erros são tratados pela classe
		//essa classe chama CategoriaServiço, onde são realizadas as operações de CRUD
		return ResponseEntity.noContent().build(); // retorno de um corpo vazio para a tela
	}

}
