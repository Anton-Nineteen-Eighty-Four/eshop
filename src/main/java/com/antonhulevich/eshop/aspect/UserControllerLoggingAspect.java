package com.antonhulevich.eshop.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class UserControllerLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(UserControllerLoggingAspect.class);

    @Pointcut("execution(* com.antonhulevich.eshop.controller.UserController.*(..))")
    private void anyControllerMethod() {}

    @Before("anyControllerMethod()")
    public void beforeUserControllerOfMethodAdvice(JoinPoint joinPoint){
        log.info("WEB REQ -> Call: {}.{}() with arguments: {}",
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing(pointcut = "anyControllerMethod()", throwing = "ex")
    public void errorAdvice(JoinPoint joinPoint, Throwable ex) {
        log.error("WEB ERR -> Error in {}: {}", joinPoint.getSignature().getName(), ex.getMessage());
    }
    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public Object profilePostMethods(ProceedingJoinPoint pjp) throws Throwable {
        long start = System.currentTimeMillis();
        try {
            return pjp.proceed();
        } finally {
            long duration = System.currentTimeMillis() - start;
            if (duration > 100) {
                log.warn("PERF -> Heavy POST request {} took {} ms", pjp.getSignature().getName(), duration);
            } else {
                log.debug("PERF -> POST request {} executed in {} ms", pjp.getSignature().getName(), duration);
            }
        }
    }
}
