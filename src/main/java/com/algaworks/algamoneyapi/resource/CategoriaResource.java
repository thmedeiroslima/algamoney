package com.algaworks.algamoneyapi.resource;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.algaworks.algamoneyapi.model.Categoria;
import com.algaworks.algamoneyapi.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	/*
	 * @GetMapping public ResponseEntity<?> listar(){ List<Categoria> categorias =
	 * categoriaRepository.findAll(); return !categorias.isEmpty() ?
	 * ResponseEntity.ok(categorias): ResponseEntity.noContent() .build();
	 */

	@GetMapping
	public List<Categoria> listar(){
	
		return categoriaRepository.findAll();
		
	}
	
	@PostMapping
	//@ResponseStatus(HttpStatus.CREATED) Notação desativada por inclusão do tipo de return e nesse return já vai código HhhtpStatus
	public ResponseEntity<Categoria> criar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		//Salva o Objeto no banco de dados e passa os dados do objeto para objeto categoriaSalva
		Categoria categoriaSalva = categoriaRepository.save(categoria);
		
		//Construção URI para inclusão no Header da Response
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(categoriaSalva.getCodigo()).toUri();
		
		//Inclusão URI do novo recurso criado no Header da Response (Inativado)
		//response.setHeader("Location", uri.toASCIIString());
		
		//Retorna o recurso criado no body da Response
		return ResponseEntity.created(uri).body(categoriaSalva);
		
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscarPeloCodigo(@PathVariable Long codigo) {
	Optional<Categoria> categoria = this.categoriaRepository.findById(codigo);
	return categoria.isPresent() ? 
	        ResponseEntity.ok(categoria.get()) : ResponseEntity.notFound().build();
	}

}
