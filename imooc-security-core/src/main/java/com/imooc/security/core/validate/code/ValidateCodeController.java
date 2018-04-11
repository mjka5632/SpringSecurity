package com.imooc.security.core.validate.code;

import com.imooc.security.core.properties.SecurityConstants;
import com.imooc.security.core.validate.code.sms.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码控制器
 */
@RestController
public class ValidateCodeController {
    public static final String SESSION_KEY = "SESSION_KEY_IMAGE_CODE";
    /**
     *
     */
    private SessionStrategy sessionStrategy = new HttpSessionSessionStrategy();

    /**
     * 图形验证码生成器
     */
    @Autowired
   private ValidateCodeGenerator imageCodeGenerator;
    /*
      验证码生成器
       @Autowired
    private ValidateCodeGenerator SmsCodeGenerate;
     */

    /**
     * 短信供应商
     */
    @Autowired
    private SmsCodeSender smsCodeSender;


    /**
     * 图形验证码生成器
     * @param request
     * @param response
     * @throws IOException
     */
  /*  @GetMapping("/code/image")
    public void createImageCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //图形验证码生成器
        ImageCode imageCode = (ImageCode)imageCodeGenerator.generateCode( new ServletWebRequest(request));
        //把生成的验证码存入session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, imageCode);
        //1.图片 2.图片格式 3.输出流
        ImageIO.write(imageCode.getImage(), "JPEG", response.getOutputStream());
    }

    @GetMapping("/code/sms")
    public void createSmsCode(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        //图形验证码生成器
        ValidateCode smsCode = SmsCodeGenerate.generateCode( new ServletWebRequest(request));
        //把生成的验证码存入session中
        sessionStrategy.setAttribute(new ServletWebRequest(request), SESSION_KEY, smsCode);
        //所传参数必须包含mobile这个参数
        String mobile= ServletRequestUtils.getRequiredStringParameter(request,"mobile");
        //短信供应商提供发送接口
        smsCodeSender.Send(mobile,smsCode.getCode());
    }*/

    @Autowired
    private ValidateCodeProcessorHolder validateCodeProcessorHolder;

    /**
     * 创建验证码，根据验证码类型不同，调用不同的 {@link ValidateCodeProcessor}接口实现
     *
     * @param request
     * @param response
     * @param type
     * @throws Exception
     */
    @GetMapping(SecurityConstants.DEFAULT_VALIDATE_CODE_URL_PREFIX + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        validateCodeProcessorHolder.findValidateCodeProcessor(type).create(new ServletWebRequest(request, response));
    }
}

