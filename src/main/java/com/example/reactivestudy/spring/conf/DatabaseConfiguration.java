package com.example.reactivestudy.spring.conf;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

import com.example.reactivestudy.util.EncryptionUtil;

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;

@Configuration
@EnableR2dbcRepositories
@PropertySource("classpath:database.properties")
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {

    @Value("${postgresql.database.url}")
    String url;

    @Value("${postgresql.database.dbname}")
    String database;

    @Value("${postgresql.database.schema}")
    String schema;
    
    @Value("${postgresql.database.port}")
    Integer port;

    @Value("${postgresql.database.username}")
    String username;

    @Value("${postgresql.database.password}")
    String password;

    @Value("${postgresql.database.timeout}")
    Long timeout;

    @Value("${postgresql.database.encrypted}")
    boolean encrypted;

    @Bean(name = "PostgresqlConnection")
    public PostgresqlConnectionFactory connectionFactory() {

        String confUsername = encrypted ? EncryptionUtil.decryptAES(username).get() : username;
        String confPassword = encrypted ? EncryptionUtil.decryptAES(password).get() : password;

        PostgresqlConnectionConfiguration config = PostgresqlConnectionConfiguration.builder()
                                                        .host(url)
                                                        .database(database)
                                                        .schema(schema)
                                                        .port(port)
                                                        .username(confUsername)
                                                        .password(confPassword)
                                                        .connectTimeout(Duration.ofMillis(timeout))
                                                        .build();
        return new PostgresqlConnectionFactory(config);
    }
}
