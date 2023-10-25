package com.fubon.ecplatformapi.token;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByToken(String token);

    @Query("SELECT t FROM Token t WHERE t.id = (SELECT MAX(t2.id) FROM Token t2)")
    Token findLatestToken();

}
