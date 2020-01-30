package com.learn.userlike.solo.coderiver.config;

import org.redisson.Redisson;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * redisson配置
 */
@Configuration
public class RedissonManager {
    @Value("${spring.redis.clusters}")
    private String cluster;

    @Value("${spring.redis.password}")
    private String password;

    @Bean("redissonLock")
    public Redisson getRedisson() {
        String[] nodes = cluster.split(",");
        // redisson版本是3.5，集群的ip前面要加上“redis://”，不然会报错，3.2版本可不加
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = "redis://" + nodes[i];
        }
        Config config = new Config();
        config.useClusterServers()      // 这是用的集群Server
                .setScanInterval(2000)          //设置集群状态扫描时间
                .addNodeAddress(nodes)
                .setPassword(password);
        return (Redisson) Redisson.create(config);
    }
}
