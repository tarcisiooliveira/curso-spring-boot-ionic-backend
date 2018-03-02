 package com.tarcisio.cursomc.resources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tarcisio.cursomc.domain.Pedido;
import com.tarcisio.cursomc.services.PedidoService;

@RestController
@RequestMapping(value = "/pedidos") // esse controlador irá responder na pagina web através desse "value"
public class PedidoResource {
	//aqui é a parte de controlador REST
	@Autowired
	PedidoService service; //aqui é a instanncia de acesso aclasse serviço

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> listar(@PathVariable Integer id) {/*
																 * ResponseEntity encapsula varias informaçoes de uma
																 * requisiçao http para um serviço rest
																 */
		//um handler vai interceptar essa classe e tratar as informaççoes com erro
		Pedido obj=service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
}
