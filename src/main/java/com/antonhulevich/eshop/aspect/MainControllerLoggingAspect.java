package com.antonhulevich.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class MainControllerLoggingAspect {
    @Pointcut("execution(* com.antonhulevich.eshop.controller.MainController.*(..))")
    public void allMethodsOfMainControllerAdvice(){}

    @Before("allMethodsOfMainControllerAdvice()")
    public void beforeMethodOfMainControllerAdvice(JoinPoint joinPoint){
        System.out.println("AOP @Before: calling method " + joinPoint.getSignature());
    }

    @AfterReturning("allMethodsOfMainControllerAdvice()")
    public void afterMethodOfMainControllerAdvice(JoinPoint joinPoint) {
        System.out.println("AOP @AfterReturning: finished method " + joinPoint.getSignature());
    }
}
