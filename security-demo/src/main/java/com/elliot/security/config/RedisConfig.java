package com.elliot.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

@Configuration
public class RedisConfig {


    @Value("${spring.redis.host}")
    private String redisHost;

    @Primary
    @Bean("masterFactory")
    @ConfigurationProperties("spring.redis")
    public JedisConnectionFactory redisConnection(@Qualifier("redisPool") JedisPoolConfig poolConfig) {
//        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(clusterProperties.getNodes());
//        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(redisClusterConfiguration, poolConfig);
        JedisConnectionFactory connectionFactory = new JedisConnectionFactory(poolConfig);
        connectionFactory.setHostName(redisHost);
//        connectionFactory.set
        return connectionFactory;
    }

    /**
     * Type safe representation of application.properties
     */
    @Autowired
    ClusterConfigurationProperties clusterProperties;

    @Bean("redisPool")
    @ConfigurationProperties(prefix = "spring.redis.pool")
    public JedisPoolConfig redisPool() {
        return new JedisPoolConfig();
    }

    @Primary
    @Bean("redisTemplate")
    public RedisTemplate<String, String> redisTemplate(
            @Qualifier("masterFactory") RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringSerializer);
        redisTemplate.setHashKeySerializer(stringSerializer);
        redisTemplate.setHashValueSerializer(stringSerializer);
        redisTemplate.setValueSerializer(stringSerializer);
        return redisTemplate;
    }


}
