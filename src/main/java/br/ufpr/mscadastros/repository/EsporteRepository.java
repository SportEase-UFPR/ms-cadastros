package br.ufpr.mscadastros.repository;

import br.ufpr.mscadastros.model.entity.Esporte;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface EsporteRepository extends JpaRepository<Esporte, Long> {
    Optional<Esporte> findByNome(String nome);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tb_espaco_esportivo_esporte WHERE id_esporte = ?1", nativeQuery = true)
    void excluirEspacoEsportivoEsporte(Long idEsporte);
}
