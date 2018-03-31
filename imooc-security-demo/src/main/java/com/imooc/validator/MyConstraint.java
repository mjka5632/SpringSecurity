package com.imooc.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//注解标注的范围
@Target({ElementType.METHOD,ElementType.FIELD})
//运行时的注解retention(保留)
@Retention(RetentionPolicy.RUNTIME)
//校验逻辑控制
@Constraint(validatedBy = MyConstraintValidator.class)
public @interface MyConstraint {
    //这三个属性是必备的
    String message() ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
