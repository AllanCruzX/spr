package com.allan.spr.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.allan.spr.domain.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	
	@Transactional(readOnly=true)
	Usuario findByEmail(String nome);
	
	@Transactional(readOnly=true)
	@Query("SELECT usu FROM Usuario usu INNER JOIN  usu.perfis usuP LEFT JOIN  usu.endereco WHERE usuP = :perfilAluno ORDER BY usu.nome")
	public Page<Usuario> findAllAlunos(@Param("perfilAluno") Integer perfilAluno ,Pageable pageRequest ); 
	

	@Transactional(readOnly=true)
	@Query("SELECT usu FROM Usuario usu INNER JOIN  usu.perfis usuP LEFT JOIN  usu.endereco WHERE usuP = :perfilAluno AND UPPER(TRIM(usu.nome)) LIKE UPPER(:nome)")
	public Page<Usuario> findAllAlunosNome(@Param("perfilAluno") Integer perfilAluno ,Pageable pageRequest , @Param("nome") String  nome ); 
	
	@Transactional(readOnly=true)
	@Query("SELECT usu FROM Usuario usu INNER JOIN  usu.perfis usuP LEFT JOIN  usu.endereco WHERE usuP = :perfilAluno AND UPPER(TRIM(usu.nomeResponsavel)) LIKE UPPER(:nomeResponsavel) ")
	public Page<Usuario> findAllAlunosNomeResponsavel(@Param("perfilAluno") Integer perfilAluno ,Pageable pageRequest ,  @Param("nomeResponsavel") String nomeResponsavel ); 
	
	@Transactional(readOnly=true)
	@Query("SELECT usu FROM Usuario usu INNER JOIN  usu.perfis usuP LEFT JOIN  usu.endereco WHERE usuP = :perfilAluno AND UPPER(TRIM(usu.nome)) LIKE UPPER(:nome) AND UPPER(TRIM(usu.nomeResponsavel)) LIKE UPPER(:nomeResponsavel) ")
	public Page<Usuario> findAllAlunosNomeNomeResponsavel(@Param("perfilAluno") Integer perfilAluno ,Pageable pageRequest, @Param("nome") String nome ,  @Param("nomeResponsavel") String nomeResponsavel ); 
	
}


