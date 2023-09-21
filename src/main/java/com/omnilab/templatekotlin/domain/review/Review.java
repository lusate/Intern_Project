package com.omnilab.templatekotlin.domain.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnilab.templatekotlin.domain.BaseEntity;
import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.member.Member;
import lombok.*;

import static javax.persistence.FetchType.LAZY;
import javax.persistence.*;
import java.util.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Review extends BaseEntity {
    @Id
    @GeneratedValue
    private Long reviewId;

    @ManyToOne(fetch = LAZY)
    private Item item;

    @ManyToOne(fetch = LAZY)
    private Member member;

//    private String loginId;

    private int grade; //별점 개수

    private String text; //리뷰 작성


    // 리뷰 1 : 댓글 N
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    public void changeGrade(int grade) {
        this.grade = grade;
    }

    public void changeText(String text) {
        this.text = text;
    }

}
