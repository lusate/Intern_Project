package com.omnilab.templatekotlin.service;

import com.omnilab.templatekotlin.domain.Delivery;
import com.omnilab.templatekotlin.domain.DeliveryDto;
import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.order.Order;
import com.omnilab.templatekotlin.domain.order.OrderItem;
import com.omnilab.templatekotlin.exception.NotCorrespondingItemException;
import com.omnilab.templatekotlin.repository.delivery.DeliveryRepository;
import com.omnilab.templatekotlin.repository.item.ItemRepository;
import com.omnilab.templatekotlin.repository.member.MemberRepository;
import com.omnilab.templatekotlin.repository.order.OrderRepository;
import com.omnilab.templatekotlin.repository.order.OrderSearch;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final DeliveryRepository deliveryRepository;

    // COMMENT 주문
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        // 엔티티 조회
        Member member = memberRepository.findById(memberId).get();
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new NotCorrespondingItemException("해당하는 아이템이 존재하지 않습니다."));



        // 배송정보 생성
        Delivery delivery = Delivery.createDelivery(member.getAddress());


        // 주문 상품 생성 -> 주문한 상품 정보, 주문 금액, 주문 수량
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성 -> 주문한 회원, 배송 정보, 주문 날짜, 주문 상태
        Order order = Order.createOrder(member, delivery, orderItem);


        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }


    @Transactional
    public void deliveryUpdate(DeliveryDto deliveryDto) {
        log.error("deliveryDto {}", deliveryDto.getDeliveryId());
        Optional<Delivery> deliveryId = deliveryRepository.findById(deliveryDto.getDeliveryId());
        log.error("deliveryId : {}", deliveryId);


        if (deliveryId.isPresent()) {
            Delivery delivery = deliveryId.get();
            log.error("deliveryId : {}", delivery.getStatus());

            delivery.update(deliveryDto.getStatus()); // 배송 상태 수정

            log.error("deliveryId : {}", delivery.getId());
            log.error("deliveryId : {}", delivery.getStatus());

            deliveryRepository.save(delivery);
        }
    }




//    @Transactional
//    public void cancelOrder(Long orderId){
//        // 엔티티 조회
//        Order order = orderRepository.findById(orderId).orElseThrow(() -> new NotCorrespondingOrderException("해당하는 주문이 존재하지 않습니다."));
//        // 주문 취소
//        order.cancel();
//    }



    // 사용자를 위한 주문 리스트 나열
    public List<Order> findOrdersByUser(Long loginUser){
        return orderRepository.findAll(loginUser);
    }


    // 관리자를 위한 주문 리스트 나열
    public List<Order> findOrdersByAdmin(OrderSearch orderSearch){
        return orderRepository.findAllAdmin(orderSearch);
    }


    public Delivery findOne(Long id) {
        return deliveryRepository.findById(id).get();
    }

}
