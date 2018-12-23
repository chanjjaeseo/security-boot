package com.elliot.security.core.validate.processor;

import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.validate.code.ValidateCode;
import com.elliot.security.core.util.ValidateCodeUtil;
import com.elliot.security.core.validate.generate.CodeGenerator;
import com.elliot.security.core.validate.code.ImageValidateCode;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

public class SessionImageCodeProcessor extends SessionValidateCodeProcessor {

    public SessionImageCodeProcessor(CodeGenerator codeGenerator, String sessionName) {
        super(codeGenerator, sessionName);
    }

    @Override
    public void save(HttpServletRequest request, ValidateCode validateCode) {
        ValidateCodeUtil.setValidateCodeToSession(request, SecurityConstant.ValidateCode.IMAGE_VALIDATE_CODE_SESSION_NAME, validateCode);
    }

    @Override
    protected void send(HttpServletRequest request, HttpServletResponse response, ValidateCode validateCode) {
        BufferedImage bufferedImage = ((ImageValidateCode)validateCode).getBufferedImage();
        try {
            ImageIO.write(bufferedImage, "JPEG", response.getOutputStream());
        } catch (Exception e){
            logger.error("生成图片验证码失败", e);
        }
    }
}
