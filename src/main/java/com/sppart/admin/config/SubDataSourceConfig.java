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
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/*
admin user DB 연결을 위한 설정
 */
@Configuration
@MapperScan(value = {"com.sppart.admin.sub.*.domain.mapper"}, sqlSessionFactoryRef = "subSqlSessionFactory")
public class SubDataSourceConfig {

    private final String SUB_DATA_SOURCE = "SubDataSource";

    @Bean(SUB_DATA_SOURCE)
    @ConfigurationProperties("spring.sub.datasource.hikari")
    public DataSource subDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean
    public SqlSessionFactory subSqlSessionFactory(@Qualifier(SUB_DATA_SOURCE) DataSource dataSource)
            throws Exception {
        final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
//        sqlSessionFactoryBean.setTypeAliasesPackage("com.sppart.admin.sub.user.dto.mapper");

        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mappers/sub/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);

        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public DataSourceTransactionManager subTransactionManager(@Qualifier(SUB_DATA_SOURCE) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
