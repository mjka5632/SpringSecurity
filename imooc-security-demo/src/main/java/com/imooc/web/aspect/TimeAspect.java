package com.imooc.web.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;

import java.util.Date;

//@Aspect
//@Component
public class TimeAspect {
    @Around("execution(* com.imooc.web.controller.UserController.*(..))")
    public Object handleControllerMethod(ProceedingJoinPoint pjp) throws  Throwable{
        System.out.println("time aspect start");
        //打印参数
        Object[] args = pjp.getArgs();
        for(Object arg:args){
            System.out.println("arg is"+arg);
        }
        long startTime = System.currentTimeMillis();

        Object proceed = pjp.proceed();
        System.out.println("time aspect 耗时："+(System.currentTimeMillis()-startTime));
        System.out.println("time aspect end");
        return proceed;

    }
}
