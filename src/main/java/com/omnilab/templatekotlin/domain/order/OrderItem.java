package com.omnilab.templatekotlin.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@JsonIgnoreProperties({"hibernateLazyInitializer"})
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;

    private int count;

    //==생성 메소드==//
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.orderPrice = orderPrice;
        orderItem.count = count;

        item.removeStock(count);
        return orderItem;
    }

    //COMMENT 비지니스 로직
    public void cancel() {
        getItem().addStock(count);
    }

    //COMMENT 조회 로직
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }

    public void setOrder(Order order) {
        this.order = order;
    }

}
