package com.exchange.stock.market.stockDetails.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExecutionTimeAspect {

    //This method will be called only when we use @LogExecutionTime at method level.
    @Around("execution(* com.exchange.stock.market.stockDetails.aop.LogExecutionTime")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();   // execute actual method
        long end = System.currentTimeMillis();
        log.info("Method {} executed in {} ms",
                joinPoint.getSignature().toShortString(),
                (end - start));
        return result;
    }
}
