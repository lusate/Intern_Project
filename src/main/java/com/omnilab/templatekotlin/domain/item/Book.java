package com.omnilab.templatekotlin.domain.item;

import com.omnilab.templatekotlin.domain.member.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@DiscriminatorValue("BOOK")
@NoArgsConstructor(access = PROTECTED)
public class Book extends Item{
    @NotEmpty
    private String author;
    @NotEmpty
    private String isbn;

    public static Book createBook(Member member, String name, int price, int stockQuantity, String author, String isbn){
        Book book = new Book();
        book.member = member;
        book.name = name;
        book.price = price;
        book.stockQuantity = stockQuantity;
        book.author = author;
        book.isbn = isbn;

        return book;
    }


}
