package com.example.airplaneletter.controller;

import com.example.airplaneletter.authentication.user.AuthenticatedUser;
import com.example.airplaneletter.dto.CreateCommentDto;
import com.example.airplaneletter.dto.CreateUserDto;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@AllArgsConstructor
public class CommentController {

    public final CommentService commentService;

    // 댓글 달기
    @PostMapping("/posts/{postId}/comments")
    public void createComment(@RequestBody CreateCommentDto createCommentDto,
                              @PathVariable UUID postId,
                              @AuthenticatedUser User user) {
        this.commentService.createComment(createCommentDto, postId, user);
    }

    // 댓글 삭제
    @DeleteMapping("/posts/comments/{commentId}")
    public void deleteComment(@PathVariable UUID commentId) {
        this.commentService.deleteComment(commentId);
    }
}
