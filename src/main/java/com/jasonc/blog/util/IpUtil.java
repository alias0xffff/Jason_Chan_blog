package com.jasonc.blog.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @Program: blog
 * @Package: com.jasonc.blog.util
 * @ClassName: IpUtil
 * @Author: Jason Chan
 * @Description: 获取真实ip的工具类
 */
public class IpUtil {
    /**
     * @return java.lang.String
     * @Author Jason Chan
     * @Description 通过Nginx获取客户端ip地址;X-Real-IP
     * nginx需要增加以下配置:
     *      proxy_set_header  Host $http_host;
     *      proxy_set_header X-Real-IP  $remote_addr;
     *      proxy_set_header  X-Forwarded-For $proxy_add_x_forwarded_for;
     *      proxy_set_header REMOTE-HOST $remote_addr;
     * @Param [request]
     **/
    public static String getClientIPByNginx(HttpServletRequest request) {
        String fromSource = "X-Real-IP";
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Forwarded-For");
            fromSource = "X-Forwarded-For";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
            fromSource = "Proxy-Client-IP";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
            fromSource = "WL-Proxy-Client-IP";
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            fromSource = "request.getRemoteAddr";
        }
        System.out.println("App Client IP: " + ip + ", fromSource: " + fromSource);
        return ip;
    }
}
