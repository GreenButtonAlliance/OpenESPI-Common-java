package org.energyos.espi.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CORSFilter extends OncePerRequestFilter {
    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    	
        if (logger.isDebugEnabled()) {
            logger.debug("Request Method is '" + request.getMethod() + "'");
        }
        
        // Only add CORS Headers if request method is OPTIONS        
        if (request.getMethod().equals("OPTIONS")) {
        	
        	if (logger.isDebugEnabled()) {
        		logger.debug("Setting Access-Control-Allow-Origin to *");
        	}
        	response.addHeader("Access-Control-Allow-Origin", "*");
        	response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        	response.addHeader("Access-Control-Allow-Headers", "content-type, authorization");
        	response.addHeader("Access-Control-Max-Age", "1800");
        }

        filterChain.doFilter(request, response);
    }
}
