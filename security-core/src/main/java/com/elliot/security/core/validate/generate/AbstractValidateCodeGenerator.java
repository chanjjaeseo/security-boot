package com.elliot.security.core.validate.generate;

import com.elliot.security.core.config.bean.SecurityBootBean;
import com.elliot.security.core.validate.code.ValidateCode;

import java.util.Random;

public abstract class AbstractValidateCodeGenerator implements CodeGenerator{

    protected SecurityBootBean securityBootBean;

    public AbstractValidateCodeGenerator(SecurityBootBean securityBootBean) {
        this.securityBootBean = securityBootBean;
    }

    protected String generateCode(int length) {
        Random random = new Random();
        StringBuilder buffer = new StringBuilder();
        for(int i=0; i < length; i++) {
            int isNum = random.nextInt(1+1);
            if (isNum == 0) {
                int num = random.nextInt(9+1);
                buffer.append(num);
            } else {
                char character = (char) (97 + random.nextInt(25)+1);
                buffer.append(character);
            }
        }
        return buffer.toString();
    }

    public abstract ValidateCode generate();

}
