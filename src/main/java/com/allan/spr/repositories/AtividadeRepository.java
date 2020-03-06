package com.allan.spr.repositories;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.allan.spr.domain.Atividade;
import com.allan.spr.domain.enums.TipoAtividade;

@Repository
public interface AtividadeRepository extends JpaRepository< Atividade, Long> {
	
	@Transactional(readOnly=true)
	@Query("SELECT a FROM Atividade a WHERE a.tipo = :tipoAtividade ORDER BY a.id DESC")
	public Page<Atividade> findByAtividadesTipoAtividade(@Param("tipoAtividade") TipoAtividade tipoAtividade, Pageable pageRequest ); 
	
	@Transactional(readOnly=true)
	@Query("SELECT a FROM Atividade a WHERE a.dataCadastro BETWEEN :inicio AND :fim ORDER BY a.id DESC")
	public Page<Atividade> findByAtividadesDataCadastro(@Param("inicio") Date inicio,@Param("fim") Date fim, Pageable pageRequest ); 
	
	@Transactional(readOnly=true)
	@Query("SELECT a FROM Atividade a WHERE a.tipo = :tipoAtividade AND a.dataCadastro BETWEEN :inicio AND :fim ORDER BY a.id DESC")
	public Page<Atividade> findByAtividadesTipoAtividadeDataCadastro(@Param("tipoAtividade") TipoAtividade tipoAtividade,@Param("inicio") Date inicio, @Param("fim") Date fim, Pageable pageRequest ); 

}
