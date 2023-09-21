package com.omnilab.templatekotlin.domain.member;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.omnilab.templatekotlin.domain.Address;
import com.omnilab.templatekotlin.domain.BaseEntity;
import com.omnilab.templatekotlin.domain.cart.Cart;
import com.omnilab.templatekotlin.domain.item.Item;
import com.omnilab.templatekotlin.domain.order.Order;
import com.omnilab.templatekotlin.domain.review.Review;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@Builder
@Setter
public class Member extends BaseEntity implements UserDetails{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String loginId;

    private String password;

    private String username;

    @Column(name="role")
    private String role;

    @Embedded
    private Address address;

    @OneToOne(fetch = LAZY, mappedBy = "member")
    private Cart cart;


    @JsonIgnore  //@ManyToOne의 Fetch 타입을 Lazy로 사용했을 때 나타나는 문제점 -> 비어있는 객체를 Serialize 하려다 에러가 발생
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL) // 부모를 삭제하면 자동으로 자식들도 삭제
    private List<Order> orders = new ArrayList<>();


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();


    // 리뷰 N : 멤버 1
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();



//    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
//    private List<Comment> comments = new ArrayList<>();



    // 회원 가입 때 멤버 만들기
    public static Member createMember(String loginId, String password, String name, String role, Address address){
        Member member = new Member();
        member.loginId = loginId;
        member.password = password;
        member.username = name;
        member.role = role;
        member.address = address;

        return member;
    }


    @Builder
    public Member(String loginId, String password, String role) {
        this.loginId = loginId;
        this.password = password;
        this.role = role;
    }



    public void changeName(String name) {
        this.username = name;
    }
//    public boolean checkPassword(String password){
//        return this.password.equals(password);
//    }



    public void update(String password, String username, String city, String street, String detail){
        this.password = password;
        this.username = username;
        this.address.update(city,street,detail);
    }


    // 이거
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : role.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
    }



    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }



    public void setUsername(String username) {
        this.username = username;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
