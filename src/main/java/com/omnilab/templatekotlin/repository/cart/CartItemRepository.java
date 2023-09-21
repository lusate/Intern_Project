package com.omnilab.templatekotlin.repository.cart;

import com.omnilab.templatekotlin.domain.cart.CartItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByMemberIdAndItemId(Long memberId, Long itemId);

    Page<CartItem> findAllByMemberId(Long memberId, Pageable pageable);

    CartItem findByCartIdAndItemId(Long cartId, Long itemId);

    @Modifying // @Query 를 통해 작성된 INSERT, UPDATE, DELETE(SELECT 제외) 쿼리에서 사용되는 애노테이션
    @Query("delete from CartItem c where c.id in :ids")
    void deleteAllByIds(@Param("ids") List<Long> ids);
}
