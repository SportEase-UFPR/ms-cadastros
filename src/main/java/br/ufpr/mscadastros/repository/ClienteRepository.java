package br.ufpr.mscadastros.repository;

import br.ufpr.mscadastros.model.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    Boolean existsByEmail(String email);
    Boolean existsByCpf(String cpf);
    Cliente findClienteByCpfOrEmail(String cpf, String email);
    Optional<Cliente> findByIdUsuario(Long usuarioId);
    Optional<Cliente> findByEmail(String email);
}
