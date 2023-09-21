package com.omnilab.templatekotlin.service;

import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.review.Review;
import com.omnilab.templatekotlin.domain.review.ReviewDto;
import com.omnilab.templatekotlin.repository.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@EnableCaching
@Slf4j
public class ReviewService {

    private final ReviewRepository reviewRepository;


    public List<ReviewDto> getListItem(Long id) {

        Item item = Item.builder().id(id).build();

        List<Review> result = reviewRepository.findByItem(item);

        return result.stream().map(itemReview -> entityToDto(itemReview)).collect(Collectors.toList());
    }



    public Long register(ReviewDto reviewDto) { //review 등록
        log.error("1234 : {}", reviewDto.getGrade());
        log.error("review_id : {}", reviewDto.getReviewId());
        log.error("item_id : {}", reviewDto.getItemId());
        log.error("member_id : {}", reviewDto.getMemberId());

        Review review = dtoToEntity(reviewDto);
        log.error("member_id : {}", review.getMember().getId());


        reviewRepository.save(review);

        return review.getReviewId();
    }



    public void edit(ReviewDto reviewDto) {
        log.error("reviewId : {}", reviewDto.getReviewId());

        Optional<Review> reviewId = reviewRepository.findById(reviewDto.getReviewId());
//        Member findMember = memberService.findOne(memberId);


        if (reviewId.isPresent()) {
            Review itemReview = reviewId.get();

            itemReview.changeGrade(reviewDto.getGrade()); // 별점이랑 텍스트 변경
            itemReview.changeText(reviewDto.getText());


            reviewRepository.save(itemReview);
        }

    }

    public void updateText(Long reviewId, String text) {
        Review review = reviewRepository.findById(reviewId).get();

        review.setReviewId(reviewId);
        review.setText(text);
    }




    public void remove(Long reviewId) {
        reviewRepository.deleteById(reviewId);
    }


    public Review findOne(Long reviewId) {
        return reviewRepository.findById(reviewId).get();
    }


    //BoardDTO를 Board 엔티티 타입으로 변환
    Review dtoToEntity(ReviewDto reviewDto){

        Review itemReview = Review.builder()
                .reviewId(reviewDto.getReviewId())
                .item(Item.builder().id(reviewDto.getItemId()).build())
                .member(Member.builder().id(reviewDto.getMemberId()).build())
                .grade(reviewDto.getGrade())
                .text(reviewDto.getText())
                .comments(reviewDto.getComments())
                .build();

        return itemReview;
    }



    ReviewDto entityToDto(Review review){

        ReviewDto itemReviewDTO = ReviewDto.builder()
                .reviewId(review.getReviewId())
                .itemId(review.getItem().getId())
                .memberId(review.getMember().getId())
                .loginId(review.getMember().getLoginId())
                .username(review.getMember().getUsername())
                .grade(review.getGrade())
                .text(review.getText())
                .createdDate(review.getCreatedDate())
                .lastModifiedDate(review.getLastModified())
                .build();

        return itemReviewDTO;
    }





    public Review findById(Long id) {
        Optional<Review> byId = reviewRepository.findById(id);
        return byId.orElseThrow(()-> new RuntimeException("없는 review입니다."));
    }
}
