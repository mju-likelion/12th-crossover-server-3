package com.example.airplaneletter.service;

import com.example.airplaneletter.dto.PostDto;
import com.example.airplaneletter.errorCode.ErrorCode;
import com.example.airplaneletter.exception.NotFoundException;
import com.example.airplaneletter.exception.UnauthorizedException;
import com.example.airplaneletter.model.Post;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.PostRepository;
import com.example.airplaneletter.response.AllPostsResponseData;
import com.example.airplaneletter.response.DetailedPostResponseData;
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
    public Page<AllPostsResponseData> getAllPosts(User user, Pageable pageable) {
        //정렬
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());

        Page<Post> postPage = postRepository.findAll(pageable);
        List<AllPostsResponseData> postList = new ArrayList<>();

        for (Post post : postPage) {
            AllPostsResponseData postData = AllPostsResponseData.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter().getNickname())
                    .createdAt(post.getCreatedAt())
                    .isMyPost(isPostOwner(user, post))
                    .build();

            postList.add(postData);
        }

        return new PageImpl<>(postList, pageable, postPage.getTotalElements());
    }
    public DetailedPostResponseData createPost(User user, PostDto postDto){
        // 새 포스트 작성하기.
        Post post = Post.builder()
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .writer(user)
                .build();

        postRepository.save(post);
        DetailedPostResponseData postResponseData = DetailedPostResponseData.builder()
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
    public DetailedPostResponseData getPostDetails(User user, UUID postId){
        // 특정 포스트 조회하기.
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.POST_NOT_FOUND, "해당 post 를 찾을 수 없습니다."));
        boolean isMyPost = isPostOwner(user, post);

        return DetailedPostResponseData.builder()
                // comments 를 볼 수 있다.
                .title(post.getTitle())
                .content(post.getContent())
                .nickname(post.getWriter().getNickname())
                .comments(post.getComments())
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
