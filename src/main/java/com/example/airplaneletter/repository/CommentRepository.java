package com.example.airplaneletter.repository;

import com.example.airplaneletter.model.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByPostId(UUID postId, Pageable pageable);
    Comment findCommentById(UUID commentId);
}
