package com.antonhulevich.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class MainControllerLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(MainControllerLoggingAspect.class);

    @Pointcut("execution(* com.antonhulevich.eshop.controller.MainController.*(..))")
    private void anyMainControllerMethod() {}

    @Before("anyMainControllerMethod()")
    public void beforeAdvice(JoinPoint joinPoint) {
        log.info("WEB REQ -> Call: {}.{}() with arguments: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing(pointcut = "anyMainControllerMethod()", throwing = "ex")
    public void errorAdvice(JoinPoint joinPoint, Throwable ex) {
        log.error("WEB ERR -> Error in {}: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }

    @AfterReturning("anyMainControllerMethod()")
    public void afterReturningAdvice(JoinPoint joinPoint) {
        log.info("WEB RES -> Successfully finished: {}.{}()",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName());
    }
}
