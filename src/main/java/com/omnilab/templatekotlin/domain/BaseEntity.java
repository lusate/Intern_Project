package com.omnilab.templatekotlin.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class) // 엔티티를 DB에 적용하기 전, 이후에 커스텀 콜백을 요청할 수 있는 어노테이션 / Auditing 을 수행할 때
@MappedSuperclass // 엔티티의 공통 매핑 정보가 필요할 때 주로 사용한다.
@Getter
public class BaseEntity {


    @CreatedDate
    @Column(updatable = false) // 생성일자(createdDate)에 대한 정보는 생성시에만 할당 가능, 갱신 불가
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModified;

}
