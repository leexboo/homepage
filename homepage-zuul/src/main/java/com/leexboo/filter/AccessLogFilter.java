package com.leexboo.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;

/**
 * 自定义过滤器，打印请求响应时间
 */
@Component
@Slf4j
public class AccessLogFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.POST_TYPE;
    }

    @Override
    public int filterOrder() {
        return FilterConstants.SEND_RESPONSE_FILTER_ORDER - 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        RequestContext ctx = RequestContext. getCurrentContext();
        Long startTime = (Long) ctx.get("startTime");

        long duration = System.currentTimeMillis() - startTime;

        HttpServletRequest request = ctx.getRequest();
        String uri = request.getRequestURI();

        log.info("uri: {}, duration：{}", uri, duration / 100);
        return null;
    }
}
