package com.omnilab.templatekotlin.repository.order;


import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.member.QMember;
import com.omnilab.templatekotlin.domain.order.Order;
import com.omnilab.templatekotlin.domain.order.OrderStatus;
import com.omnilab.templatekotlin.domain.order.QOrder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
public class OrderRepositoryImpl implements OrderRepositoryJpql{

    private final EntityManager em;

    @Override
    public List<Order> findAll(Long loginUser) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        QOrder order = QOrder.order;
        QMember member = QMember.member;


        return query
                .select(QOrder.order)
                .from(order)
                .join(order.member, member)
                .where(loginEqual(loginUser))
                .limit(1000)
                .fetch();
    }

    @Override
    public List<Order> findAllAdmin(OrderSearch orderSearch) {
        JPAQueryFactory query = new JPAQueryFactory(em);

        QOrder order = QOrder.order;
        QMember member = QMember.member;


        return query
                .select(QOrder.order)
                .from(order)
                .join(order.member, member)
                .where(statusEq(orderSearch.getOrderStatus()), nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();
    }


    private BooleanExpression nameLike(String username) {
        if(!StringUtils.hasText(username)){
            return null;
        }
        return QMember.member.username.like(username);
    }

    private BooleanExpression statusEq(OrderStatus statusCond){
        if(statusCond == null){
            return null;
        }

        return QOrder.order.status.eq(statusCond);
    }

    private BooleanExpression loginEqual(Long id) {
        return id != null ? QMember.member.id.eq(id) : null;
    }


}
