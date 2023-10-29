package br.ufpr.mscadastros.repository;

import br.ufpr.mscadastros.model.entity.EspacoEsportivo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


public interface EspacoEsportivoRepository extends JpaRepository<EspacoEsportivo, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tb_espaco_esportivo_esporte WHERE id_espaco_esportivo = ?1", nativeQuery = true)
    void excluirEspacoEsportivoEsporte(Long idEspacoEsportivo);
}
