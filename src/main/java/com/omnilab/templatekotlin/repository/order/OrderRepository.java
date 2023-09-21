package com.omnilab.templatekotlin.repository.order;


import com.omnilab.templatekotlin.domain.cart.CartItem;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.order.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryJpql {
    Optional<Order> findById(Long id);

    List<Order> findAll(Long loginUser);

    List<Order> findAllAdmin(OrderSearch orderSearch);
}