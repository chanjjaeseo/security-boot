1. Maven多模块，子模块依赖父模块，且子模块作为Web容器入口(SpringBootApplication)，
如果不指定ComponentScan的路径，则需要把启动类放在公有包路径下，如依赖com.elliot.security.app
则放在他的父节点包下如con.elliot.security。
2. Spring OAuth 
  - Provider Type
    - Authorization Service 授权服务 
    - Resource Service 资源服务   
  - Provider Endpoint 
      - AuthorizationEndpoint (/oauth/token):   获取access token
      - TokenEndpoint (/oauth/authorize):    获取 authorize token
  - Grant Type
      - authorization_code：授权码类型。
      - implicit：隐式授权类型。
      - password：资源所有者（即用户）密码类型。
      - client_credentials：客户端凭据（客户端ID以及Key）类型。
      - refresh_token：通过以上授权获得的刷新令牌来获取新的令牌。
      
   实现OAuth资源服务器需要 OAuth2AuthenticationProcessingFilter
      