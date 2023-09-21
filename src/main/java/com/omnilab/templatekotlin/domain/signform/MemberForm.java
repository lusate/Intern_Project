package com.omnilab.templatekotlin.domain.signform;

import com.omnilab.templatekotlin.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


@Getter @Setter
public class MemberForm {

    @Email(message = "이메일 형식에 맞지 않습니다.")
    private String loginId;

    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String password;

    // 위 비밀번호와 일치하는지 확인하는 어노테이션
    @NotEmpty(message = "비밀번호는 필수 항목입니다.")
    private String passwordConfirm;

    @NotEmpty(message = "회원 이름은 필수 항목입니다.")
    private String username;

    private String role;

    @NotEmpty(message = "도시는 반드시 입력")
    private String city;
    @NotEmpty(message = "거리는 반드시 입력")
    private String street;
    @NotEmpty(message = "상세 주소는 반드시 입력")
    private String detail;

    public static MemberForm createMemberForm(Member member){
        MemberForm form = new MemberForm();
        form.loginId = member.getLoginId();
        form.password = member.getPassword();
        form.username = member.getUsername();
        form.city = member.getAddress().getCity();
        form.street = member.getAddress().getStreet();
        form.detail = member.getAddress().getDetail();
        return form;
    }
}
