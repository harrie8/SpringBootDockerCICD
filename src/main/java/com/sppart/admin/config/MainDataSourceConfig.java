package com.sppart.admin.config;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/*
전시, 작가 등 주요 DB 연결 설정
 */
@Configuration
@MapperScan(value = {"com.sppart.admin.main.*.domain.mapper"})
public class MainDataSourceConfig {

    private final String MAIN_DATA_SOURCE = "MainDataSource";

    @Primary
    @Bean(MAIN_DATA_SOURCE)
    @ConfigurationProperties(prefix = "spring.main.datasource.hikari")
    public DataSource mainDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Primary
    @Bean
    public SqlSessionFactory mainSqlSessionFactory(@Qualifier(MAIN_DATA_SOURCE) DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/main/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);

        return sqlSessionFactoryBean.getObject();
    }

    @Primary
    @Bean
    public DataSourceTransactionManager mainTransactionManager(@Qualifier(MAIN_DATA_SOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
