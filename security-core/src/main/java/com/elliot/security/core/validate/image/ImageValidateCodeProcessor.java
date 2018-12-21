package com.elliot.security.core.validate.image;

import com.elliot.security.core.config.bean.SecurityBootBean;
import com.elliot.security.core.constant.SecurityConstant;
import com.elliot.security.core.util.WebUtil;
import com.elliot.security.core.validate.ValidateCodeUtil;
import com.elliot.security.core.validate.processor.AbstractValidateCodeGenerator;
import com.elliot.security.core.validate.processor.AbstractValidateCodeProcessor;
import com.elliot.security.core.validate.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor {

    @Autowired
    private SecurityBootBean securityBootBean;

    private ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();


    @Override
    public ValidateCode generate() {
        return imageCodeGenerator.generate();
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

    @Override
    public void save(HttpServletRequest request, ValidateCode validateCode) {
        ValidateCodeUtil.setValidateCodeToSession(request, SecurityConstant.ValidateCode.IMAGE_VALIDATE_CODE_SESSION_NAME, validateCode);
    }

    private class ImageCodeGenerator extends AbstractValidateCodeGenerator {

        @Override
        public ValidateCode generate() {
            int width = securityBootBean.getCode().getImage().getWidth();
            int height = securityBootBean.getCode().getImage().getHeight();
            int imageCodeLength = securityBootBean.getCode().getImage().getLength();
            int expiredMinutes = securityBootBean.getCode().getImage().getExpiredMinutes();
            String code = generateCode(imageCodeLength);
            BufferedImage bufferedImage = generateBufferedImage(width, height, code);
            return new ImageValidateCode(code, expiredMinutes, bufferedImage);
        }

        private BufferedImage generateBufferedImage(int width, int height, String code) {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

            Graphics g = image.getGraphics();

            Random random = new Random();

            g.setColor(getRandColor(200, 250));
            g.fillRect(0, 0, width, height);
            g.setFont(new Font("Times New Roman", Font.BOLD, 20));
            g.setColor(getRandColor(160, 200));
            for (int i = 0; i < 155; i++) {
                int x = random.nextInt(width);
                int y = random.nextInt(height);
                int xl = random.nextInt(12);
                int yl = random.nextInt(12);
                g.drawLine(x, y, x + xl, y + yl);
            }

            char[] charSeq = code.toCharArray();
            for (int i = 0; i < code.length(); i++) {
                g.setColor(new Color(20 + random.nextInt(110),
                        20 + random.nextInt(110),
                        20 + random.nextInt(110)));
                g.drawString(String.valueOf(charSeq[i]), 13 * i + 6, 16);
            }

            g.dispose();
            return image;
        }

        private Color getRandColor(int fc, int bc) {
            Random random = new Random();
            if (fc > 255) {
                fc = 255;
            }
            if (bc > 255) {
                bc = 255;
            }
            int r = fc + random.nextInt(bc - fc);
            int g = fc + random.nextInt(bc - fc);
            int b = fc + random.nextInt(bc - fc);
            return new Color(r, g, b);
        }
    }

}
