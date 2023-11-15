package com.tenpo.calculator.filter;

import com.tenpo.calculator.service.LoggerDBService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class LoggerDBFilter extends OncePerRequestFilter {

    @Autowired
    LoggerDBService loggerDBService;
    private byte[] cachedBody;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);

        String requestBody = new String(cachingRequestWrapper.getContentAsByteArray(),
                cachingRequestWrapper.getCharacterEncoding());

        String responseBody = new String(cachingResponseWrapper.getContentAsByteArray(),
                cachingResponseWrapper.getCharacterEncoding());

        loggerDBService.saveRequestResponseLog(cachingRequestWrapper.getRequestURI(),
                cachingRequestWrapper.getMethod(),
                LocalDateTime.now(),
                requestBody,responseBody);

        cachingResponseWrapper.copyBodyToResponse();
    }
}