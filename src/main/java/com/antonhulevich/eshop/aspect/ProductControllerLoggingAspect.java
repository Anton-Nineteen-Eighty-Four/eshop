package com.antonhulevich.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ProductControllerLoggingAspect {

    @Before("execution(public String list(..))")
    public void beforeListOfMethodAdvice(JoinPoint joinPoint){
        System.out.println("AOP @Before: calling method " + joinPoint.getSignature());
    }

    @AfterReturning("execution(public String list(..))")
    public void afterListOfMethodAdvice(JoinPoint joinPoint){
        System.out.println("AOP @AfterReturning: finished method "  + joinPoint.getSignature());
    }

    @Before("execution(public String addProduct(..))")
    public void beforeAddProductOfMethodAdvice(JoinPoint joinPoint){
        System.out.println("AOP @Before: calling method " + joinPoint.getSignature());
    }

    @AfterReturning("execution(public String addProduct(..))")
    public void afterAddProductOfMethodAdvice(JoinPoint joinPoint){
        System.out.println("AOP @AfterReturning: finished method "  + joinPoint.getSignature());
    }

    @Around("execution(public String addBucket(..))")
    public Object aroundAddBucketOfMethodAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("AOP @Around: before calling method " + proceedingJoinPoint.getSignature());
        Object targetMethodResult = proceedingJoinPoint.proceed();
        System.out.println("AOP @Around: after finished method " + proceedingJoinPoint.getSignature());
        return targetMethodResult;
    }
}
