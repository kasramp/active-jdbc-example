package com.madadipouya.example.activejdbc.activejdbcexample.filters;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class ActiveJdbcFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(ActiveJdbcFilter.class);

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        long before = System.currentTimeMillis();
        try {
            Base.open();
            Base.openTransaction();
            chain.doFilter(req, resp);
            Base.commitTransaction();
        } catch (IOException | ServletException e) {
            Base.rollbackTransaction();
            throw e;
        } finally {
            Base.close();
        }
        logger.info("Processing took: {} milliseconds", System.currentTimeMillis() - before);
    }

    @Override
    public void destroy() {
    }
}