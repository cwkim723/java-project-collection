package com.board.project.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class Article extends AuditingFields {
    // @Transient 언급이 없는 이상 @Column 적용된 것으로 인식 (nullable = true)
    // http://localhost:8080/api에 들어가면 hal explorer 페이지가 뜸

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

    @ToString.Exclude // 무한 순환참조 문제가 발생할 수 있음. ArticleComment 안에 Article이 존재하므로 -> 순환 참조 문제를 끊어주는 목적
    @OrderBy("id")
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();
    // article에 연동되어있는 comment는 중복을 허용하지 않고 다 모아서 collection으로 보겠다
    // article 테이블로부터 온 것이다라는 것을 명시해줌
    // 실무에서는 양방향을 풀어주는(걸어주지 않는) 경우가 많음 -> ex. 글은 지워도 댓글은 남기는 경우 존재




    // JPA Auditing
//    @CreatedDate // Entity가 생성되어 저장될 때 시간이 자동 저장
//    @Column(nullable = false)
//    private LocalDateTime createdAt; // 생성일시
//
//    @CreatedBy
//    @Column(nullable = false, length = 100)
//    private String createdBy; // 생성자
//
//    @LastModifiedDate // 조회한 Entity의 값을 변경할 때 시간이 자동 저장
//    @Column(nullable = false)
//    private LocalDateTime modifiedAt; // 수정일시
//
//    @LastModifiedBy
//    @Column(nullable = false, length = 100)
//    private String modifiedBy; // 수정자

    /*
    * 위 4가지 JPA Auditing 생성 방법
    * 방법1. entity마다 생성하기(중복이 많아진다는 단점 존재)
    *   장점: 데이터베이스마다 유연하게 관리 가능 (ex. article에서는 4개 다 사용, articlecomment에서는 create 관련 데이터만 사용 등등)
    * 방법2-1. @Embedded -> 필드 추가 방법
      @Embedded Metadata metadata;
      class Metadata() {}
    * 방법2-2. @MappedSuperclass -> 상속 방법
      AuditingFields 클래스 생성 후 상속
    * */





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
