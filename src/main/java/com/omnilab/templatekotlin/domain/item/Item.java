package com.omnilab.templatekotlin.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.omnilab.templatekotlin.domain.BaseEntity;
import com.omnilab.templatekotlin.domain.cart.CartItem;
import com.omnilab.templatekotlin.domain.member.Member;
import com.omnilab.templatekotlin.domain.order.OrderItem;
import com.omnilab.templatekotlin.domain.review.Review;
import com.omnilab.templatekotlin.exception.NotEnoughStockException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.InheritanceType.SINGLE_TABLE;

@Entity
@Inheritance(strategy = SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class Item extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "item_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    protected Member member;


    protected String name;
    protected int price;
    protected int stockQuantity;


    @Column(insertable = false, updatable = false)
    private String dtype;


    //item 테이블에 저장할 파일에 대한 정보
    protected String fileOriName; // 업로드할 원래 파일의 이름

    protected String saveName; // 저장될 때 이름

    protected String fileUrl; // 저장할 장소


    // 외래키로 item_id를 외래키로 지정해서 연결해놨는데 장바구니에 아이템이 있는 상태에서
    // 재고를 삭제하려고 하니 에러가 발생한다. (테이블 연관관계 잘 파악)
    // 2개를 연결을 해줘야 Cannot delete or update a parent row: a foreign key constraint fails 에러가 발생하지 않는다.
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<CartItem> cartItem;



    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL) // @OneToMany 나 @ManyToOne에 옵션으로 줄 수 있는 값. Entity의 상태 변화를 전파시키는 옵션
    private List<OrderItem> orderItems = new ArrayList<>();


    // 아이템 1 : 리뷰 N
    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();


    @JsonIgnore
    @Builder
    public Item(String fileOriName, String saveName, String fileUrl) {
        this.fileOriName = fileOriName;
        this.saveName = saveName;
        this.fileUrl = fileUrl;
    }


    /** 비지니스 로직 **/

    // 재고 수량 증가
    public void addStock(int quantity){

        this.stockQuantity += quantity;
    }

    // 재고 수량 감소
    public void removeStock(int quantity){
//        log.error("재고");
//        log.error("재고 : {}", this.stockQuantity);
//        log.error("수량 : {}", quantity);


        if(this.stockQuantity < quantity){
            throw new NotEnoughStockException("재고가 부족합니다.");
        }
        else if(quantity < 0){
            throw new NotEnoughStockException("음수를 입력할 수 없습니다.");
        }
        this.stockQuantity -= quantity;
    }



    public Item(String fileUrl) {
        this.fileUrl = fileUrl;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setDtype(String dtype) {
        this.dtype = dtype;
    }


    public void setFileOriName(String fileOriName) {
        this.fileOriName = fileOriName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public String getDtype() {
        return dtype;
    }


    public String getFileOriName() {
        return fileOriName;
    }

    public String getSaveName() {
        return saveName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

}
