package com.example.airplaneletter.controller;

import com.example.airplaneletter.authentication.user.AuthenticatedUser;
import com.example.airplaneletter.dto.post.CreatePostDto;
import com.example.airplaneletter.dto.ResponseDto;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.dto.response.post.PostResponseData;
import com.example.airplaneletter.dto.response.post.PostWithCommentResponseData;
import com.example.airplaneletter.service.PostService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/posts")
@AllArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<ResponseDto<Page<PostResponseData>>> getAllPosts(
            @AuthenticatedUser User user,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<PostResponseData> posts = postService.getAllPosts(user, pageable);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "ok", posts), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseDto<PostWithCommentResponseData>> createPost(@AuthenticatedUser User user, @RequestBody CreatePostDto postDto) {
        System.out.println("create post");
        PostWithCommentResponseData postResponseData = postService.createPost(user, postDto);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "ok", postResponseData), HttpStatus.CREATED);
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ResponseDto<Void>> deletePost(@AuthenticatedUser User user, @PathVariable UUID postId) {
        postService.deletePost(user, postId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "ok"), HttpStatus.OK);

    }
    @GetMapping("/{postId}")
    public ResponseEntity<ResponseDto<PostWithCommentResponseData>> getPostDetails(@AuthenticatedUser User user, @PathVariable UUID postId) {
        PostWithCommentResponseData postResponseData = postService.getPostDetails(user, postId);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "ok", postResponseData), HttpStatus.OK);
    }
}