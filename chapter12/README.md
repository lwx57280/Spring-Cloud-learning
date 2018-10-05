**Spring Cloud微服务**
-------------------------
   
@SpringBootApplication注解
-----------------------------	

这里主要关注@SpringBootApplication注解，它包括三个注解：

    @Configuration：表示将该类作用springboot配置文件类。

    @EnableAutoConfiguration:表示程序启动时，自动加载springboot默认的配置。

    @ComponentScan:表示程序启动是，自动扫描当前包及子包下所有类。


SpringCloud
------------
    
Spring Cloud是一个基于Spring Boot实现的微服务架构开发工具。它为微服务架构中涉及的配置管理、服务治理、断路器
、智能路由、微代理、控制总线、全局锁、决策竞选、分布式会话和集群状态管理操作提供了一种简单的开发方式。

Spring Cloud 包含多个子项目（针对分布式系统中涉及的多个不同开源产品，还可能会新增），如下所述：    	
    
*  Spring Cloud Config:配置管理工具，支持使用Git存储配置内容，可以使用它实现应用配置的外部存储，并支持客户端
   配置刷新、加密/解密配置内容等。
  
*  Spring Cloud Netflix：核心组件，对多个Netflix OSS开源套件进行整合。
    > Eureka:服务治理组件,包含服务注册中心、服务注册与发现机制的实现。
    
    > Hystrix:容错管理组件，实现断路器模式,帮助服务依赖中出现的延迟和为故障提供强大的容错能力。
    
	> Ribbon:客户端负载均衡的服务调用组件。
	
	> Feign:基于Ribbon和Hystrix的声明式服务调用组件。
	
	> Zuul:网关组件，提供智能路由、访问过滤等功能。
	
	> Archaius:外部化配置组件。
	
* Spring Cloud Bus:事件、消息总线、用于传播集群中的状态变化或事件，以触发后续的处理，比如用来动态刷新配置等。

* Spring Cloud Cluster:正对Zookeeper、Redis、Hazelcast、Consul的选举算法和通用状态模式的实现。
* Spring Cloud Cloudfoundry:与Pivotal Cloudfoundry的整合支持。
* Spring Cloud Stream：通过Redis、Rabbit或者Kafka实现的消费微服务，可以通过简单的声明式模型来发送和接收消息。
* Spring Cloud AWS:用于简化整合Amazon Web Service的组件。
* Spring Cloud Security:安全工具包，提供在Zuul代理中对OAuth2客户端请求的中继器。
* Spring Cloud Sleuth:Spring Cloud应用的分布式跟踪实现，可以完美整合Zipkin。
* Spring Cloud Zookeeper:基于Zookeeper的服务发现与配置管理组件。
* Spring Cloud Starters: Spring Cloud的基础组件，它是基于Spring Boot风格项目的基础依赖模块。
* Spring Cloud CLI:用于在Groovy中快速创建Spring Cloud应用的Spring Boot CLI插件。

	Eureka注册服务中心 三个重要的角色：服务注册中心、服务提供者、服务消费者。
	
	单点模式 注册服务中心只有一个。
	
	高可用模式 服务中心可用将自己作为服务提供者，注册到相关的服务中心去。服务中心可以有多个，集群的方式。


SpringCloud Ribbon 客户端负载均衡
	RibbonEurekaAutoConfiguration 自动配置类
	开启负载均衡的步骤：	
	
		1、多个服务提供者，注册到服务中心
		2、服务消费者通过调用被@LoadBalanced注解修饰过的restTemplate
	RestTemplate 与 Ribbon整合使用 详解
		1、RestTemplate 基本使用 GET POST PUT DELETE
		2、RestTemplate 与 Ribbon 整合
		
		3、重点源码：LoadBalancerClient LoadBalancerAutoConfiguration
	springCloud Feign 声明是服务调用
	
	
	微服务架构的九大特性
		1、服务组件化。
		2、按业务组织团队。
		3、做“产品”的态度。
		4、智能端点与哑道。
		5、去中心化治理。
		6、去中心化管理数据。
		7、基础设施自动化。
			自动化测试：每次部署前的强心剂，尽可能地获得对正在运行的软件的信息。
			自动化不是：解放繁琐枯燥的重复操作以及对多环境的配置管理。
		8、容错设计。
		9、演进式设计。
		
	微服务优点：
		1、易于开发和维护。
		2、启动较快。
		3、局部修改容易部署。
		4、技术栈不受限。
		5、按需伸缩。
		6、DevOps.
		
	微服务带来的挑战
		1、运维要求较高。
		2、分布式的复杂性。
		3、接口调整成本高。
		4、重复劳动。
	
	微服务设计原则：
		1、单一职责原则。
		2、服务自治原则。
		3、轻量级通信原则。
		4、接口明确原则。
		
服务提供者：服务的被调用方（即，为其他服务提供服务）
服务消费者：服务的调用方（即：依赖其他服务的服务）

服务注册表：
------------

![服务注册与发现](https://github.com/lwx57280/Spring-Cloud-learning/blob/master/chapter12/img-folder/Discovery.jpg)
	
	服务注册表是一个记录当前可用服务实例的网络信息的数据库，是服务发现机制的核心。服务注册表提供查询API
	和管理API，使用查询API获得可用的服务实例，使用管理API实现注册和注销！
	
	
Eureka简介：
-----------    
	Eureka是Netflix开发的服务发现框架，本身是一个基于REST的服务，主要用于定位运行AWS域中的中间层服务，
	以达到负载均衡和中间层服务故障转移的目的。Spring Cloud将它集成在其子项目Spring-Cloud-netflix中，
	以实现Spring Cloud的服务发现个功能。
	
Eureka Client是一个java客户端，用于简化与Eureka Server的交互，客户端同时也具备一个内置的、使用轮询（round-robin）
负载算法的负载均衡器。在应用启动后，将会向Eureka Server发送心跳（默认周期为30s一次），如果Eureka Server
在多个心跳周期内没有接收到某个节点的心跳，Eureka Server将会从服务注册表中把这个服务节点移除（默认90s）

Eureka Server之间将会通过复制的方式完成数据同步

Eureka还提供了客户端缓存的机制，即使所有的Eureka Server都挂掉，客户端依然可以利用缓存中的信息消费其他服务API。
综上所述，Eureka 通过心跳检测、	健康检查、客户端缓存等机制，确保了系统的高可用、灵活性和可伸缩性。	


注解@EnableDiscoveryClient，@EnableEurekaClient的区别
----------------------------------------------------
	@EnableDiscoveryClient基于spring-cloud-commons。
	@EnableEurekaClient基于spring-cloud-netflix。

Ribbon工作时分为两步：
--------------------
	
![Ribbon架构](https://github.com/lwx57280/Spring-Cloud-learning/blob/master/chapter12/img-folder/Ribbon.jpg)
    
	第一步先选择Eureka Server，它优先选择在同一个Zone且负载较少的Server；
	第二步再根据用户指定的策略，从Server取到的服务注册列表中选择一个地址。其中Ribbon
	提供了多种策略，例如轮询round robin、随机Random、根据响应时间加权等。
