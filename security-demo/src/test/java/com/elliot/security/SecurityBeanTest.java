package com.elliot.security;

import com.elliot.security.core.config.bean.SecurityBootBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityBeanTest {

    @Autowired
    private SecurityBootBean securityBootBean;

    @Test
    public void test() {
        System.out.println("===========================");
        System.out.println(securityBootBean.getBrowser().getTokenValiditySeconds());
        System.out.println("===========================");
    }

}
