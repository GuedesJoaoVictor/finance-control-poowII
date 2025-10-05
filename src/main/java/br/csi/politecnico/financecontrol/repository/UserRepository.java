package br.csi.politecnico.financecontrol.repository;

import br.csi.politecnico.financecontrol.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByUuid(UUID uuid);
}
