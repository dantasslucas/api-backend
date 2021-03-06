package com.spring.backend.api.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.spring.backend.api.model.Todo;
import com.spring.backend.api.repositories.TodoRepository;
import com.spring.backend.api.services.TodoService;


@Service
public class TodoServiceImpl implements TodoService{
	
	private static final Logger log = LoggerFactory.getLogger(TodoServiceImpl.class);

	@Autowired
	private TodoRepository tr;
	

	@Override
	public Todo persistir(Todo todo) {
		// TODO Auto-generated method stub
		log.info("Persistindo tarefa {}",todo);
		return this.tr.save(todo);
	}

	@Override
	public Optional<Todo> buscarPorId(Long id) {
		// TODO Auto-generated method stub
		return Optional.ofNullable(tr.findOne(id));
	}
	
	public List<Todo> buscarLista(){
		return tr.findAll(new Sort(new Sort.Order(Sort.Direction.ASC,"id")));
	}

	@Override
	public void remover(Long id) {
		// TODO Auto-generated method stub
		this.tr.delete(id);
	}

	@Override
	public List<Todo> buscarPorDescicao(String descricao) {
		// TODO Auto-generated method stub
		return tr.findByDescription("%"+descricao+"%");
	}

	
}
