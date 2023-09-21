package com.omnilab.templatekotlin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnilab.templatekotlin.domain.order.Order;
import com.omnilab.templatekotlin.domain.order.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "delivery_id")
    private Long id;

    @OneToOne(fetch = LAZY, mappedBy = "delivery")
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(value = STRING)
    public DeliveryStatus status;


    public static Delivery createDelivery(Address address) {
        Delivery delivery = new Delivery();
        delivery.address = address;
        return delivery;
    }

    public void update(DeliveryStatus status) {
        this.status = status;
    }

    public void setOrder(Order order){
        this.order = order;
    }
}
