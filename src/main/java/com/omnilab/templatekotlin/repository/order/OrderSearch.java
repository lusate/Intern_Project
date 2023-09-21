package com.omnilab.templatekotlin.repository.order;

import com.omnilab.templatekotlin.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class OrderSearch {
    private String memberName;
    private OrderStatus orderStatus;
}
