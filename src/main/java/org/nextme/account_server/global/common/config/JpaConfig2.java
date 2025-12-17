package org.nextme.account_server.global.common.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
//@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaAuditing
@EnableTransactionManagement
public class JpaConfig2 {
//    @Bean
//    public AuditorAware<String> auditorProvider(){
//        return new AuditorAwareImpl();
//    }
    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}