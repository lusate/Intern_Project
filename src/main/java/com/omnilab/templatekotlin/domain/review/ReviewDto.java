package com.omnilab.templatekotlin.domain.review;

import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDto {
    private Long reviewId;
    private Long itemId;
    private Long memberId;
    private String loginId; // 리뷰를 등록한 사람의 loginId

    private String username;

    private int grade;
    private String text;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private List<Comment> comments;
}
