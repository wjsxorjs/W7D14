package com.sist.app.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

@Configuration
@MapperScan(basePackages = "com.sist.app.mapper")
public class DBConfig {
    // 현재 클래스는 스프링 환경이 알아서 호출하며
    // 아래의 @Bean이라는 annotation으로 인해 강제로
    // 함수들을 한 번 호출하여 스프링 부트 환경(Context)에
    // 반환되는 객체들을 등록한다.
    @Bean
    public SqlSessionFactory sqlSessionFactory(
        DataSource dataSource) throws Exception {
            SqlSessionFactoryBean factoryBean =
                new SqlSessionFactoryBean();
            factoryBean.setDataSource(dataSource);

            PathMatchingResourcePatternResolver resolver =
            new PathMatchingResourcePatternResolver();
        factoryBean.setMapperLocations(
            resolver.getResources("classpath:mapper/**/*.xml")
        );

        return factoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(
        SqlSessionFactory factory){
            return new SqlSessionTemplate(factory);
        }



        
}