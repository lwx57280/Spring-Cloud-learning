package com.learn.user.controller;

import com.google.common.collect.Lists;
import com.learn.user.domain.User;
import com.learn.user.repository.UserRepository;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @PostMapping("/user")
    public User postUser(@RequestBody User user) {
        return user;
    }

    // 该请求不会成功
    @GetMapping("/get-user")
    public User getUser(User user) {
        return user;
    }

    @GetMapping("list-all")
    public List<User> listAll() {
        ArrayList<User> list = Lists.newArrayList();
        User user = new User(1L, "zhangsan");
        User user2 = new User(2L, "zhangsan");
        User user3 = new User(3L, "zhangsan");
        list.add(user);
        list.add(user2);
        list.add(user3);
        return list;
    }
}
