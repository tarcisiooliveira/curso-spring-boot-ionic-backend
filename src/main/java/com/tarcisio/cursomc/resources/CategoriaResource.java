package com.tarcisio.cursomc.resources;

// essa classe contem os metodos get, set, post, put, delete
import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.tarcisio.cursomc.domain.Categoria;
import com.tarcisio.cursomc.dto.CategoriaDTO;
import com.tarcisio.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias") // esse controlador irá responder na pagina web através desse "value"
public class CategoriaResource {
	/* aqui é a parte de controlador REST */
	@Autowired
	CategoriaService service; // aqui é a instanncia de acesso aclasse serviço

	/**
	 * @PathVariable Integer id é a variavel passada na url do navegado
	 *               ResponseEntity encapsula varias informaçoes de uma requisiçao
	 *               http para um serviço rest um handler vai interceptar essa
	 *               classe e tratar as informaççoes com erro
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);
	}

	/**
	 * RequestBody faz o json ser coonvertido para java, podendo acessar os dados
	 * para manipulação nesse momento é feita a iserção no banco de dados
	 */
	// @RequestMapping(method = RequestMethod.POST)
	// public ResponseEntity<Void> insert(@Valid @PathVariable CategoriaDTO objDTO)
	// {
	// Categoria obj = service.fromDTO(objDTO);
	// obj = service.insert(obj);
	// System.out.println("Passou aqui.");
	// URI uri =
	// ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
	// return ResponseEntity.created(uri).build();
	//
	// }

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto) {
		Categoria obj = service.fromDTO(objDto);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	/**
	 * ReponseEntity porque é o retorno para a tela do navegador retorno de um corpo
	 * vazio para a tela
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@Valid @RequestBody CategoriaDTO objDTO, @PathVariable Integer id) {
		Categoria obj = service.fromDTO(objDTO);
		obj.setId(id);
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	/**
	 * ReponseEntity porque é o retorno para a tela do navegador Os erros são
	 * tratados pela classe essa classe chama CategoriaServiço, onde são realizadas
	 * as operações de CRUD retorno de um corpo vazio para a tela
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer id) {
		service.delete(id);//
		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {// aqui vou

		List<Categoria> listCategoria = service.findAll();
		List<CategoriaDTO> categoriaDTO = listCategoria.stream().map(obj -> new CategoriaDTO(obj))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(categoriaDTO);
	}

	/**
	 * value = "/page" é a nova pagina dentro de categoria O metodo será criado com
	 * parametro opcionais, não sendo necessários criar a url com os
	 * atributos/variaveis do path separados por "/", dessa forma, ficará os
	 * parametros opcionais separado a ur por "?pages=0&linesPerPage=24...."
	 */
	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "nome", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);
		Page<CategoriaDTO> listDTO = list.map(obj -> new CategoriaDTO(obj));

		return ResponseEntity.ok().body(listDTO);
	}

}
