package com.omnilab.templatekotlin.controller.cart;


import com.omnilab.templatekotlin.controller.SessionConst;
import com.omnilab.templatekotlin.domain.cart.Cart;
import com.omnilab.templatekotlin.domain.cart.CartItem;
import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.repository.cart.CartItemRepository;
import com.omnilab.templatekotlin.service.CartService;
import com.omnilab.templatekotlin.service.ItemService;
import com.omnilab.templatekotlin.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class CartItemController {
    private final CartService cartService;
    private final MemberService memberService;
    private final ItemService itemService;


    @GetMapping("/cartItems")
    public String cartItems(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Long memberId,
                            @PageableDefault(size = 8) Pageable pageable,
                            Model model){


        model.addAttribute("items", cartService.cartItems(memberId, pageable));

        return "cart/cartItems";
    }


    // 장바구니에 Item 넣기
    @PostMapping("/addItem/{itemId}")
    @ResponseBody //객체를 JSON으로 바꿔서 HTTP body에 담는 스프링 어노테이션
    public Map<String, Object> addToCart(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                                         @PathVariable Long itemId,
                                         @RequestBody Map<String, Object> map) {

        Member member = memberService.findOne(memberId);
        Item item = itemService.findOne(itemId).get();

        log.error("member.getId : {}", member.getId());
        log.error("item.getId : {}", item.getId());



        int result = 0;
        log.error("{}", map);
        log.error("map {} {} {}", memberId, Long.parseLong((String) map.get("itemId")), Integer.parseInt((String) map.get("count")));

//        result = cartService.addCartItem(memberId, Long.parseLong((String) map.get("itemId")), Integer.parseInt((String) map.get("count")));
        result = cartService.addCart(memberId, Long.parseLong((String) map.get("itemId")), Integer.parseInt((String) map.get("count")));


        log.error("result : {}", result);
        // result는 1 또는 0이 나옴.
        map.put("result", result);
        // return map으로

        return map;
    }
    //controller로 값을 보내고 json에 담아 ajax로 받아오는 과정에서 에러가 발생
    //@RequestParam이나 기타 Prameter 값을 받아올 때 null이거나 Type이 맞지 않는 경우 이 에러가 발생
    //false 해주면 해당 Parameter를 반드시 받지 않아도 된다


    @GetMapping("/cartItem/{cartItemId}/cancel")
    public String cancelCartItem(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Long memberId,
                                 @PathVariable("cartItemId") Long cartItemId){

        Member member = memberService.findOne(memberId);

        member.getCart().setCount(member.getCart().getCount() - 1);

        cartService.cancelCartItem(cartItemId);


        return "redirect:/cartItems";
    }


    @PostMapping("/cartItem/order")
    public String orderCartItem(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Long memberId,
                                @RequestParam List<Long> cartItemIds){


        cartService.order(cartItemIds, memberId);
        cartService.cancelCartItems(cartItemIds);

        return "redirect:/orders";
    }
}
