### 认证处理逻辑

认证处理逻辑即在认证后的动作，例如用户认证成功后跳转至指定界面，认证失败后提示失败信息。

通过实现认证成功和认证失败处理器，即可按照自定义的逻辑完成认证成功/失败的动作。

- 认证成功处理器 

实现接口 AuthenticationSuccessHandler

SavedRequestAwareAuthenticationSuccessHandler 认证成功后跳转至原请求url

- 认证失败处理器

实现接口 AuthenticationFailureHandler

### 记住我功能

记住我功能通过在用户浏览器的Cookie中写入Token，在服务器中也保存一份副本，当session失效用户再次访问服务时，

Spring Security会替我们检查用户中Cookie的内容，并在数据库中检索用户信息，实现基于token的验证，

当认证成功后用户即可访问受保护的资源，这些过程用户是没有感知的。

需要完成下面的内容

1. 在Security Config中配置记住我功能

2. 完成tokenRepository的搭建（DB搭建）

tokenRepository有两种实现

- InMemoryTokenRepositoryImpl
把token 放在在内存中
- JdbcTokenRepositoryImpl
把token 放在数据库中

这里采用第二种实现，第一种方式比较吃JVM堆上内存，

token主要是插入和查找所以不存在锁的竞争，所以放在数据库效率会很高。


3. 表单请求字段中携带remember-me，参数为true 




