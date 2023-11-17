package com.tenpo.calculator.filter;

import com.tenpo.calculator.service.LoggerDBService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class LoggerDBFilter extends OncePerRequestFilter {

    @Autowired
    LoggerDBService loggerDBService;
    //Request has parameters and body, so we need to prepare the information for logging
    private String generateRequestLog(ContentCachingRequestWrapper cachingRequestWrapper)
            throws ServletException, IOException {

        String requestLog ="";

        Map<String, String[]> parameterMap = cachingRequestWrapper.getParameterMap();
        for (String key : parameterMap.keySet())
        {
            requestLog += "parameter: " + key + " value: " +  parameterMap.get(key)[0] + " ";
        }

        requestLog += "Body: " + new String(cachingRequestWrapper.getContentAsByteArray(),
                cachingRequestWrapper.getCharacterEncoding());

        return requestLog;
    }

    // This filter get the request and responses to save them into the DB
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        ContentCachingRequestWrapper cachingRequestWrapper = new ContentCachingRequestWrapper(httpServletRequest);
        ContentCachingResponseWrapper cachingResponseWrapper = new ContentCachingResponseWrapper(httpServletResponse);

        filterChain.doFilter(cachingRequestWrapper, cachingResponseWrapper);

        // the try here is to avoid impact when saving the log
        try {
            String responseBody = new String(cachingResponseWrapper.getContentAsByteArray(),
                    cachingResponseWrapper.getCharacterEncoding());

            loggerDBService.saveRequestResponseLog(cachingRequestWrapper.getRequestURI(),
                    cachingRequestWrapper.getMethod(),
                    LocalDateTime.now(),
                    generateRequestLog(cachingRequestWrapper),
                    responseBody,
                    String.valueOf(cachingResponseWrapper.getStatus()));

            cachingResponseWrapper.copyBodyToResponse();
        } catch (Exception e) {
            log.error("Error saving RequestResponseLog in DB");
            log.error(ExceptionUtils.getStackTrace(e));
        }
    }
}