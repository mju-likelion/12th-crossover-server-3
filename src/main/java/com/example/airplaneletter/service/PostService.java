package com.example.airplaneletter.service;

import com.example.airplaneletter.dto.PostDto;
import com.example.airplaneletter.model.Post;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.PostRepository;
import com.example.airplaneletter.response.AllPostsResponseData;
import com.example.airplaneletter.response.DetailedPostResponseData;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    public Page<AllPostsResponseData> getAllPosts(User user, Pageable pageable) {
        // 모든 포스트 조회하기
        Page<Post> postPage = postRepository.findAll(pageable);
        List<AllPostsResponseData> postList = new ArrayList<>();

        for (Post post : postPage) {
            // 이때는 comments 가 없다.
            AllPostsResponseData postData = AllPostsResponseData.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .writer(post.getWriter().getNickname())
                    .createdAt(post.getCreatedAt())
                    .isMyPost(true)
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
                .build();
        return postResponseData;
    }
    public void deletePost(User user, UUID postId) {
        // 포스트 삭제하기.
        Post oldPost = postRepository.findPostById(postId);

        if (isPostOwner(user, oldPost)) {
            this.postRepository.delete(oldPost);
        } else {
            throw new RuntimeException("해당 게시글을 삭제할 수 없습니다.");
        }
    }
    public DetailedPostResponseData getPostDetails(User user, UUID postId){
        // 특정 포스트 조회하기.
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found."));
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
