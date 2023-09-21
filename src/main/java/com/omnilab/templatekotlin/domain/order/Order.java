package com.omnilab.templatekotlin.domain.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnilab.templatekotlin.domain.Delivery;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.DeliveryStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // 특정 엔티티를 영속 상태로 만들 경우, 연관된 엔티티도 함께 영속 상태로 만들고 싶을 경우 영속성 전이를 사용
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) // @OneToMany 나 @ManyToOne에 옵션으로 줄 수 있는 값. Entity의 상태 변화를 전파시키는 옵션
    private List<OrderItem> orderItems = new ArrayList<>();
    // ALL : 상위 엔터티에서 하위 엔터티로 모든 작업을 전파

    @OneToOne(fetch = LAZY, cascade = CascadeType.ALL)
    private Delivery delivery;

    private LocalDate orderDate;


    @Enumerated(value = STRING)
    private OrderStatus status;

    // 연관 관계 편의 메소드
    public void setMember(Member member){
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    // 생성 메소드 //
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        order.status = OrderStatus.ORDER;
        delivery.status = DeliveryStatus.WAITING;

        order.orderDate = LocalDate.now();

        return order;
    }





    // 주문 취소
//    public void cancel(){
//        if(delivery.getStatus() == DeliveryStatus.COMPLETE){
//            throw new IllegalStateException("이미 배송 완료된 상품은 취소가 불가능합니다.");
//        }
//        this.status = OrderStatus.CANCEL;
//        for (OrderItem orderItem : orderItems) {
//            orderItem.cancel();
//        }
//    }


}
