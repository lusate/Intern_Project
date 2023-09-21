package com.omnilab.templatekotlin.domain.cart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnilab.templatekotlin.domain.BaseEntity;
import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Slf4j
public class CartItem extends BaseEntity {
    @Id
    @GeneratedValue
    @Column(name = "cart_item_id")
    private Long id;

    // 부모를 삭제하면 자동으로 자식들도 삭제
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;


    // cartItem : N / Item : 1
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id") // 아이템 하나를 여러번 장바구니로 넣을 수 있기 때문에
    private Item item;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name="cart_id")
    private Cart cart;


    /** 주문 상품의 개수 **/
    @NotNull(message = "주문 수량은 반드시 입력해야 합니다.")
    private int count;

    /** CartItem 생성자 **/

    public static CartItem createCartItem(Member member, Cart cart, Item item, int count){
        CartItem cartItem = new CartItem();
        cartItem.member = member;
        cartItem.cart = cart;
        cartItem.item = item;
        cartItem.count = count;


        return cartItem;
    }

    /** 상품의 개수 갱신 **/
    public void addCount(int count){
        this.count += count;
    }
}
