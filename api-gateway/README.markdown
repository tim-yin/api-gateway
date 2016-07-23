# 配置
1.application.properties 

1.1 内嵌 server 启动端口
1.2 调用 后台聚合服务的 协调器 zookeeper 地址 
1.3 日志信息

2.routeRule.properties
主要放置 uri 对应  后台聚合服务的  映射文件
唯一确定前端请求: interface_version + uri +  requestMethod
唯一确定后台聚合服务: group  + interface + method + version

入参 多基本类型参数支持  在配置文件配置 serviceEnumInput：
e.g:
{
    "uri":"dashboard.test",
    "requestMethod":"get",
    "serviceInterfaceName": "com.niwodai.fortune.endpoint.MockAggregationServiceApi",
    "serviceMethod":"testNormalType",
    "serviceVersion":"v1.0",
    "serviceEnumInput":"id,address"
}

入参 inputDto 配置:
e.g:
{
    "uri":"dashboard.login",
    "requestMethod":"get",
    "serviceInterfaceName": "com.niwodai.fortune.endpoint.MockAggregationServiceApi2",
    "serviceMethod":"acquireData2",
    "serviceVersion":"v1.0",
    "serviceInputDto":"com.niwodai.fortune.endpoint.impl.Product"
}

入参  无参或者map 结构的配置 入参啥都不用配置后台自动识别:
e.g:
{
    "uri":"dashboard.login",
    "requestMethod":"get",
    "serviceInterfaceName": "com.niwodai.fortune.endpoint.MockAggregationServiceApi2",
    "serviceMethod":"acquireData2",
    "serviceVersion":"v1.0"
}



例子 url:http://localhost:8089/dashboard/product?name=yin&age=18
这里uri资源配资成 dashboard.product  用"."做分割  
后面 uri 和  requestMethod 做联合key 也是用的"."做连接  
比如这里就是  dashboard.product.get 作为前台唯一key 对应后台唯一聚合服务

3. spring-metrics.xml 放置的是api接口度量配置-主要使用了metrics-spring (目前这种自动配置度量方式放弃)
可以查看metries 入口: RestApiTimerManager.java   
目前策略是打印到目录  /opt/metrics 下会生成对应 csv文件 以10分钟一次的频率做写入 

4.exception  可以做自定义扩展
加一类自定义异常，只要添加2个文件  参考 : 
BusinessException.java  
BusinessExceptionResponseBuilder.java  
同时在 OpenApiHandlerExceptionResolver 类里添加 注册
参考:L42 add(new BusinessExceptionResponseBuilder());


5.目前业务验签 做成了chain handler  随业务变化可随意扩展
参考 入口：Validator.java
必填参数："app_version","version", "interface_version", "channel", "current_time", "sign"

