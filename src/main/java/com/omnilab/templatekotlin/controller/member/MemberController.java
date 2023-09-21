package com.omnilab.templatekotlin.controller.member;

import com.omnilab.templatekotlin.controller.SessionConst;
import com.omnilab.templatekotlin.domain.Address;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.member.MemberDto;
import com.omnilab.templatekotlin.domain.signform.MemberForm;
import com.omnilab.templatekotlin.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping("/members/new")
    public String createForm(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }




    // @Validated는 스프링 전용 검증 어노테이션이고 @Valid는 자바 표준 검증 어노테이션
//    @PostMapping("/members/new")
//    public String create(@Valid @ModelAttribute MemberForm form, BindingResult result) {
//        if (result.hasErrors()) {
//            return "members/createMemberForm";
//        }
//
//        Address address = Address.createAddress(form.getCity(), form.getStreet(), form.getDetail());
//        Member member = Member.createMember(form.getLoginId(), bCryptPasswordEncoder.encode(form.getPassword()), form.getUsername(), form.getRole(), address);
//        //db에 비밀번호 확인은 컬럼은 없도록함
//
//
//        if (!form.getPassword().equals(form.getPasswordConfirm())) {
//            result.rejectValue("passwordConfirm", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다");
//            return "members/createMemberForm";
//        }
//
//
//        memberService.join(member);
//        return "redirect:/";
//    }




    @PostMapping("/members/new")
    public String create(@Valid @ModelAttribute MemberForm form, BindingResult bindingResult) throws Exception{
        Address address = Address.createAddress(form.getCity(), form.getStreet(), form.getDetail());
        Member member = Member.createMember(form.getLoginId(), bCryptPasswordEncoder.encode(form.getPassword()), form.getUsername(), form.getRole(), address);
        //db에 비밀번호 확인은 컬럼은 없도록함



        if (bindingResult.hasErrors()) { // 스프링이 제공하는 검증 오류를 보관하는 객체
            return "members/createMemberForm";
        }

        else if (!form.getPassword().equals(form.getPasswordConfirm())) {
            bindingResult.rejectValue("passwordConfirm", "passwordInCorrect", "2개의 패스워드가 일치하지 않습니다");
            return "members/createMemberForm";
        }

        memberService.join(member);
        return "redirect:/";
    }


    @ResponseBody
    @PostMapping("/duplicate")
    public Map<String, Object> validate(@RequestBody Map<String, Object> map) throws Exception{

        log.error("{}", map);
        log.error("map {}", map.get("loginId"));

        int result = memberService.idCheck((String) map.get("loginId"));

        // result는 같은 중복된 loginId 개수
        log.error("result : {}", result);
        // result는 1 또는 0이 나옴.
        map.put("result", result);
        return map;
    }




    @GetMapping("/members")
    public String list(@PageableDefault(size = 8) Pageable pageable, Model model) {
        pageable.getSort();
        Page<MemberDto> page = memberService.findAll(pageable);
        model.addAttribute("members", page);
        return "/members/memberList";
    }

    @GetMapping("/member/info")
    public String updateMemberForm(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId, Model model) {
        Member findMember = memberService.findOne(memberId);
        MemberForm form = MemberForm.createMemberForm(findMember);


        model.addAttribute("member", findMember);
        model.addAttribute("memberForm", form);
        return "members/memberInfo";
    }

    @PostMapping("/member/edit")
    public String updateMember(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Long memberId,
                               @Valid @ModelAttribute("memberForm") MemberForm form,
                               BindingResult bindingResult) {


        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.error("{}, {}", error.getObjectName(), error.getDefaultMessage());
            }
            return "/members/memberInfo";
        }

        memberService.updateMember(memberId, bCryptPasswordEncoder.encode(form.getPassword()), form.getUsername(), form.getCity(), form.getStreet(), form.getDetail());
        return "redirect:/member/info";
    }


    //엑셀 다운 받기
    @PostMapping("/excel/download")
    public void excelDownload(HttpServletResponse response) {

        memberService.excelDownloadResponse(response);

    }
}

