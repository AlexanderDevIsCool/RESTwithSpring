package ua.l5.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.l5.service.models.Token;

import java.util.Optional;

public interface TokensRepository extends JpaRepository<Token, Long> {
    Optional<Token> findOneByValue(String value);
}
