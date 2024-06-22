package com.example.airplaneletter.service;

import com.example.airplaneletter.dto.post.CreatePostDto;
import com.example.airplaneletter.dto.response.comment.CommentListResponseData;
import com.example.airplaneletter.dto.response.comment.CommentResponseData;
import com.example.airplaneletter.errorCode.ErrorCode;
import com.example.airplaneletter.exception.NotFoundException;
import com.example.airplaneletter.exception.UnauthorizedException;
import com.example.airplaneletter.model.Comment;
import com.example.airplaneletter.model.Post;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.PostRepository;
import com.example.airplaneletter.dto.response.post.PostResponseData;
import com.example.airplaneletter.dto.response.post.PostWithCommentResponseData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public Page<PostResponseData> getAllPosts(User user, Pageable pageable) {
        //정렬
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());

        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostResponseData> postList = new ArrayList<>();

        for (Post post : postPage) {
            PostResponseData postData = PostResponseData.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter().getNickname())
                    .createdAt(post.getCreatedAt())
                    .IsMyPost(post.getWriter().getId().equals(user.getId()))
                    .postId(post.getId())
                    .build();

            postList.add(postData);
        }

        return new PageImpl<>(postList, pageable, postPage.getTotalElements());
    }
    public PostWithCommentResponseData createPost(User user, CreatePostDto postDto){
        // 새 포스트 작성하기.
        Post post = Post.builder()
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .writer(user)
                .build();

        postRepository.save(post);
        PostWithCommentResponseData postResponseData = PostWithCommentResponseData.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .nickname(post.getWriter().getNickname())
                .isMyPost(true)
                .build();
        return postResponseData;
    }
    public void deletePost(User user, UUID postId) {
        // 포스트 삭제하기.
        Post oldPost = postRepository.findPostById(postId);
        if(oldPost == null) {
                throw new NotFoundException(ErrorCode.POST_NOT_FOUND, "해당 post 를 찾을 수 없습니다.");
        }

        if (isPostOwner(user, oldPost)) {
            this.postRepository.delete(oldPost);
        } else {
            throw new UnauthorizedException(ErrorCode.FORBIDDEN_USER, "해당 게시글을 삭제할 수 없습니다.");
        }
    }
    public PostWithCommentResponseData getPostDetails(User user, UUID postId){
        // 특정 포스트 조회하기.
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND, "해당 post 를 찾을 수 없습니다."));
        List<CommentResponseData> commentResponseData = new ArrayList<>();
        boolean isMyPost = isPostOwner(user, post);
        // comment -> CommentResponseData로 수정.
        for(Comment c : post.getComments()) {
            commentResponseData.add(CommentResponseData.builder()
                            .author(c.getWriter().getNickname())
                            .createdAt(c.getCreatedAt())
                            .content(c.getContent())
                            .IsMyComment(c.getWriter().getId().equals(user.getId()))
                    .build());
        }
        return PostWithCommentResponseData.builder()
                // comments 를 볼 수 있다.
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getWriter().getNickname())
                .comments(commentResponseData)
                .isMyPost(isMyPost)
                .createdAt(post.getCreatedAt())
                .build();
    }


    private boolean isPostOwner(User user, Post post){
        if(post.getWriter().equals(user)){
            return true;
        }
        else{
            return false;
        }
    }
}
