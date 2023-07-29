package com.videos.course.server.config;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.PropertyPreFilters;
import com.videos.course.server.utils.UUIDUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * @author 隔壁_老陈
 * 代码风骚、效率恐怖、编译器深深鄙视智商。
 * 千里之外定位问题、瞬间修复依旧风骚……
 * @create 2021-04-17 17:14
 */
@Component
@Aspect
@Slf4j
public class LogAspect {
    /**
     * 定义切入点
     */
    @Pointcut("execution(public  * com.videos.course.*.controller..*Controller.*(..))")
    public void controllerPointcut() {
    }


    @Before("controllerPointcut()")
    public void logBefore(JoinPoint joinPoint) throws Throwable {
        //日志编号
        MDC.put("UUID", UUIDUtils.random32());
        //获取打印请求的数据
        ServletRequestAttributes servletRequestAttributes
                = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Signature signature = joinPoint.getSignature();
        String name = signature.getName();

        //获取业务操作方式
        String nameLogTitle = "";
        if (name.contains("list") || name.contains("query") || name.contains("get") || name.contains("select")) {
            nameLogTitle = "查询";
        } else if (name.contains("save") || name.contains("add")) {
            nameLogTitle = "保存";
        } else if (name.contains("del") || name.contains("remove")) {
            nameLogTitle = "删除";
        } else {
            nameLogTitle = "其他操作";
        }

        //通过反射的方式获取业务的名称
        Class aClass = signature.getDeclaringType();
        Field field;
        String business_name = "";
        try {
            field = aClass.getField("BUSINESS_NAME");
            if (!StringUtils.isEmpty(field)) {
                business_name = (String) field.get(aClass);
            }
        } catch (NoSuchFieldException e) {
            log.error("未获取到业务名称", e);
        } catch (IllegalAccessException e) {
            log.error("获取业务名称失败", e);
        }
        /**
         * 打印请求信息
         */
        log.info("---------------------【{}】{} start--------------------------------- ", business_name, nameLogTitle);
        log.info("请地址:{} \t操作方法:{}", request.getRequestURL().toString(), request.getMethod());
        log.info("类名路径:{} \t 方法名:\\{{}\\}", signature.getDeclaringTypeName(), name);
        log.info("远程请求地址:{}", request.getRemoteAddr());
        log.info("{}\t请求路径:{}",business_name, request.getRequestURI());

        /**
         * 打印的请参数，如文件上传不用打印
         */
        Object[] args = joinPoint.getArgs();
        Object[] argsArrays = new Object[args.length];
        for (int i = 0, j = 0; i < args.length; i++) {
            if (args[i] instanceof ServletRequest
                    || args[i] instanceof ServletResponse
                    || args[i] instanceof MultipartFile
            ) {
                continue;
            }
            argsArrays[j] = args[i];
            j++;
        }
        //排查除敏感字段的打印
        String[] excludeProperties = {"shard"};
        PropertyPreFilters propertyPreFilters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter
                excludePropertiesFilter = propertyPreFilters.addFilter();
        excludePropertiesFilter.addExcludes(excludeProperties);
        // 为空的会不打印，但是像图片等长字段也会打印
       // log.info("请求参数:{}", JSONObject.toJSONString(argsArrays, excludePropertiesFilter));
        log.info("请求参数:{}", JSONObject.toJSONString(argsArrays));
    }


    @Around("controllerPointcut()")
    public Object logAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startMillis = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        //排查除敏感字段的打印
        String[] excludeProperties = {"shard","password"};
        PropertyPreFilters propertyPreFilters = new PropertyPreFilters();
        PropertyPreFilters.MySimplePropertyPreFilter
                excludePropertiesFilter = propertyPreFilters.addFilter();
        excludePropertiesFilter.addExcludes(excludeProperties);
        //log.info("返回的结果:{}", JSONObject.toJSONString(result, excludePropertiesFilter));
        log.info("返回的结果:{}", JSONObject.toJSONString(result));
        log.info("--------结束 \t 共消耗时间:{} ms----------------", System.currentTimeMillis() - startMillis);
        return result;

    }




}
