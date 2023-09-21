package com.omnilab.templatekotlin.domain.item;

import com.omnilab.templatekotlin.domain.review.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Long id;
    private Long memberId;
    private String dtype;
    private String name;
    private int price;
    private int stockQuantity;

    private String fileOriName; // 업로드할 원래 파일의 이름
    private String saveName; // 저장될 때 이름
    private String fileUrl; // 저장할 장소



    public ItemDto(Item item) {
        this.id = item.getId();
        if(item.getMember().getId() != null){
            this.memberId = item.getMember().getId();
        }
        this.dtype = item.getDtype();
        this.name = item.getName();
        this.price = item.getPrice();

        this.stockQuantity = item.getStockQuantity();

        this.fileOriName = item.getFileOriName();
        this.saveName = item.getSaveName();
        this.fileUrl = item.getFileUrl();

    }
}
