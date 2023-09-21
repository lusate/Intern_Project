package com.omnilab.templatekotlin.controller.comment;

import com.omnilab.templatekotlin.domain.review.Comment;
import com.omnilab.templatekotlin.domain.review.CommentDto;
import com.omnilab.templatekotlin.domain.review.Review;
import com.omnilab.templatekotlin.service.CommentService;
import com.omnilab.templatekotlin.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


import java.io.*;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final ReviewService reviewService;
    private final CommentService commentService;


    // 답글 등록
    @PostMapping("/item/info/{itemId}/all/{reviewId}/add")
    public ResponseEntity<Long> insertComment(@PathVariable Long reviewId, @RequestBody CommentDto commentDto) throws NotFoundException {

        if (Objects.equals(reviewId, commentDto.getReviewId())) {
            Long commentId = commentService.insert(commentDto);
            return new ResponseEntity<>(commentId, HttpStatus.OK);
        } else throw new IllegalStateException("ID가 달라 답글 등록 불가합니다");

    }


    @PutMapping("/item/info/{itemId}/all/{reviewId}/{commentId}/edit")
    public ResponseEntity<Long> updateComment(@PathVariable Long reviewId,
                                              @PathVariable Long commentId,
                                              @RequestBody CommentDto commentDto) {


        Review review = reviewService.findOne(reviewId);
        Comment comment = commentService.findOne(commentId);

        // 내가 쓴 댓글 comment의 reviewId와 Review에서 reviewId가 같은지를 체크
        log.error("comment : {}", comment.getCommentId());
        log.error("comment : {}", comment.getContent());
        log.error("comment reviewId : {} {}", comment.getReview().getReviewId(), review.getReviewId());


        if (comment.getReview().getReviewId() == review.getReviewId()) {
            commentService.edit(commentDto);
            log.error("getContent : {}", commentDto.getContent());
            log.error("comment reviewId : {}", commentDto.getReviewId());
            log.error("reviewId : {}", review.getReviewId());

            return new ResponseEntity<>(commentId,HttpStatus.OK);
        }

        else throw new IllegalStateException("댓글 입력 불가합니다.");
    }




    //댓글 삭제
    @DeleteMapping("/item/info/{itemId}/all/{reviewId}/{commentId}/remove")
    public ResponseEntity<Long> removeComment(@PathVariable Long commentId){

        commentService.remove(commentId);

        return new ResponseEntity<>(commentId,HttpStatus.OK);
    }

}
