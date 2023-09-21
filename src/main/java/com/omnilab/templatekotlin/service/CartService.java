package com.omnilab.templatekotlin.service;

import com.omnilab.templatekotlin.domain.cart.Cart;
import com.omnilab.templatekotlin.domain.cart.CartItem;
import com.omnilab.templatekotlin.domain.cart.CartItemDto;
import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.exception.NotCorrespondingException;
import com.omnilab.templatekotlin.repository.cart.CartItemRepository;
import com.omnilab.templatekotlin.repository.cart.CartRepository;
import com.omnilab.templatekotlin.repository.item.ItemRepository;
import com.omnilab.templatekotlin.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor //@RequiredArgsConstructor는 final이 있는 필드만 모아서 생성자를 만들어 준다.
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final OrderService orderService;
    private final CartRepository cartRepository;


    //장바구니 생성
//    public void createCart(Member member){
//        Cart cart = Cart.createCart(member);
//        cartRepository.save(cart);
//    }


    // 장바구니 Item 추가 / 재고와 count를 확인하기 위해 int으로
    @Transactional
    public int addCart(Long memberId, Long itemId, Integer count) {

        log.error("에러1 {} {} {}", memberId, itemId, count);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotCorrespondingException("memberId에 해당하는 Member 객체가 존재하지 않습니다."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotCorrespondingException("itemId에 해당하는 Item 객체가 존재하지 않습니다."));

        Cart cart = cartRepository.findByMemberId(memberId);


        log.error("에러2 {} {} {}", memberId, itemId, count);


        // cart 가 비어있다면 생성.
        if(cart == null){
            cart = Cart.createCart(member);
            cartRepository.save(cart);
        }

        // cart_item 생성
        CartItem cartItem = cartItemRepository.findByCartIdAndItemId(cart.getId(),item.getId());

        if(item.getStockQuantity() < count || count == 0){
            return 0;
        }

        else {
            // cart_item이 비어있다면 새로 생성.
            if (cartItem == null) {
                cartItem = CartItem.createCartItem(member, cart, item, count);
                cartItemRepository.save(cartItem);
                cart.setCount(cart.getCount() + 1);
            } else {
                // 비어있지 않다면 그만큼 갯수를 추가.
                cartItem.addCount(count);
            }

            return 1;
        }
    }


    // 장바구니에 Item 추가
/*    @Transactional
    public int addCartItem(Long memberId, Long itemId, Integer count) {
        // Member, Item 객체 찾기
        log.error("에러1 {} {} {}", memberId, itemId, count);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotCorrespondingException("memberId에 해당하는 Member 객체가 존재하지 않습니다."));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new NotCorrespondingException("itemId에 해당하는 Item 객체가 존재하지 않습니다."));




        log.error("에러2 {} {} {}", memberId, itemId, count);
        // 현재 재고수(조회한 아이템 정보의 남은 수량과 비교) >= 주문 수량 (성공)
        if(item.getStockQuantity() < count || count == 0){
            return 0;
        }

        else {

            CartItem cartItem = cartItemRepository.findByMemberIdAndItemId(memberId, item.getId()).orElseGet(() -> CartItem.createCartItem(member, item, 0));

            // Item 개수 및 Cart 의 Item 개수 갱신
            log.error("개수: {}", cartItem.getCount()); // 개수가 2인 상태
            cartItem.addCount(count); // 주문 수량 2를 입력하면 +2
            log.error("개수: {}", count); // 4
            cartItemRepository.save(cartItem); // 결국 4가 저장됨.
            return 1;
        }
    }*/






    @Transactional
    public Page<CartItemDto> cartItems(Long memberId, Pageable pageable) {
        return cartItemRepository.findAllByMemberId(memberId, pageable).map(i -> new CartItemDto(i));
    }



    //장바구니 Item 삭제
    @Transactional
    public void cancelCartItem(Long cartItemId) {

        cartItemRepository.deleteById(cartItemId); // 한 개 취소
    }



    //장바구니 Item 전체 삭제
    /*public void cartDelete(int id){
        List<CartItem> cart_items = cartItemRepository.findAll(); // 전체 cart_item 찾기

        // 반복문을 이용하여 접속 User의 Cart_item 만 찾아서 삭제
        for(CartItem cart_item : cart_items){
            if(cart_item.getCart().getMember().getId() == id){
                cart_item.getCart().setCount(cart_item.getCart().getCount() - 1);
                cartItemRepository.deleteById(cart_item.getId());
            }
        }
    }*/

    @Transactional
    public void cancelCartItems(List<Long> cartItemIds) {
        cartItemRepository.deleteAllByIds(cartItemIds);
    }


    public Optional<CartItem> findOne(Long id){
        return cartItemRepository.findById(id);
    }

    public List<CartItem> findAll() {
        return cartItemRepository.findAll();
    }


    //cartItem에서 주문 시에 사용
    @Transactional
    public void order(List<Long> cartItemIds, Long memberId) {
        cartItemRepository.findAllById(cartItemIds)
                .forEach(item -> orderService.order(memberId, item.getItem().getId(), item.getCount()));
    }



}
