package com.allan.spr.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.allan.spr.domain.Presenca;

@Repository
public interface PresencaRepository extends JpaRepository<Presenca, Long> {

	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT p FROM Presenca p INNER JOIN p.atividade pa INNER JOIN p.listUsuarioPresenca WHERE to_char(p.dataCadastro, 'dd/MM/yyyy') = :data ORDER BY p.dataCadastro DESC")
	public Page<Presenca> findByPresencaDataCadastro(@Param("data") String data, Pageable pageRequest);

	@Transactional(readOnly = true)
	@Query("SELECT DISTINCT p FROM Presenca p INNER JOIN p.atividade pa INNER JOIN p.listUsuarioPresenca ORDER BY p.dataCadastro DESC")
	public Page<Presenca> findAllPresenca(Pageable pageRequest);

}
