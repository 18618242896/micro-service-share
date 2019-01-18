package com.share.zuul.config;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 采用令牌桶进行限流
 */
@Component
public class RateLimitZuulFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(RateLimitZuulFilter.class);

    //谷歌guava的令牌桶实现
    private static final RateLimiter rateLimiter = RateLimiter.create(100);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SERVLET_DETECTION_FILTER_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        logger.info("=================================>RateLimitZuulFilter");
        if(!rateLimiter.tryAcquire()){
            RequestContext currentContext = RequestContext.getCurrentContext();
            HttpServletResponse response = currentContext.getResponse();
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            currentContext.setSendZuulResponse(false);
            logger.warn("Too Many Requests");
            try {
                response.getWriter().write("Too Many Requests");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        return null;
    }
}
