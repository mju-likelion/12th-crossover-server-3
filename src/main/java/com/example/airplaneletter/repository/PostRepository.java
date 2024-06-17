package com.example.airplaneletter.repository;

import com.example.airplaneletter.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    Post findPostById(UUID postId);
}
