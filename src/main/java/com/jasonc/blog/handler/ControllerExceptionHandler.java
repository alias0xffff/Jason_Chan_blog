package com.jasonc.blog.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.handler
 * @ClassName: ControllerExceptionHandler
 * @Author: Jason Chan
 * @CreateTime:
 * @Description: 全局异常处理
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({Exception.class})
    public String exceptionHandler(HttpServletRequest request, Model model, Exception e) throws Exception {
        StringBuffer requestURL = request.getRequestURL();
        // 如果异常为404，500等，如定义的notfoundexception添加注解@ResponseStatus(value = HttpStatus.NOT_FOUND) 为404错误，则
        // 抛出异常交给springboot处理，自动跳转至自定义的404.html、500.html,或springboot默认的错误网页
        if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
            throw e;
        } else {
            logger.error("Request URL : {}, Exception : {}", requestURL, e.getMessage());
            model.addAttribute("url", requestURL);
            model.addAttribute("exception", e.getMessage());
            return "error/error";
        }
    }
}
