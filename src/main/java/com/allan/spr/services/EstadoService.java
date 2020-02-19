package com.allan.spr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allan.spr.domain.Estado;
import com.allan.spr.repositories.EstadoRepository;



@Service

public class EstadoService {

	@Autowired
	private EstadoRepository repo;

	public List<Estado> findAll() {
		return  repo.findAllByOrderByNome();
	}

}
