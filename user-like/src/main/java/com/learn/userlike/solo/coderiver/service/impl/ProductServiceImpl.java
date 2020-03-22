package com.learn.userlike.solo.coderiver.service.impl;

import com.google.common.collect.Maps;
import com.learn.userlike.solo.coderiver.config.RedisLock;
import com.learn.userlike.solo.coderiver.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private RedisLock redisLock;

    /**
     * 设置超时时间10秒
     */
    private static final int TIMEOUT = 10 * 1000;

    /**
     * 例如国庆大甩卖 图书大甩卖 库存 1000 件
     */

    /**
     * 库存
     */
    static Map<String, Integer> products;
    /**
     * 库存余量
     */
    static Map<String, Integer> stock;
    /**
     * 抢购成功者信息
     */
    static Map<String, String> orders;

    static {
        products = Maps.newHashMap();
        stock = Maps.newHashMap();
        orders = Maps.newHashMap();
        products.put("book", 1000);
        stock.put("book", 1000);
    }

    public String queryMap(String productId) {
        return "国庆图书大甩卖，库存 " + products.get(productId) + " 件，现余 " + stock.get(productId) + " 件，已被抢购 " + orders.size() + " 件";
    }

    /**
     * 第一种方法    synchronized 锁机制，解决高并发产生的超卖问题 但效率大大降低 不推荐使用
     * 第二种方法    使用 Redis 分布式锁，解决高并发产生的超卖问题 并且效率相对高很多
     *
     * @param productId
     * @return
     */
    @Override
    public String orderGoods(String productId) {
        // 加锁
        Long time = System.currentTimeMillis() + TIMEOUT;
        // 加锁失败 说明有人正在使用
        if (!redisLock.lock(productId, String.valueOf(time))) {
            log.info("抢购失败，请再试试吧...");
            throw new RuntimeException("服务器刚才好像睡着了，请再试试吧...");
        }
        // 先获取商品余量
        Integer number = stock.get(productId);
        if (number == 0) {
            throw new RuntimeException("商品已抢购完，请您下次再来，谢谢您的理解...");
        } else {
            // 模拟下单（不同用户拥有不同ID）
            orders.put(String.valueOf(UUID.randomUUID()), productId);
            // 减库存
            number = number - 1;
            // 模拟延迟
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                stock.put(productId, number);
                // 解锁
                redisLock.unLock(productId, String.valueOf(time));
            }
        }
        log.info("共抢购 {} 件，抢购详情:{}", orders.size(), orders);
        //再返回商品的抢购详情
        return this.queryMap(productId);
    }

    @Override
    public String queryGoods(String productId) {
        return this.queryMap(productId);
    }
}
