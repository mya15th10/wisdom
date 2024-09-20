package net.javatrain.train_backend.repository;
import net.javatrain.train_backend.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByUsername(String username); // Trả về Optional
}
