package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.example.model.ExampleObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.List;


@Aspect
@Component
public class MyAspect {

    private final static Logger logger = LoggerFactory.getLogger(MyAspect.class);

    @Before("@annotation(LogExecution)")
    public void logBefore(JoinPoint joinPoint) {
        logger.info("Вызов метода с аннотацией @Before {}", joinPoint.getSignature().getName());
    }

    @AfterThrowing(pointcut = "@annotation(LogException)")
    public void logAfterThrowing(JoinPoint joinPoint) {
        logger.info("Вызов метода с аннотацией @AfterThrowing {}", joinPoint.getSignature().getName());
    }

    @AfterReturning(pointcut = "@annotation(HandlingResult)",
    returning = "result")
    public void handleResult(JoinPoint joinPoint, ExampleObject result) {
        logger.info("Вызов метода с аннотацией @AfterReturning {}", joinPoint.getSignature().getName());
        logger.info("result: {}", result);

    }

    @Around("@annotation(LogTracking)")
    public Object logExecTimeAround(ProceedingJoinPoint joinPoint) {
        logger.info("Начало вызова метода с аннотацией @Around {}", joinPoint.getSignature().getName());

        Object result ;

        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        logger.info("Конец вызова метода с аннотацией @Around {}", joinPoint.getSignature().getName());
        return result;

    }


}
