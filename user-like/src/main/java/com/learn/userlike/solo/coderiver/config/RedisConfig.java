package com.learn.userlike.solo.coderiver.config;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;


/**
 * <p>Redis缓存配置</p>
 *
 * @Author: li cong zhi
 * @CreateDate: 2019/12/22 19:47
 * @UpdateUser: li cong zhi
 * @UpdateDate: 2019/12/22 19:47
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 * @EnableCaching 启用Cache注解支持
 */
@Configuration
@EnableCaching
public class RedisConfig extends CachingConfigurerSupport {

    /**
     * 日志
     */
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Bean
    public RedisTemplate<?, ?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<Object, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        // 使用Jackson2JsonRedisSerialize 替换默认序列化
        @SuppressWarnings("rawtypes")
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);

        jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
        LettuceConnectionFactory factory = (LettuceConnectionFactory) redisConnectionFactory;
        LOGGER.info("spring.redis.database: {}", factory.getDatabase());
        LOGGER.info("spring.redis.host: {}", factory.getHostName());
        LOGGER.info("spring.redis.port: {}", factory.getPort());
        LOGGER.info("spring.redis.timeout: {}", factory.getTimeout());
        LOGGER.info("spring.redis.password: {}", factory.getPassword());

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // key序列化方式,但是如果方法上有Long等非String类型的话，会报类型转换错误
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        // 设置value的序列化规则和 key的序列化规则
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        // 调用后完成设置
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * redis缓存管理
     *
     * @param connectionFactory
     * @return
     * @throws
     * @author li cong zhi
     * @date 2019/6/19 8:59
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory connectionFactory) {

        //  包装成SerializationPair类型
        RedisSerializationContext.SerializationPair serializationPair =
                RedisSerializationContext.SerializationPair.fromSerializer(getRedisSerializer());
        //  redis默认配置文件,并且设置过期时间
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                // 设置缓存有效期60秒
                .entryTtl(Duration.ofHours(60 * 2))
                .serializeValuesWith(serializationPair);

        //  RedisCacheManager 生成器创建
        return RedisCacheManager
                .builder(RedisCacheWriter.nonLockingRedisCacheWriter(connectionFactory))
                .cacheDefaults(redisCacheConfiguration).build();
    }

    private RedisSerializer<Object> getRedisSerializer() {
        //return new GenericFastJsonRedisSerializer();
        return new Jackson2JsonRedisSerializer(Object.class);
    }


    /**
     * 缓存key生成策略
     *
     * @return KeyGenerator
     * @throws
     * @author li cong zhi
     * @date 2019/6/19 9:15
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        /**
         * 设置自动key的生成规则，配置spring boot的注解，进行方法级别的缓存
         *
         * 使用：进行分割，可以很多显示出层级关系这里其实就是new了一个KeyGenerator对象，
         * 只是这是lambda表达式的写法，感觉很好用，
         */
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append(":" + String.valueOf(obj));
            }
            String rsToUse = String.valueOf(sb);
            LOGGER.info("自动生成Redis Key -> [{}]", rsToUse);
            return rsToUse;
        };
    }


    /**
     * <p>redis统一异常处理,通过继承CachingConfigurerSupport覆写errorHandler()</p>
     * <p>如果redis出现异常就会进入errorHandler()中的某个方法，使得程序能够继续执行下去查询数据库而不是中断。</p>
     *
     * @return CacheErrorHandler
     * @author li cong zhi
     * @date 2019/6/18 18:06
     */
    @Override
    public CacheErrorHandler errorHandler() {
        return new RedisCacheErrorHandler();
    }

    /**
     * 如果cache出错， 我们会记录在日志里，方便排查，比如反序列化异常
     *
     * @author li cong zhi
     * @return
     * @exception
     * @date 2019/6/20 16:44
     */
    class RedisCacheErrorHandler extends SimpleCacheErrorHandler {
        @Override
        public void handleCacheGetError(RuntimeException e, Cache cache, Object o) {
            LOGGER.error(("---errorHandler#handleCacheGetError:" + cache.getName()));
        }

        @Override
        public void handleCachePutError(RuntimeException e, Cache cache, Object o, Object o1) {
            LOGGER.error(("---errorHandler#handleCachePutError:" + cache.getName()));
        }

        @Override
        public void handleCacheEvictError(RuntimeException e, Cache cache, Object o) {
            LOGGER.error(("---errorHandler#handleCacheEvictError:" + cache.getName()));
        }

        @Override
        public void handleCacheClearError(RuntimeException e, Cache cache) {
            LOGGER.error(("---errorHandler#handleCacheClearError:" + cache.getName()));
        }
    }
}
