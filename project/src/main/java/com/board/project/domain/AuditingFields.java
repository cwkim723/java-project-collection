package com.board.project.domain;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@ToString
@EntityListeners(AuditingEntityListener.class) // @EntityListeners: 엔티티를 DB에 적용하기 전, 이후에 커스텀 콜백을 요청. Auditing 을 수행할 때는 AuditingEntityListener.class 를 넘기면 된다.
@MappedSuperclass // 엔티티가 아닌 클래스를 상속받기 위해서 @MappedSuperclass를 사용, 공통된 매핑 정보가 필요한 경우에 사용
public abstract class AuditingFields {
    // JPA에서 엔티티가 상속을 받기 위해서는 @Entity 혹은 @MappedSuperclass가 붙어있는 엔티티 클래스만 상속이 가능

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt; // 생성일시

    @CreatedBy
    @Column(nullable = false, updatable = false, length = 100)
    private String createdBy; // 생성자

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자
}
