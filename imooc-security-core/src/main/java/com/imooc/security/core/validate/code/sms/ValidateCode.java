package com.imooc.security.core.validate.code.sms;

import java.time.LocalDateTime;

/**
 * 验证码
 */
public class ValidateCode {

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

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(LocalDateTime expireTime) {
        this.expireTime = expireTime;
    }
}
