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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.tarcisio.cursomc.domain.Cliente;
import com.tarcisio.cursomc.services.ClienteService;

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {
	
	@Autowired
	private ClienteService service;
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {
		Cliente obj = service.buscar(id);
		return ResponseEntity.ok().body(obj);
	}
}