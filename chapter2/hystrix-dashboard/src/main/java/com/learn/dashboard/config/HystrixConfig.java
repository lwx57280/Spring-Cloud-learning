//package com.learn.dashboard.config;
//
//import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
//import org.springframework.boot.web.servlet.ServletRegistrationBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * @Author
// * @Date
// * @Version v1.0
// */
//@Configuration
//public class HystrixConfig {
//
//https://blog.csdn.net/xiaoluo033/article/details/80952786
//    @Bean
//    public HystrixMetricsStreamServlet hystrixMetricsStreamServlet(){
//        return new HystrixMetricsStreamServlet();
//    }
//
//    @Bean
//    public ServletRegistrationBean registrationBean(HystrixMetricsStreamServlet streamServlet){
//        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
//        registrationBean.setServlet(streamServlet);
//        //是否启用该registrationBean
//        registrationBean.setEnabled(true);
//        registrationBean.addUrlMappings("/hystrix.stream");
//        return registrationBean;
//    }
//}
