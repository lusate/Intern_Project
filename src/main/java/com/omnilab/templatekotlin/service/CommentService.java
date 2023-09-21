package com.omnilab.templatekotlin.service;

import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.review.Comment;
import com.omnilab.templatekotlin.domain.review.CommentDto;
import com.omnilab.templatekotlin.domain.review.Review;
import com.omnilab.templatekotlin.repository.member.MemberRepository;
import com.omnilab.templatekotlin.repository.review.CommentRepository;
import com.omnilab.templatekotlin.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class CommentService {
    private final MemberRepository memberRepository;
    private final CommentRepository commentRepository;
    private final ReviewRepository reviewRepositroy;

//    @Transactional(readOnly = true)
//    public List<Comment> findAllCommentsInReview(Long reviewId) {
//        Review review = reviewService.findById(reviewId);
//        return commentRepository.findByReview(review);
//    }

    public List<CommentDto> getListReview(Long id) {

        Review review = Review.builder().reviewId(id).build();

        List<Comment> comments = commentRepository.findByReview(review);

        return comments.stream().map(reviewComment -> entityToDto(reviewComment)).collect(Collectors.toList());
    }

    @Transactional
    public Long insert(CommentDto commentDto) {
        Comment comment = dtotoEntity(commentDto);
        log.error("4321 : {}", comment.getContent());

        commentRepository.save(comment);
        return comment.getCommentId();
    }


    @Transactional
    public void edit(CommentDto commentDto) {
        log.error("commentId : {}", commentDto.getCommentId());

        Optional<Comment> commentId = commentRepository.findById(commentDto.getCommentId());


        if (commentId.isPresent()) {
            Comment reviewComment = commentId.get();

            reviewComment.updateContent(commentDto.getContent());

            log.error("comment content : {}", reviewComment.getCommentId());
            log.error("comment content : {}", reviewComment.getContent());

            commentRepository.save(reviewComment);
        }
    }


    @Transactional
    public void remove(Long commentId) {
        commentRepository.deleteById(commentId);
    }


    Comment dtotoEntity(CommentDto commentDto) {
        Comment reviewComment = Comment.builder()
                .commentId(commentDto.getCommentId())
                .member(Member.builder().id(commentDto.getMemberId()).build())
                .review(Review.builder().reviewId(commentDto.getReviewId()).build())
                .content(commentDto.getContent())
                .build();

        return reviewComment;
    }




    CommentDto entityToDto(Comment comment){

        CommentDto reviewCommentDto = CommentDto.builder()
                .commentId(comment.getCommentId())
                .memberId(comment.getMember().getId())
                .reviewId(comment.getReview().getReviewId())
                .content(comment.getContent())
                .loginId(comment.getLoginId())
                .createdDate(String.valueOf(comment.getCreatedDate()))
                .modifiedDate(String.valueOf(comment.getLastModified()))
                .build();

        return reviewCommentDto;
    }




    public Comment findOne(Long commentId) {
        return commentRepository.findById(commentId).get();
    }
}
