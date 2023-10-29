package br.ufpr.mscadastros.repository;

import br.ufpr.mscadastros.model.entity.Administrador;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AdministradorRepository extends JpaRepository<Administrador, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);
    Administrador findAdministradorByCpfOrEmail(String cpf, String email);
    Optional<Administrador> findByIdUsuario(Long usuarioId);
}
