package com.share.zuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


/**
 * 自定义zuul过滤器
 */
@Component
public class TokenZuulFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(TokenZuulFilter.class);


    /**
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     *      pre：路由之前
     *      routing：路由之时
     *      post： 路由之后
     *      error：发送错误调用
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 过滤的顺序
     */
    @Override
    public int filterOrder() {

//        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;
        return 0;
    }

    /**
     * 逻辑判断是否进行过滤
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * run：过滤器的具体逻辑。可用很复杂，包括查sql，nosql去判断该请求到底有没有权限访问。
     */
    @Override
    public Object run() throws ZuulException {
        logger.info("=================================>TokenZuulFilter");
        RequestContext currentRequestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentRequestContext.getRequest();
        logger.info(String.format("%s >>> %s", request.getMethod(), request.getRequestURL().toString()));
        String token = request.getHeader("token");
        if(token == null){
            token = request.getParameter("token");
        }
        logger.info("=================================>");
        if(token == null){
            logger.warn("传递token为空");
            currentRequestContext.setSendZuulResponse(false);
            currentRequestContext.setResponseStatusCode(403);
            try {
                //解决中文乱码
                currentRequestContext.getResponse().setCharacterEncoding("UTF-8");
                currentRequestContext.getResponse().getWriter().write("传递token为空");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        logger.info("token校验通过....");
        return null;
    }
}
