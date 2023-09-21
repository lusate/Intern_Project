package com.omnilab.templatekotlin.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Address {
    private String city;
    private String street;
    private String detail;

    public static Address createAddress(String city, String street, String detail) {
        Address address = new Address();
        address.city = city;
        address.street = street;
        address.detail = detail;
        return address;
    }

    public void update(String city, String street, String detail){
        this.city = city;
        this.street = street;
        this.detail = detail;
    }
}
