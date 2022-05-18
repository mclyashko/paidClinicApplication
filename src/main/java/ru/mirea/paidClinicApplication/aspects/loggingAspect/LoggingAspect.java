package ru.mirea.paidClinicApplication.aspects.loggingAspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Slf4j
@Component
@org.aspectj.lang.annotation.Aspect
public class LoggingAspect {
    @Before("allServiceMethods()")
    public void log(JoinPoint joinPoint) {
        log.info("Awakening " + joinPoint.getSignature().getName() +
                " method from " + joinPoint.getTarget().getClass() +
                " with args: " + Arrays.toString(joinPoint.getArgs()) +
                " at " + LocalDateTime.now() +
                ";"
        );
    }
    @Pointcut("within(ru.mirea.paidClinicApplication.services..*)")
    public void allServiceMethods() {}
}