package com.board.project.domain;

import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
//@EntityListeners(AuditingEntityListener.class)
@Entity
public class ArticleComment extends AuditingFields{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    @ManyToOne(optional = false)
    private Article article; // 게시글(ID)
    // 연관관계 매핑 하지 않는 경우 Long articleId로 할 수도 있겠으나 객체지향적으로 연관관계를 줄 것

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 본문

//    @CreatedDate
//    @Column(nullable = false)
//    private LocalDateTime createdAt; // 생성일시
//
//    @CreatedBy
//    @Column(nullable = false, length = 100)
//    private String createdBy; // 생성자
//
//    @LastModifiedDate
//    @Column(nullable = false)
//    private LocalDateTime modifiedAt; // 수정일시
//
//    @LastModifiedBy
//    @Column(nullable = false, length = 100)
//    private String modifiedBy; // 수정자

    protected ArticleComment() {}
    // annotation으로 @NoArgsConstructor(access = AccessLevel.PROTECTED) 로 지정해줘도 됨


    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }

    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment)) return false;
        ArticleComment that = (ArticleComment) o;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
