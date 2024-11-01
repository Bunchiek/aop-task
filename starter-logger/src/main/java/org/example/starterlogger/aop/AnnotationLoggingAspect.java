package org.example.starterlogger.aop;

import jakarta.annotation.PostConstruct;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.starterlogger.annotation.Loggable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AnnotationLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(AnnotationLoggingAspect.class);

    @Value("${logger.http.enabled:true}")
    private boolean loggingEnabled;

    @Value("${logger.http.level:INFO}")
    private String loggingLevel;

    @Before("@annotation(loggable)")
    public void logRequest(JoinPoint joinPoint, Loggable loggable) {
        if (!loggingEnabled) return;

        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        switch (loggingLevel.toUpperCase()) {
            case "DEBUG":
                logger.debug("Incoming request to method: {} with arguments: {}", methodName, args);
                break;
            case "INFO":
            default:
                logger.info("Incoming request to method: {}", methodName);
                break;
        }
    }

    @AfterReturning(pointcut = "@annotation(loggable)", returning = "result")
    public void logResponse(JoinPoint joinPoint, Loggable loggable, Object result) {
        if (!loggingEnabled) return;

        String methodName = joinPoint.getSignature().getName();

        switch (loggingLevel.toUpperCase()) {
            case "DEBUG":
                logger.debug("Response from method: {} with result: {}", methodName, result);
                break;
            case "INFO":
            default:
                logger.info("Response from method: {}", methodName);
                break;
        }
    }
}
