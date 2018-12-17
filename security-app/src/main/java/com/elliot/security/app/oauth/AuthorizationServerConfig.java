package com.elliot.security.app.oauth;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig{


//    /**
//     * tokenKey的访问权限表达式配置
//     */
//    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
//        security.tokenKeyAccess("permitAll()");
//    }
//
//    /**
//     * 客户端配置
//     */
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
//        OAuth2ClientProperties[] auth2ClientProperties = new OAuth2ClientProperties[1];
//        auth2ClientProperties[0] = new OAuth2ClientProperties();
//        auth2ClientProperties[0].setClientId("security-boot");
//        auth2ClientProperties[0].setClientSecret("security-boot-secret");
//
//        if (ArrayUtils.isNotEmpty(auth2ClientProperties)) {
//            for (OAuth2ClientProperties client : auth2ClientProperties) {
//                builder.withClient(client.getClientId())
//                        .secret(client.getClientSecret())
//                        .authorizedGrantTypes("refresh_token", "authorization_code", "password")
//                        .accessTokenValiditySeconds(7200)
//                        .refreshTokenValiditySeconds(2592000)
//                        .scopes("all");
//            }
//        }
//    }

}
