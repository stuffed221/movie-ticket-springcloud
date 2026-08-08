package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotation.OperationLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class OperationLogAspect {

    private static final Logger log = LoggerFactory.getLogger(OperationLogAspect.class);

    @Around("@annotation(operationLog)")
    public Object log(ProceedingJoinPoint pjp, OperationLog operationLog) throws Throwable {
        long start = System.currentTimeMillis();
        Object result;
        try {
            result = pjp.proceed();
            long cost = System.currentTimeMillis() - start;
            log.info("【操作日志】{} | 参数: {} | 结果: 成功 | 耗时: {}ms",
                    operationLog.value(), Arrays.toString(pjp.getArgs()), cost);
            return result;
        } catch (Throwable e) {
            log.error("【操作日志】{} | 参数: {} | 结果: 失败: {}",
                    operationLog.value(), Arrays.toString(pjp.getArgs()), e.getMessage());
            throw e;
        }
    }
}
