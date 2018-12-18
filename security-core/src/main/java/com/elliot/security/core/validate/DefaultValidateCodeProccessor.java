package com.elliot.security.core.validate;

import com.elliot.security.core.config.bean.SecurityBootBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;

@Component
public class DefaultValidateCodeProccessor {

    @Autowired
    private SecurityBootBean securityBootBean;

    private SMSCodeGenerator smsCodeGenerator = new SMSCodeGenerator();

    private ImageCodeGenetor imageCodeGenetor = new ImageCodeGenetor();

    public void createSMSCode() {

    }

    public void createImageCode() {

    }


    private class SMSCodeGenerator extends AbstractValidateCodeGenerator {

        @Override
        public ValidateCode generate() {
            int smsCodeLength = securityBootBean.getCode().getSms().getLength();
            int expiredMinutes = securityBootBean.getCode().getSms().getExpiredMinutes()
            String code = generateCodeString(smsCodeLength);
            return new ValidateCode(code, expiredMinutes);
        }
    }

    private class ImageCodeGenetor extends AbstractValidateCodeGenerator {

        @Override
        public ValidateCode generate() {
            int imageCodeWidth = securityBootBean.getCode().getImage().getWidth();
            int imageCodeHeight = securityBootBean.getCode().getImage().getHeight();
            int imageCodeLength = securityBootBean.getCode().getImage().getLength();
            int expiredMinutes = securityBootBean.getCode().getImage().getExpiredMinutes();
            String code = generateCodeString(imageCodeLength);
            BufferedImage bufferedImage = null;
            return new ImageValidateCode(code, expiredMinutes, bufferedImage);
        }

        private BufferedImage generateBufferedImage() {
            return null;
        }
    }

}
