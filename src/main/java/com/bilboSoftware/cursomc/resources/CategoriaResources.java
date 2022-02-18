package com.bilboSoftware.cursomc.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

import com.bilboSoftware.cursomc.domain.Categoria;
import com.bilboSoftware.cursomc.dto.CategoriaDTO;
import com.bilboSoftware.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResources {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "{Id}", method = RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer Id) {

		Categoria obj = service.find(Id);

		return ResponseEntity.ok().body(obj);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@RequestBody Categoria obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}/").buildAndExpand(obj.getId()).toUri();

		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value = "{Id}", method = RequestMethod.PUT)
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer Id) {
		obj.setId(Id);

		obj = service.update(obj);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(value = "{Id}", method = RequestMethod.DELETE)
	public ResponseEntity<Void> delete(@PathVariable Integer Id) {
		service.delete(Id);

		return ResponseEntity.noContent().build();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<CategoriaDTO>> findAll() {

		List<Categoria> objList = service.findAll();

		List<CategoriaDTO> objListDTO = objList.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList());

		return ResponseEntity.ok().body(objListDTO);
	}

	@RequestMapping(value = "/page", method = RequestMethod.GET)
	public ResponseEntity<Page<CategoriaDTO>> findPage(@RequestParam(value = "page", defaultValue = "0") Integer page, 
			                                           @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage, 
			                                           @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy, 
			                                           @RequestParam(value = "direction", defaultValue = "ASC")  String direction) {

		Page<Categoria> objList = service.findPage(page, linesPerPage, orderBy, direction);

		Page<CategoriaDTO> objListDTO = objList.map(obj -> new CategoriaDTO(obj));

		return ResponseEntity.ok().body(objListDTO);
	}
}
