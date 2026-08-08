package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.DateUtils;
import org.example.annotation.AutoFill;
import org.example.enums.OperationType;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Date;

@Aspect
@Component
public class AutoFillAspect {

    @Before("@annotation(autoFill)")
    public void autoFill(JoinPoint joinPoint, AutoFill autoFill) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }
        Object entity = args[0];

        String now = DateUtils.format(new Date());
        String simpleName = entity.getClass().getSimpleName();
        String setCreate = "set" + simpleName + "CreateTime";
        String setUpdate = "set" + simpleName + "UpdateTime";

        try {
            if (autoFill.value() == OperationType.INSERT) {
                Method m1 = entity.getClass().getMethod(setCreate, String.class);
                Method m2 = entity.getClass().getMethod(setUpdate, String.class);
                m1.invoke(entity, now);
                m2.invoke(entity, now);
            } else if (autoFill.value() == OperationType.UPDATE) {
                Method m2 = entity.getClass().getMethod(setUpdate, String.class);
                m2.invoke(entity, now);
            }
        } catch (Exception e) {
            // 实体没有时间字段时跳过（如 Cinema）
        }
    }
}
