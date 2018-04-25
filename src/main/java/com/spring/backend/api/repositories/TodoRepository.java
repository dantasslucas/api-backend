package com.spring.backend.api.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.backend.api.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {
	
	@Transactional(readOnly=true)
	Todo findByDescription(String description);
	@Transactional(readOnly=true)
	Todo findAll(Todo todo);
}
