package com.omnilab.templatekotlin.domain.review;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnilab.templatekotlin.domain.BaseEntity;
import com.omnilab.templatekotlin.domain.member.Member;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Comment extends BaseEntity { // 답글
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long commentId;


    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Member member;



    @JoinColumn(name = "review_id", nullable = false)
    @ManyToOne(fetch = LAZY)
    private Review review;


    @Column(nullable = false)
    private String content; // 답글 내용



    private String loginId;



    public void updateContent(String content) {
        this.content = content;
    }

}



//    BaseEntity에 있는데 createDate를 쓴 이유
//    댓글의 입장에선 게시글과 사용자는 다대일 관계이므로 @ManyToOne이 된다.
//    한 개의 게시글에는 여러 개의 댓글이 있을 수 있고, 한 명의 사용자는 여러 개의 댓글을 작성할 수 있다.