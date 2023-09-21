package com.omnilab.templatekotlin.domain.ItemForm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class ClothForm {
    private Long id;
    private Long memberId;

    private String dtype;

    @NotEmpty(message = "이름은 필수 항목입니다.")
    private String name;

    @NotNull(message = "가격은 필수 항목입니다.")
    private int price;

    @NotNull(message = "수량은 필수 항목입니다.")
    private int stockQuantity;

    @NotEmpty(message = "브랜드는 필수 항목입니다.")
    private String brand;

    @NotNull(message = "사이즈는 필수 항목입니다.")
    private int size;

    @NotEmpty(message = "색상은 필수 입니다!!")
    private String color;

    public static ClothForm createClothForm(Long id, Long memberId, String name, int price, int stockQuantity, String brand, int size, String color){
        ClothForm form = new ClothForm();
        form.id = id;
        form.memberId = memberId;
        form.name = name;
        form.price = price;
        form.stockQuantity = stockQuantity;
        form.brand = brand;
        form.size = size;
        form.color = color;
        return form;
    }
}
