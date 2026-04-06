package com.carwash.orderservice.configs;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.*;

public class RequestContextHolderUtil {

    public static String getAuthHeader() {
        ServletRequestAttributes attrs =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if (attrs == null) return null;

        HttpServletRequest request = attrs.getRequest();
        return request.getHeader("Authorization");
    }
}