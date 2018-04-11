package com.imooc.validator;

import com.imooc.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
//因为继承了ConstraintValidator<A,T> （A: 代表用于哪个注解 T:用于哪个类型）  所以不用注解
public class MyConstraintValidator implements ConstraintValidator<MyConstraint,Object>{
    @Autowired
    private HelloService helloService;
    @Override
    public void initialize(MyConstraint myConstraint) {
        System.out.println("my Constraint init");
    }
    /**
     * o代表我们写的message的值
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        helloService.greeting("tom");
        System.out.println("Value"+o);
        return false;
    }
}
