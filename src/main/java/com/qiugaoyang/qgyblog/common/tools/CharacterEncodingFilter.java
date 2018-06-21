package com.qiugaoyang.qgyblog.common.tools;

import javax.servlet.*;
import java.io.IOException;

public class CharacterEncodingFilter implements Filter {
	protected FilterConfig filterConfig = null;
	protected String encoding = "";

	public void destroy() {
		filterConfig = null;
		encoding = null;
	}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {
		if (encoding != null && !"".equals(encoding))
			servletRequest.setCharacterEncoding(encoding);
		filterChain.doFilter(servletRequest, servletResponse);
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
		this.encoding = filterConfig.getInitParameter("encoding");
	}
}
