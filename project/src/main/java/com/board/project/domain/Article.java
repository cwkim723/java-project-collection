package com.board.project.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class Article {
    // @Transient 언급이 없는 이상 @Column 적용된 것으로 인식 (nullable = true)

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // JPA의 autoincrement는 IDENTITY 방식
    private Long id;

    // 필수
    @Setter // 사용자가 특정 필드에 접근하지 못하도록 전체에 걸지 않고 특징 필드에만 건다
    @Column(nullable = false)
    private String title; // 제목

    @Setter
    @Column(nullable = false, length = 10000)
    private String content; // 본문

    // 옵션
    @Setter
    private String hashtag; // 해시태그

    // JPA Auditing
    @CreatedDate // Entity가 생성되어 저장될 때 시간이 자동 저장
    @Column(nullable = false)
    private LocalDateTime createdAt; // 생성일시

    @CreatedBy
    @Column(nullable = false, length = 100)
    private String createdBy; // 생성자

    @LastModifiedDate // 조회한 Entity의 값을 변경할 때 시간이 자동 저장
    @Column(nullable = false)
    private LocalDateTime modifiedAt; // 수정일시

    @LastModifiedBy
    @Column(nullable = false, length = 100)
    private String modifiedBy; // 수정자

    // Entity로서의 기본 기능 추가
    // Hibernate 구현체 기준으로 기본 생성자가 필요 (외부에서는 사용하지 못하도록 해야 하므로 protected 사용)
    // 코드 바깥에서 new로 생성하지 못하도록 함
    protected Article() {}

    // 도메인과 관련이 있는 정보만 오픈
    // private으로 막아버리고 팩토리 매서드로 사용할 수 있도록 함
    // 생성자의 접근 제한자를 private으로 설정함으로써 객체 생성을 정적 팩토리 메서드로만 가능하도록 제한
    private Article(String title, String content, String hashtag) {
        // public or protected만 가능
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    // 도메인 Article을 생성하려면 위의 매개값이 필요하다는 가이드
    // list, collection 에 넣고 사용할 때 동일성 / 동등성 확인 필요
    // Lombok의 @EqualsAndHashCode 사용 x, 사용하게 되면 모든 필드가 동일해야 동일한 객체라고 판단하게 되는데 그럴 필요 없음
    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) {
        // equals 비교 -> id까리 비교하면 됨

        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return id!= null && id.equals(article.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
