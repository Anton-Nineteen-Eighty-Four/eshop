package com.antonhulevich.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BucketControllerLoggingAspect {

    @Pointcut("execution(public String removeProductFromBucket(..))")
    public void methodRemoveProductFromBucket(){}

    @Before("methodRemoveProductFromBucket()")
    public void beforeRemoveProductFromBucketOfMethodAdvice(JoinPoint joinPoint){
        System.out.println("AOP @Before: calling method " + joinPoint.getSignature());
    }

    @AfterReturning("methodRemoveProductFromBucket()")
    public void afterReturningRemoveProductFromBucketOfMethodAdvice(JoinPoint joinPoint){
        System.out.println("AOP @AfterReturning: finished method " + joinPoint.getSignature());
    }

    @Around("execution(public String aboutBucket(..))")
    public Object aroundAboutBucketOfMethodAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("AOP @Around: before calling method " + proceedingJoinPoint.getSignature());
        Object targetMethodResult = proceedingJoinPoint.proceed();
        System.out.println("AOP @Around: after finished method " + proceedingJoinPoint.getSignature());
        return targetMethodResult;
    }
}
