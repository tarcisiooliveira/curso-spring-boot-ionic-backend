package com.tarcisio.cursomc.Resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tarcisio.cursomc.domain.Categoria;
import com.tarcisio.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias") // esse controlador irá responder na pagina web através desse "value"
public class CategoriaResource {
	//aqui é a parte de controlador REST
	@Autowired
	CategoriaService service; //aqui é a instanncia de acesso aclasse serviço

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {/*
																 * ResponseEntity encapsula varias informaçoes de uma
																 * requisiçao http para um serviço rest
																 */
		Categoria obj=service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
}
