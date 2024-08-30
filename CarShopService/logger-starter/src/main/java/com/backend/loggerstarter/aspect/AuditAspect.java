package com.backend.loggerstarter.aspect;

import com.backend.loggerstarter.annotation.EnableAudit;
import com.backend.loggerstarter.config.LoggerConfig;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.logging.Logger;

@Aspect
@Component
public class AuditAspect {

    private static final Logger logger = LoggerConfig.getAuditLogger();

    @Around("@annotation(enableAudit)")
    public Object audit(ProceedingJoinPoint joinPoint, EnableAudit enableAudit) throws Throwable {
        String username = "anonymous";
        String actionType = enableAudit.actionType();
        String description = enableAudit.description();
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
