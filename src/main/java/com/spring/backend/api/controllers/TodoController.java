package com.spring.backend.api.controllers;

import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.backend.api.dtos.TodoDto;
import com.spring.backend.api.model.Todo;
import com.spring.backend.api.response.Response;
import com.spring.backend.api.services.TodoService;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins="*")
public class TodoController {
		
	private static final Logger log = LoggerFactory.getLogger(TodoController.class);
	
	
	@Autowired
	private TodoService todoService;
	
	
	public TodoController() {}
	
	/**
	 * Retorna Lista completa
	 * @return
	 */
	@GetMapping()
	public ResponseEntity<Response<List<TodoDto>>> getAll(){
		Response<List<TodoDto>> response = new Response<List<TodoDto>>();
		List<Todo> lista = todoService.buscarLista();
		List<TodoDto> retorno = new ArrayList<>();
		for (Todo todo : lista) {
			retorno.add(this.converterTodoDto(todo));
		}
		response.setData(retorno);
		return ResponseEntity.ok(response);
	}
	
	/**
	 * Retorna uma tarefa dado id
	 * 
	 * @param description
	 * @return ResponseEntity<Response<TodoDto>>
	 */
	@GetMapping(value="/{description}")
	public ResponseEntity<Response<List<TodoDto>>> buscarPorDescricao(
			@PathVariable("description") String description){
		log.info("Buscando tarefa por Descricao {}", description);
		Response<List<TodoDto>> response = new Response<List<TodoDto>>();
		List<Todo> lista = todoService.buscarPorDescicao(description);
		List<TodoDto> retorno = new ArrayList<>();
		for (Todo todo : lista) {
			retorno.add(this.converterTodoDto(todo));
		}
		response.setData(retorno);
		return ResponseEntity.ok(response);
		
	}

	/**
	 * 
	 * Adcionando nova tarefa
	 * 
	 * @param todoDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PostMapping()
	public ResponseEntity<Response<TodoDto>> adicionar(@Valid @RequestBody TodoDto todoDto,
			BindingResult result) throws NoSuchAlgorithmException{
		log.info("Adcionando tarefa");
		Response<TodoDto> response = new Response<TodoDto>();
		Todo todo = this.converterTodoDtoParaTodo(todoDto,result);
		this.todoService.persistir(todo);
		response.setData(this.converterTodoDto(todo));
		return ResponseEntity.ok(response);
	}
	/**
	 * Atualiza dados de uma tarefa
	 * 
	 * @param id
	 * @param todoDto
	 * @param result
	 * @return
	 * @throws ParseException
	 */
	@PutMapping(value="/{id}")
	public ResponseEntity<Response<TodoDto>> autalizar(@PathVariable("id") Long id,
			@Valid @RequestBody TodoDto todoDto, BindingResult result ) throws ParseException{
		log.info("Atualiza Descrição pelo id {}", todoDto.toString());
		Response<TodoDto> response = new Response<TodoDto>();
		todoDto.setId(id);
		Todo todo = this.converterTodoDtoParaTodoPut(todoDto, result);
		if(result.hasErrors()) {
			log.error("Erro validando tarefa: {}",result.getAllErrors());
			result.getAllErrors().forEach(error -> response.getErrors().add(error.getDefaultMessage()));
		}
		todo = this.todoService.persistir(todo);
		response.setData(this.converterTodoDto(todo));
		return ResponseEntity.ok(response);
	}
	private Todo converterTodoDtoParaTodoPut(TodoDto todoDto, BindingResult result) {
		// TODO Auto-generated method stub
		Todo todo = new Todo();
		todo.setId(todoDto.getId());
		todo.setDescription(todoDto.getDescription());
		todo.setDone(todoDto.isDone());
		return todo;
	}

	@DeleteMapping(value="/{id}")
	public ResponseEntity<Response<Long>> remover(@PathVariable("id") Long id) throws NoSuchAlgorithmException{
		log.info("Adcionando tarefa");
		Response<Long> response = new Response<>();
		this.todoService.remover(id);
		response.setData(id);
		return ResponseEntity.ok(response);
	}
	/**
	 * Popula modelo com dados da Dto
	 * 
	 * @param todoDto
	 * @param result
	 * @return
	 */
	private Todo converterTodoDtoParaTodo(TodoDto todoDto, BindingResult result) {
		// TODO Auto-generated method stub
		Todo todo = new Todo();
		todo.setDescription(todoDto.getDescription());
		return todo;
	}

	/**
	 * Pupula um DTO com os dados do Todo
	 * @param todo
	 * @return
	 */
	private TodoDto converterTodoDto(Todo todo) {
		// TODO Auto-generated method stub
		TodoDto todoDto = new TodoDto();
//		todoDto.setCreatedat(this.dateFormat.format(todo.getCreatedAt()));
		todoDto.setDescription(todo.getDescription());
		todoDto.setDone(todo.isDone());
		todoDto.setId(todo.getId());
		return todoDto;
	}

}
