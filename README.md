# 网约车项目 3.0

## 一、服务拆分

### 业务层

| 模块   | 项目名                                        | 描述   |
|------|--------------------------------------------|------|
| 乘客端  | api-passenger(订单bean。@RequestBody 订单的bean) | 乘客端  |
| 司机端  | api-driver                                 | 司机端  |
| 司机听单 | api-listen-order                           | 司机听单 |

### 能力层

| 模块     | 项目名                                        |
|--------|--------------------------------------------|
| app升级  | service-app-update                         |
| 订单     | service-order(订单bean。@RequestBody 订单的bean) |
| 派单     | service-order-dispatch                     |
| 乘客用户管理 | service-passenger-user                     |
| 短信     | service-sms                                |
| 计价     | service-valuation                          |
| 验证码    | service-verification-code                  |
| 钱包     | service-wallet                             |
| 支付     | service-payment                            |
| 地图     | service-map                                |


### spring cloud 基础
所有服务都引用的一个 二方库。

| 模块       | 项目名                     |
|----------|-------------------------|
| 注册中心     | cloud-eureka            |
| 配置中心     | cloud-config-server     |
| 网关       | cloud-zuul              |
| 熔断监控(ui) | cloud-hystrix-dashboard |
| 健康检查     | cloud-admin             |
| 链路追踪     | cloud-zipkin-ui         |

## 二、微服务项目结构

```sh
|--online-taxi-parent-v4
	!-- 项目A
	|-- 项目B
```

单独项目

```sh
|--pom
|--src
	|--controller
	|--service
		impl
		接口
	|--dao
    	entity
    	mapper
    |--manager
    |--constant常量
    |--request 接受的参数bean
    |--response返回参数bean
|--resource
	|--mapper
		|--xxxxMapper.xml
	yml	
```


## 三、用到的技术

git, mysql,file,nacos,redis


## 四、第三方

短信服务（腾讯，阿里短信，华信）

语音服务：隐私号。（乘客和司机订单匹配后，A，B，X），录音。

文件服务oss：

地图：高德

消息推送：极光、透传、通知

支付：微信、支付宝

航旅纵横：查航班

发票：百望云

前端：vue、android、ios
