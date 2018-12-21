package com.elliot.security.core.validate.processor;

import com.elliot.security.core.validate.ValidateCode;

import java.util.Random;

public abstract class AbstractValidateCodeGenerator{

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

    protected abstract ValidateCode generate();

}
