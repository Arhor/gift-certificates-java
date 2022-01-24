package com.epam.esm.gift.web.aspect;

import java.lang.invoke.MethodHandles;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.epam.esm.gift.web.context.CurrentRequestContext;

@Aspect
@Component
public class MethodExecutionLoggingAspect {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final CurrentRequestContext currentRequestContext;

    public MethodExecutionLoggingAspect(final CurrentRequestContext currentRequestContext) {
        this.currentRequestContext = currentRequestContext;
    }

    @Before(value = "webLayer() || serviceLayer() || repositoryLayer()")
    public void intercept(final JoinPoint joinPoint) {
        if (log.isInfoEnabled()) {
            var traceId = currentRequestContext.getTraceId();
            var signature = stringifyMethodExecutionSignature(joinPoint);

            log.info("Trace-ID: {}, Method: {}", traceId, signature);
        }
    }

    @Pointcut("execution(* com.epam.esm.gift.web.api..*(..))")
    private void webLayer() { /* no-op */ }

    @Pointcut("execution(* com.epam.esm.gift.service.impl.*.*(..))")
    private void serviceLayer() { /* no-op */ }

    @Pointcut("execution(* com.epam.esm.gift.repository.*.*(..))")
    private void repositoryLayer() {/* no-op */ }

    private String stringifyMethodExecutionSignature(final JoinPoint joinPoint) {
        var className = joinPoint.getSourceLocation().getWithinType().getCanonicalName();
        var methodName = joinPoint.getSignature().getName();
        var methodArgs = StringUtils.joinWith(",", joinPoint.getArgs());

        return className + "." + methodName + "(" + methodArgs + ")";
    }
}
