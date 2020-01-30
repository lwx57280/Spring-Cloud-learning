package com.learn.userlike.solo.coderiver.service;

public interface ProductService {

    /**
     * 根据商品ID抢购商品并且返回商品的抢购详情
     * @param productId
     * @return
     */
    String orderGoods(String productId);

    /**
     * 根据商品ID查询商品抢购详情
     * @param productId
     * @return
     */
    String queryGoods(String productId);
}
