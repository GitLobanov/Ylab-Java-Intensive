package com.backend.aspect;

import com.backend.annotations.Auditable;
import com.backend.config.LoggerConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerConfig.getLogger();

    @Around("execution(* com.backend.controller.*.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        LocalDateTime startTime = LocalDateTime.now();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            System.err.println("Exception in method: " + methodName);
            throw throwable;
        }

        logExecution(joinPoint.getSignature(), startTime, LocalDateTime.now(), java.time.Duration.between(startTime, LocalDateTime.now()).toMillis());

        return result;
    }

    private void logExecution(Signature signature, LocalDateTime startTime, LocalDateTime endTime, long millis) {
        String message = String.format("Method: %s, Start: %s, End: %s, Duration: %d ms",
                signature, startTime, endTime, millis);
        logger.info(message);
    }


    @Around("@annotation(auditable)")
    public Object audit(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        String username = "anonymous";
        String actionType = auditable.actionType();
        String description = auditable.description();
        LocalDateTime startTime = LocalDateTime.now();

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Exception e) {
            logAudit(username, actionType, description, startTime, LocalDateTime.now(), false);
            throw e;
        }

        logAudit(username, actionType, description, startTime, LocalDateTime.now(), true);
        return result;
    }

    private void logAudit(String username, String actionType, String description, LocalDateTime startTime, LocalDateTime endTime, boolean success) {
        String message = String.format("User: %s, Action: %s, Description: %s, Start: %s, End: %s, Success: %s",
                username, actionType, description, startTime, endTime, success);
        logger.info(message);
    }

}
