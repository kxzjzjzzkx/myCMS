package org.marker.mushroom.urlrewrite;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.marker.urlrewrite.URLRewrite;

/**
 * 
 * 
 * 
 * @author marker
 * */
public class URLRewriteFilter implements Filter {

	private URLRewrite rewrite = URLRewrite.me();

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		String qs = request.getRequestURI();
		String url = rewrite.decoder(qs);
		req.getRequestDispatcher(url).forward(req, resp);
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
