package com.example.airplaneletter.repository;

import com.example.airplaneletter.model.Term;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TermRepository extends JpaRepository<Term, UUID> {
    Term findTermById(UUID termId);
}
