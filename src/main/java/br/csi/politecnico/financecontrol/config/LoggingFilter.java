package br.csi.politecnico.financecontrol.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger LOG = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            logRequest(wrappedRequest, wrappedResponse, duration);

            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long duration) {
        String method = request.getMethod();
        String uri = request.getRequestURI();
        int status = response.getStatus();

        LOG.info("REQUEST_FINISHED: {}, {} finished with {} status code, took {}ms",
                method, uri, status, duration);

//        if (LOG.isDebugEnabled()) {
//            logRequestDetails(request);
//            logResponseDetails(response);
//        }
    }

    private void logRequestDetails(ContentCachingRequestWrapper request) {
        LOG.debug("=== REQUEST DETAILS ===");
        LOG.debug("Method: {}", request.getMethod());
        LOG.debug("URI: {}", request.getRequestURI());
        LOG.debug("Query String: {}", request.getQueryString());
        LOG.debug("Remote Address: {}", request.getRemoteAddr());
        LOG.debug("Time: {}", LocalDateTime.now());

        Enumeration<String> headerNames = request.getHeaderNames();
        Map<String, String> headers = new HashMap<>();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        if (!headers.isEmpty()) {
            LOG.debug("Headers: {}", headers);
        }

        Map<String, String[]> parameters = request.getParameterMap();
        if (!parameters.isEmpty()) {
            LOG.debug("Parameters: {}", parameters);
        }

        byte[] content = request.getContentAsByteArray();
        if (content.length > 0) {
            try {
                String body = new String(content, request.getCharacterEncoding());
                LOG.debug("Request Body: {}", body);
            } catch (UnsupportedEncodingException e) {
                LOG.debug("Could not read request body: {}", e.getMessage());
            }
        }
    }

    private void logResponseDetails(ContentCachingResponseWrapper response) {
        LOG.debug("=== RESPONSE DETAILS ===");
        LOG.debug("Status: {}", response.getStatus());

        Map<String, String> headers = new HashMap<>();
        for (String headerName : response.getHeaderNames()) {
            headers.put(headerName, response.getHeader(headerName));
        }
        if (!headers.isEmpty()) {
            LOG.debug("Response Headers: {}", headers);
        }

        byte[] content = response.getContentAsByteArray();
        if (content.length > 0) {
            try {
                String body = new String(content, response.getCharacterEncoding());
                LOG.debug("Response Body: {}", body);
            } catch (Exception e) {
                LOG.debug("Could not read response body: {}", e.getMessage());
            }
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        // Excluir endpoints de saúde, documentação, etc.
        return path.startsWith("/actuator") ||
                path.startsWith("/v3/api-docs") ||
                path.startsWith("/swagger-ui") ||
                path.startsWith("/favicon.ico") ||
                path.startsWith("/error");
    }
}