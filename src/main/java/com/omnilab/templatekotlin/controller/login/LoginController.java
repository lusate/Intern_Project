//package com.omnilab.templatekotlin.controller.login;
//
//
//import com.omnilab.templatekotlin.domain.signform.LoginForm;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.validation.Valid;
//
//@Controller
//@RequiredArgsConstructor
//@Slf4j
//public class LoginController {
////    private final LoginService loginService;
////    private final MemberRepository memberRepository;
//
//
//    @PostMapping("/")
//    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
//                        @RequestParam(defaultValue = "/") String redirectURL,
//                        HttpServletRequest request, Model model) {
//
//        if (bindingResult.hasErrors()) {
////            return "logins/loginForm";
//            return "index";
//        }
//
//
//        return "redirect:" + redirectURL;
//    }
//
//}
