package com.omnilab.templatekotlin.domain.member;

import lombok.Data;

@Data
public class MemberDto {
    private String username;
    private String city;
    private String street;
    private String detail;
    private String role; // User, Admin

    public MemberDto(Member member) {
        this.username = member.getUsername();
        this.city = member.getAddress().getCity();
        this.street = member.getAddress().getStreet();
        this.detail = member.getAddress().getDetail();
        this.role = member.getRole();
    }
}
