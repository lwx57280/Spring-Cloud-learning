package com.learn.hello.controller;


import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {


    private final Logger logger = Logger.getLogger(getClass());


    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;


    @Autowired
    private DiscoveryClient discoveryClient;

    //@RequestMapping(value = "/hello",method = RequestMethod.GET)
    @GetMapping("/eureka-instance")  // @GetMapping是一个组合注解 是@RequestMapping(method = RequestMethod.GET)的缩写
    public String index(){
        InstanceInfo instance = eurekaClient.getNextServerFromEureka("HELLO-SERVICE", false);
        logger.info("/hello,host:"+instance.getHostName()+" , service_id:"+instance.getInstanceId());
        return "Hello World";
    }

    @GetMapping("/instance-info")
    public ServiceInstance showInfo(){
        ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
        return localServiceInstance;
    }

}
