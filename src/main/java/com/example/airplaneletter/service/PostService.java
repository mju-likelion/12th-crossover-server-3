package com.example.airplaneletter.service;

import com.example.airplaneletter.dto.PostDto;
import com.example.airplaneletter.model.Post;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.PostRepository;
import com.example.airplaneletter.response.PostListResponseData;
import com.example.airplaneletter.response.PostResponseData;
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
    public Page<PostListResponseData> getAllPosts(User user, Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostListResponseData> postList = new ArrayList<>();

        for (Post post : postPage) {
            PostListResponseData postData = PostListResponseData.builder()
                    .title(post.getTitle())
                    .content(post.getContent())
                    .nickname(post.getWriter().getNickname())
                    .createdAt(post.getCreatedAt())
                    .isMyPost(post.getWriter().getId().equals(user.getId()))
                    .build();

            postList.add(postData);
        }

        return new PageImpl<>(postList, pageable, postPage.getTotalElements());
    }
    public PostResponseData createPost(User user, PostDto postDto){
        Post post = Post.builder()
                .content(postDto.getContent())
                .title(postDto.getTitle())
                .writer(user)
                .build();

        postRepository.save(post);
        PostResponseData postResponseData = PostResponseData.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .writer(post.getWriter().getNickname())
                .build();
        return postResponseData;
    }
    public void deletePost(User user, UUID postId) {
        Post oldPost = postRepository.findPostById(postId);

        if (isPostOwner(user, oldPost)) {
            this.postRepository.delete(oldPost);
        } else {
            throw new RuntimeException("해당 게시글을 삭제할 수 없습니다.");
        }
    }
    public PostResponseData getPostDetails(User user, UUID postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found."));
        boolean isMyPost = isPostOwner(user, post);

        return PostResponseData.builder()
                .title(post.getTitle())
                .content(post.getContent())
                .writer(post.getWriter().getNickname())
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