package com.board.project.repository;

import com.board.project.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository의 구현체인 SimpleRepository에 @Repository가 붙어있어서 여기에 굳이 @Repository를 붙여주지 않아도됨. 여러 이유로 @Repository는 class에 주로 붙임
// command + option + b하면 확인 가능
public interface ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
