
package com.happylifeplat.netty.utils;

import com.happylifeplat.netty.context.NettyEmbeddedContext;
import org.apache.commons.lang3.StringUtils;


/**
 * <p>Description: .</p>
 * <p>Company: 深圳市旺生活互联网科技有限公司</p>
 * <p>Copyright: 2015-2017 happylifeplat.com All Rights Reserved</p>
 *  uri解析类
 * @author yu.xiao@happylifeplat.com
 * @version 1.0
 * @date 2017/5/12 16:46
 * @since JDK 1.8
 */
public class URIParser {

    private NettyEmbeddedContext context;

    private String servletPath;

    private String requestUri;

    private String pathInfo;

    private String queryString;

    public URIParser(NettyEmbeddedContext context) {
        this.context = context;
    }

    public void parse(String uri) {
        /**
         * 如果有contextPath 需要去掉
         */
        if (StringUtils.isNoneBlank(context.getContextPath())) {
            uri=uri.replaceAll(context.getContextPath(),"");
        }
        int index = uri.indexOf('?');
        this.servletPath = this.context.getMatchingUrlPattern(uri);
        if (!this.servletPath.startsWith("/")) {
            this.servletPath = "/" + this.servletPath;
        }
        if (index != -1) {
            this.pathInfo = uri.substring(servletPath.length(), index);
            this.queryString = uri.substring(index + 1);
            this.requestUri = uri.substring(0, index);
        } else {
            this.pathInfo = uri.substring(servletPath.length());
            this.requestUri = uri;
        }

        if (this.requestUri.endsWith("/")) {
            this.requestUri.substring(0, this.requestUri.length() - 1);
        }

        if (this.pathInfo.equals("")) {
            this.pathInfo = null;
        } else if (!this.pathInfo.startsWith("/")) {
            this.pathInfo = "/" + this.pathInfo;
        }
    }

    public String getServletPath() {
        return servletPath;
    }

    public String getQueryString() {
        return queryString;
    }

    public String getPathInfo() {
        return this.pathInfo;
    }

    public String getRequestUri() {
        return requestUri;
    }
}
