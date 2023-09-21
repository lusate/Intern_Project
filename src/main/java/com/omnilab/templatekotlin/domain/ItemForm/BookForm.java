package com.omnilab.templatekotlin.domain.ItemForm;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Getter @Setter
public class BookForm{


    private Long id;
    private Long memberId;

    private String dtype; //물건의 상태 (팔림, 안 팔림.)

    @NotEmpty(message = "이름은 필수 항목입니다.")
    private String name;

    @NotNull(message = "가격은 필수 항목입니다.")
    private int price;


    @NotNull(message = "수량은 필수 항목입니다.")
    private int stockQuantity;

    @NotEmpty(message = "저자는 필수 항목입니다.")
    private String author;

    @NotEmpty(message = "ISBN는 필수 항목입니다.")
    private String isbn;

    public static BookForm createBookForm(Long id, Long memberId, String name, int price, int stockQuantity, String author, String isbn){
        BookForm form = new BookForm();
        form.id = id;
        form.memberId = memberId;
        form.name = name;
        form.price = price;
        form.stockQuantity = stockQuantity;
        form.author = author;
        form.isbn = isbn;
        return form;
    }
}
