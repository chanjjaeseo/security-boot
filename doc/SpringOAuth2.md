#### Spring OAuth 
> Spring OAuth 主要针对APP实现。访问Web服务器的客户端具备Session存储的功能，所以我们可以利用Session存储
省略很多安全存储的工作。我们可以利用OAuth2的认证流程(基于JWT)作为APP各项安全认证的实现方式。
-----------------
  
  - Provider Type
    - Authorization Service 授权服务 
    Client Server 从这里获取授权
    
    - Resource Service 资源服务 
    访问的资源在这里，这里需要验证授权  
    
    这两个服务可以分开部署，也可以部署在一个服务上
  
  - Provider Endpoint 
        
      Endpoint即端点也就是一个提供服务的接口, Spring OAuth服务主要依赖两个Endpoint
      - AuthorizationEndpoint (/oauth/authorize):   获取access token
      - TokenEndpoint (/oauth/token):    获取 authorize token
  
  - Grant Type
  
      - authorization_code：授权码类型。
      - implicit：隐式授权类型。
      - password：资源所有者（即用户）密码类型。
      - client_credentials：客户端凭据（客户端ID以及Key）类型。
      - refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。

#### Spring Security OAuth2 Authorize Code Type

-----------------------------------------------

Spring Security OAuth 授权模式访问步骤

1. Client Server 让 Resource Owner携带参数访问Authorize Server 获取Authorize Code

这里把认证信息放在请求体中，Spring OAuth默认实现是放在请求头中
http://localhost:8090/oauth/authorize?response_type=code&client_id=[yourclientid]&redirect_uri=[yourappuri]

成功后认证服务器会让 Resource Owner 跳转到 Client Server(携带 AuthorizeCode)

2. Client Server 拿Authorize Code 换取 Access Token

http://localhost:8090/oauth/token?
grant_type=authorization_code
&code=[authorizecode]&client_id=[yourclientid]
&client_secret=[yourclientsecret]&redirect_uri=[yourappuri]

获取到响应
```
{
    "access_token": "da019a23-ed8d-4eef-9b45-8b0ac88d9427",
    "token_type": "bearer",
    "refresh_token": "11cc6d4f-f3b8-490c-b837-2fd65ef3a5b2",
    "expires_in": 7199,
    "scope": "all"
}
```

3. 访问服务

请求头中加入 
```
    Authorization  |  Bearer da019a23-ed8d-4eef-9b45-8b0ac88d9427
```

#### 实现OAuth2表单登陆

---------------------------------------------------
在表单登陆中，我们在web浏览器中采用表单登陆，经过Spring Security认证成功后，认证信息会放到session中。

而在APP中是不存在Session这个概念的，所以我们直接采用发放基于JWT形式的AccessToken就OK。

这里有两种方式:

- 第一种就是OAuth2的密码模式，

- 第二种就是基于表单登陆，登陆成功后发访token，用户拿token访问资源。

我们采用的是方案二:

向认证端点发送包含用户名密码的POST请求，验证成功后，发放AccessToken。

注意我们这里需要验证 client的信息，所以请求头中要携带clientId和clientScret。按照如下设置:

```
    Content-Type  | application/json;charset=UTF-8
    Authorization | Basic  base64[clientId:clientScret] 
```
    
      