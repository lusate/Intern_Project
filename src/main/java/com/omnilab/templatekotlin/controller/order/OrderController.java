package com.omnilab.templatekotlin.controller.order;

import com.omnilab.templatekotlin.controller.SessionConst;
import com.omnilab.templatekotlin.domain.Delivery;
import com.omnilab.templatekotlin.domain.DeliveryDto;
import com.omnilab.templatekotlin.domain.order.Order;
import com.omnilab.templatekotlin.domain.review.ReviewDto;
import com.omnilab.templatekotlin.repository.order.OrderSearch;
import com.omnilab.templatekotlin.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    // 이거
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch") OrderSearch orderSearch, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();

        // session.setAttribute(SessionConst.LOGIN_MEMBER, userDto.id)  여기서 Long 타입을 setAttribute 했기 때문에
        Long loginUser = (Long) session.getAttribute(SessionConst.LOGIN_MEMBER);


        List<Order> orderAdmin = orderService.findOrdersByAdmin(orderSearch);
        List<Order> orders = orderService.findOrdersByUser(loginUser);


        model.addAttribute("orders", orders); // 사용자 주문
        model.addAttribute("orderAdmin", orderAdmin); // 관리자 주문

        return "order/orderList";
    }




    @PostMapping("/order")
    public String order(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count){

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }




    //관리자가 배송 상태 수정
    @PutMapping("/order/update/{id}")
    public ResponseEntity<Long> deliveryUpdate(@PathVariable("id") Long id, @RequestBody DeliveryDto deliveryDto) {
        Delivery delivery = orderService.findOne(id);
        log.error("deliveryId : {}", delivery.getId());
        log.error("deliveryStatus : {}", delivery.getStatus());

        orderService.deliveryUpdate(deliveryDto); // delivery_id
        log.error("deliveryId: {}", deliveryDto.getDeliveryId());
        log.error("deliveryStatus: {}", deliveryDto.getStatus());

        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
