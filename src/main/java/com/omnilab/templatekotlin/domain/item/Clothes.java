package com.omnilab.templatekotlin.domain.item;

import com.omnilab.templatekotlin.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import static lombok.AccessLevel.PROTECTED;


@Entity
@Getter
@DiscriminatorValue("CLOTH")
@NoArgsConstructor(access = PROTECTED)
public class Clothes extends Item{
    private String brand;
    private int size;
    private String color;


    public static Clothes createClothes(Member member, String name, int price, int stockQuantity, String brand, int size, String color){
        Clothes clothes = new Clothes();
        clothes.member = member;
        clothes.name = name;
        clothes.price = price;
        clothes.stockQuantity = stockQuantity;
        clothes.brand = brand;
        clothes.size = size;
        clothes.color = color;
        return clothes;
    }

}

