package com.active4j.hr.core.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @title RedisConfig.java
 * @description 
		  redis 配置类
 * @time  2019年12月17日 下午4:41:31
 * @author 麻木神
 * @version 1.0
*/
@Configuration
public class RedisConfig {


    @Bean
    @SuppressWarnings("all")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(factory);
        
        FastJson2JsonRedisSerializer fastJsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        // key采用String的序列化方式
        template.setKeySerializer(stringRedisSerializer);
        // hash的key也采用String的序列化方式
        template.setHashKeySerializer(stringRedisSerializer);
        // value序列化方式采用jackson
        template.setValueSerializer(fastJsonRedisSerializer);
        // hash的value序列化方式采用jackson
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }
	
}
