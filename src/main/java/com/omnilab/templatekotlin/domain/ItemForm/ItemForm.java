package com.omnilab.templatekotlin.domain.ItemForm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter @Setter
public class ItemForm {

    private Long id;
    private Long memberId;

    private String dtype;

    @NotEmpty(message = "이름은 필수 입니다!!")
    private String name;

    @NotNull(message = "가격은 필수 입니다!!")
    private int price;

    @NotNull(message = "수량은 필수 입니다!!")
    private int stockQuantity;

    private Long fileId;
    private String fileOriName; // 업로드할 원래 파일의 이름
    private String saveName; // 저장될 때 이름
    @JsonProperty
    private String fileUrl; // 저장할 장소

    //여기까지가 공통 속성들

    //Book 속성
    @NotEmpty(message = "저자는 필수 입니다!!")
    private String author;

    private String isbn;


    //Cloth 속성
    @NotEmpty(message = "브랜드는 필수 입니다!!")
    private String brand;
    @NotNull(message = "사이즈는 필수 입니다!!")
    private int size;

    @NotEmpty(message = "색상은 필수 입니다!!")
    private String color;


}
