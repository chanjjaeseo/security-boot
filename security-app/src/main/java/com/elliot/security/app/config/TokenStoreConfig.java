package com.elliot.security.app.config;

import com.elliot.security.app.oauth.token.JWTTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

@Configuration
public class TokenStoreConfig {

    // 如果storeType为redis 则注入该bean
    @Configuration
    @ConditionalOnProperty(prefix = "boot.app.oauth2", name = "token-store", havingValue = "redis")
    public static class RedisConfig {

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        @Bean
        public TokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

    }

    // 如果storeType为jdbc 则注入该bean
    @Configuration
    @ConditionalOnProperty(prefix = "boot.app.oauth2", name = "token-store", havingValue = "jdbc")
    public static class JdbcConfig {

        @Autowired
        private DataSource dataSource;

        @Bean
        public TokenStore jdbcTokenStore() {
            return new JdbcTokenStore(dataSource);
        }
    }

    // 如果storeType为jwt 则注入该bean
    // 如果boot.app.oauth2.store-type缺少时同样注入该bean
    @Configuration
    @ConditionalOnProperty(prefix = "boot.app.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
    public static class JWTConfig {

        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter() {
            JwtAccessTokenConverter tokenConverter = new JwtAccessTokenConverter();
            // 妥善保管jwt验签
            tokenConverter.setSigningKey("~!security-boot!~");
            return tokenConverter;
        }

        @Bean
        public TokenStore jwtTokenStore() {
            return new JwtTokenStore(jwtAccessTokenConverter());
        }

        @Bean
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer() {
            return new JWTTokenEnhancer();
        }

    }

}
