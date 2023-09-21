package com.omnilab.templatekotlin.repository.review;

import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByItem(Item item);

}
