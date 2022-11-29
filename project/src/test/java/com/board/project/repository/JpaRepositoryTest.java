package com.board.project.repository;

import com.board.project.config.JpaConfig;
import com.board.project.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

//@ActiveProfiles("testdb")
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class) // 이게 없으면 JpaConfig의 Auditing을 인식하지 못함
@DataJpaTest // @ExtendWith(SpringExtension.class) 존재 -> 생성자 주입 패턴으로 의존성 주입 가능
class JpaRepositoryTest {
    // @DataJpaTest -> 메소드 단위로 자동으로 @Transactional이 걸려있음
    // 테스트 돌릴 때 기본으로 @Transactional은 롤백으로 동작

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository
    ) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }


    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_thenWorksFine() {
        // Given

        // When
        List<Article> article = articleRepository.findAll();

        // Then
        assertThat(article)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_thenWorksFine() {
        // Given
        long previousCount = articleRepository.count();

        // When
        articleRepository.save(Article.of("new article", "new content", "#spring"));

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }

    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_thenWorksFine() {
        // update쿼리가 나타나지 않음.
        // springboot로 바꿔도 다시 롤백을 통해 원래 값으로 돌아가기 때문에 update를 날리지 않는 것임
        // 그래서 따로 flush를 날려줘야 함

        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updatedHashtag = "#springboot";
        article.setHashtag(updatedHashtag);

        // When
        Article savedArticle = articleRepository.saveAndFlush(article);
        // 테스트에서 update 쿼리를 생성해주고 싶으면 saveAndFlush를 통해 flush를 날려주어야 함
        /*
            update
                article
            set
                content=?,
                created_at=?,
                created_by=?,
                hashtag=?,
                modified_at=?,
                modified_by=?,
                title=?
            where
                id=?
        */

        // Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updatedHashtag);
    }

    @DisplayName("delete 테스트")
    @Test
    void givenTestData_whenDeleting_thenWorksFine() {
        // Given
        Article article = articleRepository.findById(1L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int deletedCommentsSize = article.getArticleComments().size();

        // When
        articleRepository.delete(article);

        // Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - deletedCommentsSize);
    }

}