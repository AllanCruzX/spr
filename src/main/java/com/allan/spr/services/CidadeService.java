package com.allan.spr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allan.spr.domain.Cidade;
import com.allan.spr.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository repo;

	public List<Cidade> findCidades(Long estado_id) {
		return repo.findCidades(estado_id);

	}

}
