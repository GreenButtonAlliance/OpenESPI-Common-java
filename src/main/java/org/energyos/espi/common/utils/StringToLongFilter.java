package org.energyos.espi.common.utils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

@Component
public class StringToLongFilter extends GenericFilterBean {	
	
	@Override
	public void doFilter(ServletRequest request,
			ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
        if (logger.isDebugEnabled()) {
        	
        	logger.debug("StringToLongFilter processing");
        	
        	HttpServletRequest httpRequest = (HttpServletRequest) request;
        	HttpServletResponse httpResponse = (HttpServletResponse) response;
        	
        	logger.debug("Request is " + httpRequest.getClass());
        	logger.debug("Request URL: " + httpRequest.getRequestURL());
            logger.debug("Request Method is '" + httpRequest.getMethod() + "'");        	
        	logger.debug("Response is " + httpResponse.getClass());

        }		
		
		//TODO: Add Long to String conversion logic
		
		filterChain.doFilter (request, response);
	}

		
}