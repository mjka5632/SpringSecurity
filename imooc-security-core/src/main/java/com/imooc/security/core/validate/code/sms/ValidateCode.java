package com.imooc.security.core.validate.code.sms;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 验证码
 */
@Data
public class ValidateCode implements Serializable {

    private String code;

    private LocalDateTime expireTime;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expireTime);
    }

    public ValidateCode(String code, int expirInTime) {
        this.code = code;
        this.expireTime = LocalDateTime.now().plusSeconds(expirInTime);
    }

    public ValidateCode(String code, LocalDateTime expireTime) {
        this.code = code;
        this.expireTime =expireTime;
    }

}
