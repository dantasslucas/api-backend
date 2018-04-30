package com.spring.backend.api.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.spring.backend.api.model.Todo;


@Transactional(readOnly=true)
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
	
	List<Todo> findByDescription(@Param("description") String description);

	
}
