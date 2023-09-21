package com.omnilab.templatekotlin.domain.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnilab.templatekotlin.domain.member.Member;
import lombok.*;
import java.util.*;
import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Cart {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id")
    private Member member; // 구매자와 1대1

    private int count; // 카트에 담긴 총 상품 개수

    @OneToMany(mappedBy = "cart") // cartItems와 1대 다
    private List<CartItem> cartItems = new ArrayList<>();


    public static Cart createCart(Member member) {
        Cart cart = new Cart();
        cart.member = member;
        cart.count = 0;
        return cart;
    }

}
