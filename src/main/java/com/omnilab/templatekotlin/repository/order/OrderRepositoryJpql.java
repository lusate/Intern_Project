package com.omnilab.templatekotlin.repository.order;


import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.order.Order;

import java.util.List;

public interface OrderRepositoryJpql {

    List<Order> findAll(Long userLogin);

    List<Order> findAllAdmin(OrderSearch orderSearch);
}
