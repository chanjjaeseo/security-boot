package com.elliot.security.core.validate.sms;

import com.elliot.security.core.validate.ValidateCode;

import java.awt.image.BufferedImage;

public class ImageValidateCode extends ValidateCode {

    private BufferedImage bufferedImage;

    public ImageValidateCode (String code, int delayMinutes, BufferedImage bufferedImage) {
        super(code, delayMinutes);
        this.bufferedImage = bufferedImage;
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

}
