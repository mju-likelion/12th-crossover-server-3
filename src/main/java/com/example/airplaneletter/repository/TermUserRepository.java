package com.example.airplaneletter.repository;

import com.example.airplaneletter.model.TermUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TermUserRepository extends JpaRepository<TermUser, UUID> {
}
