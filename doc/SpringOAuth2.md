#### Spring OAuth 

-----------------
  
  - Provider Type
    - Authorization Service 授权服务 
    - Resource Service 资源服务   
  - Provider Endpoint 
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

    
      