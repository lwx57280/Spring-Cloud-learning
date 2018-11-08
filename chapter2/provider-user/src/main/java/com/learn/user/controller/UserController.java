package com.learn.user.controller;

import com.learn.user.domain.User;
import com.learn.user.repository.UserRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private UserRepository repository;


    @Autowired
    @Qualifier("eurekaClient")
    private EurekaClient eurekaClient;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/simple/{id}")
    public User findById(@PathVariable Long id){
        return this.repository.findOne(id);
    }

    @GetMapping("/eureka-instance")
    public String serviceUrl() {
        InstanceInfo instance = this.eurekaClient.getNextServerFromEureka("provider-user", false);
        logger.info("/serviceUrl,host:"+instance.getHostName()+" , service_id:"+instance.getInstanceId());
        return instance.getHomePageUrl();
    }

    @GetMapping("/instance-info")
    public ServiceInstance showInfo() {
        ServiceInstance localServiceInstance = this.discoveryClient.getLocalServiceInstance();
        return localServiceInstance;
    }
}
