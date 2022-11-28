package com.board.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        // Optional.of(value) : value값이 null이 아닌 경우에 사용
        // @CreatedBy, @LastModifiedBy의 이름이 admin으로 들어가게 됨
        return () -> Optional.of("admin"); // TODO: 스프링 시큐리티로 인증 기능을 붙이게 될 때 수정 예정
    }

}
