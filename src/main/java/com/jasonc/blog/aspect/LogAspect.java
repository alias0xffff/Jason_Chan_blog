package com.jasonc.blog.aspect;

import com.alibaba.fastjson.JSON;
import com.jasonc.blog.util.IpUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.aspect
 * @ClassName: LogAspect
 * @Author: Jason Chan
 * @Description: 日志切面，记录每一次访问的接口和参数 ip地址 异常 访问耗时 请求方法 请求参数名和参数值 返回结果
 */
@Component
@Aspect
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @return void
     * @Author Jason Chan
     * @Description 设置操作日志切入点 记录操作日志
     * @Param []
     **/
    @Pointcut("execution(* com.jasonc.blog..*.*(..))")
    public void logPointCut() {
    }

    /**
     * @return java.lang.Object
     * @Author Jason Chan
     * @Description 环绕通知，目标方法执行成功记录日志
     * @Param [proceedingJoinPoint]
     **/
    @Around(value = "logPointCut()")
    public Object saveLog(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 开始时间
        long startTimeMillis = System.currentTimeMillis();
        // 取得HttpServletRequest
        HttpServletRequest request = this.getHttpServletRequest();
        // 目标方法执行
        Object result = proceedingJoinPoint.proceed();

        RequestInfo requestInfo = new RequestInfo();
        // 客户端IP
        // String ip = request.getRemoteAddr();
        requestInfo.setIp(IpUtil.getClientIPByNginx(request));
        // 请求url
        requestInfo.setUrl(request.getRequestURL().toString());
        // 请求方式(GET, POST等)
        requestInfo.setHttpMethodd(request.getMethod());
        // 全限定类名.方法名
        requestInfo.setClassMethod(String.format("%s.%s", proceedingJoinPoint.getSignature().getDeclaringTypeName(),
                proceedingJoinPoint.getSignature().getName()));
        // map<String, Object> 参数名:参数值
        requestInfo.setRequestParams(this.getRequestParams(proceedingJoinPoint));
        // 执行目标方法后的返回结果
        requestInfo.setResult(result);
        // 请求耗时
        requestInfo.setTimeCost(System.currentTimeMillis() - startTimeMillis);

        logger.info("Request Info : {}", requestInfo);

        return result;

    }

    /**
     * @return
     * @Author Jason Chan
     * @Description 抛出异常后的日志记录,同上
     * @Date 2021/7/12 14:07
     * @Param [joinPoint, e]
     **/
    @AfterThrowing(pointcut = "logPointCut()", throwing = "e")
    public void saveLog(JoinPoint joinPoint, Exception e) {
        HttpServletRequest request = this.getHttpServletRequest();
        RequestErrorInfo requestErrorInfo = new RequestErrorInfo();
        requestErrorInfo.setIp(IpUtil.getClientIPByNginx(request));
        requestErrorInfo.setUrl(request.getRequestURL().toString());
        requestErrorInfo.setHttpMethodd(request.getMethod());
        requestErrorInfo.setClassMethod(String.format("%s.%s", joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName()));
        requestErrorInfo.setRequestParams(this.getRequestParams(joinPoint));
        requestErrorInfo.setException(e);
        logger.info("Error Request Info : {}", requestErrorInfo);
    }

    /**
     * @return
     * @Author Jason Chan
     * @Description 取得HttpServletRequest
     * @Date 2021/7/12 14:19
     * @Param []
     **/
    private HttpServletRequest getHttpServletRequest() {
        // 获取requestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从requestAttributes中获取request
        HttpServletRequest request = (HttpServletRequest) Objects.requireNonNull(requestAttributes).resolveReference(RequestAttributes.REFERENCE_REQUEST);
        return request;
    }


    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Jason Chan
     * @Description 通过proceedingJoinPoint获取请求参数名和请求参数值
     * @Param [proceedingJoinPoint]
     **/
    private Map<String, Object> getRequestParams(JoinPoint joinPoint) {
        // 参数名
        String[] parameterNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        // 参数值
        Object[] parameterValues = joinPoint.getArgs();

        return this.buildRequestParams(parameterNames, parameterValues);
    }

    /**
     * @return java.util.Map<java.lang.String, java.lang.Object>
     * @Author Jason Chan
     * @Description 将请求参数名和请求参数值封装到map中
     * @Param [parameterNames, parameterValues]
     **/
    private Map<String, Object> buildRequestParams(String[] parameterNames, Object[] parameterValues) {
        Map<String, Object> requestParams = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            Object parameterValue = parameterValues[i];
            // 如果参数值是文件对象, 即客户端请求了文件, 则以文件名作为参数值
            if (parameterValue instanceof MultipartFile) {
                MultipartFile file = (MultipartFile) parameterValue;
                parameterValue = file.getOriginalFilename();
            }
            requestParams.put(parameterNames[i], parameterValue);
        }
        return requestParams;
    }

    /**
     * @Author Jason Chan
     * @Description 请求成功信息
     * @Param
     * @return
     **/
    private class RequestInfo {
        // 源IP
        private String ip;
        // 请求url
        private String url;
        // http请求方式
        // HTTP1.0定义了三种请求方法： GET, POST 和 HEAD方法。
        // HTTP1.1新增了五种请求方法：OPTIONS, PUT, DELETE, TRACE 和 CONNECT 方法
        private String httpMethodd;
        // 需要调用的java方法
        private String classMethod;
        // 请求参数
        private Object requestParams;
        // 返回结果
        private Object result;
        // 完成一次请求花费的时间
        private Long timeCost;

        public RequestInfo() {
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHttpMethodd() {
            return httpMethodd;
        }

        public void setHttpMethodd(String httpMethodd) {
            this.httpMethodd = httpMethodd;
        }

        public String getClassMethod() {
            return classMethod;
        }

        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }

        public Object getRequestParams() {
            return requestParams;
        }

        public void setRequestParams(Object requestParams) {
            this.requestParams = requestParams;
        }

        public Object getResult() {
            return result;
        }

        public void setResult(Object result) {
            this.result = result;
        }

        public Long getTimeCost() {
            return timeCost;
        }

        public void setTimeCost(Long timeCost) {
            this.timeCost = timeCost;
        }

        @Override
        public String toString() {
            return "RequestInfo{" +
                    "ip='" + ip + '\'' +
                    ", url='" + url + '\'' +
                    ", httpMethodd='" + httpMethodd + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", requestParams=" + requestParams +
                    ", result=" + result +
                    ", timeCost=" + timeCost +
                    '}';
        }
    }

    private class RequestErrorInfo {
        // 源IP
        private String ip;
        // 请求url
        private String url;
        // http请求方式
        // HTTP1.0定义了三种请求方法： GET, POST 和 HEAD方法。
        // HTTP1.1新增了五种请求方法：OPTIONS, PUT, DELETE, TRACE 和 CONNECT 方法
        private String httpMethodd;
        // 需要调用的java方法
        private String classMethod;
        // 请求参数
        private Object requestParams;
        // 异常
        private Exception exception;

        public RequestErrorInfo() {
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getHttpMethodd() {
            return httpMethodd;
        }

        public void setHttpMethodd(String httpMethodd) {
            this.httpMethodd = httpMethodd;
        }

        public String getClassMethod() {
            return classMethod;
        }

        public void setClassMethod(String classMethod) {
            this.classMethod = classMethod;
        }

        public Object getRequestParams() {
            return requestParams;
        }

        public void setRequestParams(Object requestParams) {
            this.requestParams = requestParams;
        }

        public Exception getException() {
            return exception;
        }

        public void setException(Exception exception) {
            this.exception = exception;
        }

        @Override
        public String toString() {
            return "RequestErrorInfo{" +
                    "ip='" + ip + '\'' +
                    ", url='" + url + '\'' +
                    ", httpMethodd='" + httpMethodd + '\'' +
                    ", classMethod='" + classMethod + '\'' +
                    ", requestParams=" + requestParams +
                    ", exception=" + exception +
                    '}';
        }
    }
}
