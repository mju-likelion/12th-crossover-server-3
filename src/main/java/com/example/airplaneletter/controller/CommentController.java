package com.example.airplaneletter.controller;

import com.example.airplaneletter.authentication.user.AuthenticatedUser;
import com.example.airplaneletter.dto.comment.CreateCommentDto;
import com.example.airplaneletter.dto.ResponseDto;
import com.example.airplaneletter.dto.response.comment.CommentResponseData;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.dto.response.comment.CommentListResponseData;
import com.example.airplaneletter.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletionException;

@RestController
@AllArgsConstructor
public class CommentController {

    public final CommentService commentService;

    // 댓글 달기
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<Void>> createComment(@RequestBody CreateCommentDto createCommentDto,
                                                           @PathVariable UUID postId,
                                                           @AuthenticatedUser User user) {
        this.commentService.createComment(createCommentDto, postId, user);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "Comment created"), HttpStatus.OK);
    }

    // 댓글 삭제
    @DeleteMapping("/posts/comments/{commentId}")
    public ResponseEntity<ResponseDto<Void>> deleteComment(@AuthenticatedUser User user, @PathVariable UUID commentId) {
        this.commentService.deleteComment(user, commentId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "Comment deleted"), HttpStatus.OK);
    }

    // 전체 댓글 조회
    @GetMapping("/posts/{postId}/comments")
    public ResponseEntity<ResponseDto<Page<CommentResponseData>>> getComments(@AuthenticatedUser User user, @PathVariable UUID postId,
                                                                            @RequestParam(defaultValue = "0") int page,
                                                                            @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page, size);
        Page<CommentResponseData> commentResponseData = commentService.getComments(user, postId, pageable);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "OK", commentResponseData), HttpStatus.OK);
    }
}
