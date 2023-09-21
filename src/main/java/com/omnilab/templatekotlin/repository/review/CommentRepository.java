package com.omnilab.templatekotlin.repository.review;

import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.review.Comment;
import com.omnilab.templatekotlin.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReview(Review review);



    Optional<Comment> findByMemberAndReview(Member member, Review review);

}
