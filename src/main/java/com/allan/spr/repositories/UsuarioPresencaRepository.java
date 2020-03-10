package com.allan.spr.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.allan.spr.domain.UsuarioPresenca;

@Repository
public interface UsuarioPresencaRepository extends JpaRepository< UsuarioPresenca, Long> {
	
	@Transactional(readOnly=true)
	@Query("SELECT up FROM UsuarioPresenca up WHERE to_char(up.dataCadastro, 'dd/MM/yyyy') = :data ORDER BY up.id DESC")
	public Page<UsuarioPresenca> findByUsuarioPresencaDataCadastro(@Param("data") String data, Pageable pageRequest ); 
	
	
	@Transactional(readOnly=true)
	@Query("SELECT up FROM UsuarioPresenca up  INNER JOIN  up.usuario upu WHERE  upu.id= :idUsuario ORDER BY up.id ")
	public Page<UsuarioPresenca> findByUsuarioPresencaFromUsuario(@Param("idUsuario") Long idUsuario, Pageable pageRequest ); 
	
	

}
