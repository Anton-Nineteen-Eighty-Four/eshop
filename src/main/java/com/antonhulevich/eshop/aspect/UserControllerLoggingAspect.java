package com.antonhulevich.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class UserControllerLoggingAspect {
    @Pointcut("execution(* com.antonhulevich.eshop.controller.UserController.*(..)) && !execution(* com.antonhulevich.eshop.controller.UserController.updateProfileUser(..))")
    private void allMethodsOfUserControllerExcludingUpdateProfileUser() {}

    @Before("allMethodsOfUserControllerExcludingUpdateProfileUser()")
    public void beforeUserControllerOfMethodAdvice(JoinPoint joinPoint){
        System.out.println("AOP @Before: calling method "  + joinPoint.getSignature());
    }

    @AfterReturning("allMethodsOfUserControllerExcludingUpdateProfileUser()")
    public void afterReturningUserControllerOfMethodAdvice(JoinPoint joinPoint){
        System.out.println("AOP @AfterReturning: finished method " + joinPoint.getSignature());
    }

    @AfterThrowing("allMethodsOfUserControllerExcludingUpdateProfileUser()")
    public void afterThrowingUserOfControllerMethodAdvice(JoinPoint joinPoint){
        System.out.println("AOP @AfterThrowing: an exception in the method " + joinPoint.getSignature());
    }

    @Around("execution(public String com.antonhulevich.eshop.controller.UserController.updateProfileUser(com.antonhulevich.eshop.dto.UserDto, org.springframework.ui.Model, java.security.Principal))")
    public Object aroundUpdateProfileAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("AOP @Around: before calling method " + proceedingJoinPoint.getSignature());
        Object targetMethodResult = null;
        try {
            targetMethodResult = proceedingJoinPoint.proceed();
        } catch (RuntimeException runtimeException) {
            System.out.println("AOP @Around: an exception in the method " + proceedingJoinPoint.getSignature());
            throw runtimeException;
        }
        System.out.println("AOP @Around: after finished method " + proceedingJoinPoint.getSignature());
    return targetMethodResult;
    }
}
