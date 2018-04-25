package com.spring.backend.api.dtos;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class TodoDto {
	private Long id;
	private String description;
	
	public TodoDto() {}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotEmpty(message="Descrição não pode estar vazia")
	@Length(min =3, max=250,message="Descição deve conter entre 3 e 200 caracteres")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
