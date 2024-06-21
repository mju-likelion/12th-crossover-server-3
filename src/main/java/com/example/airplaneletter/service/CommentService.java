package com.example.airplaneletter.service;

import com.example.airplaneletter.dto.response.comment.CommentResponseData;
import com.example.airplaneletter.dto.comment.CreateCommentDto;
import com.example.airplaneletter.errorCode.ErrorCode;
import com.example.airplaneletter.exception.NotFoundException;
import com.example.airplaneletter.model.Comment;
import com.example.airplaneletter.model.Post;
import com.example.airplaneletter.model.User;
import com.example.airplaneletter.repository.CommentRepository;
import com.example.airplaneletter.repository.PostRepository;
import com.example.airplaneletter.dto.response.comment.CommentListResponseData;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
@Slf4j
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
    public CommentListResponseData getComments(User user, UUID postId, Pageable pageable){
        // 정렬
        pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("createdAt").descending());

        Page<Comment> commentsPage = this.commentRepository.findByPostId(postId, pageable);
        List<CommentResponseData> commentDataList = new ArrayList<>();
        // 특정 post의 모든 comments 가져오기.
        for (Comment comment : commentsPage.getContent()) {
            boolean isMine = comment.getWriter().getId().equals(user.getId());
            CommentResponseData commentData = new CommentResponseData(
                    comment.getContent(),
                    comment.getWriter().getNickname(),
                    isMine,
                    comment.getCreatedAt()
            );
            commentDataList.add(commentData);
        }
        // 해당 commentsList 를 pagination 으로 내보내기
        CommentListResponseData responseData = new CommentListResponseData(
                commentDataList,
                commentsPage.getNumber(),
                commentsPage.getTotalPages(),
                commentsPage.getTotalElements()
        );

        return responseData;
    }
}
