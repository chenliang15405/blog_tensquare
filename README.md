tensquare-blog
---
1. Initial Project
2. TODO LIST
- [ ] 验证码
- [ ] elasticsearch
- [ ] crawler
- [ ] AI
- [ ] 资源获取api
- [ x ] jenkins
- [ ] docker registry
- [ ] spring cloud config配置中心
- [ x ] 评论模块
- [ x ] jenkins自动化部署springcloud项目，基本Docker镜像（插件和Dockerfile两个版本）
- [ ] 评论列表和点赞等数据需要使用Redis缓存
- [ ] ES实现博客内容的搜索
- [ ] 数据库账号创建，限制ip访问
- [ ] Mysql主从配置
- [ ] Hystrix增加缓存 -- 默认Hystrix有请求缓存和断路器，可以在频繁请求的方法上使用缓存
- [ ] eureka增加security
- [ ] feign增加负载均衡 -- 自动默认Ribbon轮询
- [ ] Hystrix配置服务降级

### 页面统计
- [ ] 页面埋点统计
- [ ] PV 页面统计--String incr即可 （每天页面点击量，无需去重）
- [ ] UV 用户统计—— HyperLogLog统计用户每天访问量（每个用户每天点击只会统计一次），HyperLogLog更少的空间来统计，否则使用Set数据结构也可以实现，空间占用较大