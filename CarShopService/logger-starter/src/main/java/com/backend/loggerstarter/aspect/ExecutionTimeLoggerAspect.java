package com.backend.loggerstarter.aspect;

import com.backend.loggerstarter.config.LoggerConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Aspect
@Component
public class ExecutionTimeLoggerAspect {

    private static final Logger logger = LoggerConfig.getTimeLogger();

    @Around("@within(org.springframework.web.bind.annotation.RestController)")
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

}
