package com.learn.discoveryeureka.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * Zuul过滤器
 *
 * @Author
 * @Date
 * @Version v1.0
 */
public class PreZuulFilter extends ZuulFilter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreZuulFilter.class);

    /**
     * 过滤器类型
     * 顺序: pre ->routing -> post ,以上3个顺序出现异常时都可以触发error类型的filter
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     *
     * 同filterType类型中，order值越大，优先级越低
     * @return
     */
    @Override
    public int filterOrder() {
        return FilterConstants.DEBUG_FILTER_ORDER;
    }

    /**
     * 是否应该执行该过滤器，如果是false，则不执行该filter
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 执行业务操作，可执行sql,nosql等业务
     */
    @Override
    public Object run() {

        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        String host = request.getRemoteHost();
        PreZuulFilter.LOGGER.info("请求的host:{}", host);
        return null;
    }
}
