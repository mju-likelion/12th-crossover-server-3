package com.example.airplaneletter.service;

import com.example.airplaneletter.dto.CreateCommentDto;
import com.example.airplaneletter.errorCode.ErrorCode;
import com.example.airplaneletter.exception.NotFoundException;
import com.example.airplaneletter.model.Comment;
import com.example.airplaneletter.model.Post;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.CommentRepository;
import com.example.airplaneletter.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 달기
    public void createComment(CreateCommentDto createCommentDto, UUID postId, User user) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND));

        // 새 댓글 생성
        Comment comment = Comment.builder()
                .content(createCommentDto.getContent())
                .post(post)
                .writer(user)
                .build();
        // 해당 포스트에 댓글 추가
        post.getComments().add(comment);
        // 댓글을 작성한 유저에도 댓글 추가
        user.getComments().add(comment);

        this.postRepository.save(post);
        this.commentRepository.save(comment);
    }

    // 댓글 삭제
    public void deleteComment(UUID commentId) {
        this.commentRepository.deleteById(commentId);
    }
}
