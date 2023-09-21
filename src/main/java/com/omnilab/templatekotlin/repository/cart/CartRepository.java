package com.omnilab.templatekotlin.repository.cart;

import com.omnilab.templatekotlin.domain.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long>{
    Cart findByMemberId(Long memberId);
}
