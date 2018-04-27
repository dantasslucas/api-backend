package com.spring.backend.api.services;

import java.util.List;
import java.util.Optional;

import com.spring.backend.api.model.Todo;

public interface TodoService {

	/**
	 * Retorna uma tarefa dado uma Descricao.
	 * 
	 * @param description
	 * @return Optional<Todo>
	 */
	Optional<Todo> buscarPorDescicao(String descricao);
	/**
	 * Retorna uma tarefa por Id
	 * @param id
	 * @return
	 */
	Optional<Todo> buscarPorId(Long id);
	/**
	 * Cadastra uma nova tarefa na base de dados.
	 * 
	 * @param todo
	 * @return Todo
	 */
	Todo persistir (Todo todo);
	
	public List<Todo> buscarLista();
	
	void remover(Long id);
	
}
