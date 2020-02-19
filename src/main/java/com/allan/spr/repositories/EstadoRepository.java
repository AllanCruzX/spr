package com.allan.spr.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.allan.spr.domain.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer> {
	
	@Transactional(readOnly=true)//Uma outra propriedade muito importante no uso de transações é o read-only, identificando que determinada transação não pode realizar operações de escrita ou alterações, apenas leitura.
	public List<Estado> findAllByOrderByNome();

}
