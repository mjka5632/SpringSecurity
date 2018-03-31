package com.imooc.security.core.validate.code.image;

import com.imooc.security.core.validate.code.sms.ValidateCode;

import java.awt.image.BufferedImage;

/**
 * 图形验证码
 *
 * 是验证码的子类
 *多了一个图片的属性
 */
public class ImageCode extends ValidateCode{
    private BufferedImage image;

    /**
     * @param image
     * @param code
     * @param expireIn 传入的是过期的时间间隔
     */
    public ImageCode(BufferedImage image, String code, int expireIn) {
        super(code,expireIn);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }






}
