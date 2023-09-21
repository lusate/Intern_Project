package com.omnilab.templatekotlin.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.ModelAndView;


@Slf4j
@Controller
public class HomeController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/")
    public String homeLogin(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Long memberId, Model model){
        //사용자 정보 찾음
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.error("member : {}", memberId);


        /** 로그인 페이지로 이동 **/
        if(memberId == null) {
            logger.error("IF : {} ", principal);
//            model.addAttribute("loginForm", new LoginForm());
//            return "/logins/loginForm";
            return "/index";
        }

        else {
            logger.error("else {} : ", principal);
            /** 메인 페이지로 이동 **/
            return "/home";
        }
    }
}



//@SessionAttribute : sessionAttribute는 HttpSession에 저장되어있는 값을 Handler의 매개변수에 맵핑해주는 어노테이션