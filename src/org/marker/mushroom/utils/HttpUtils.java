package org.marker.mushroom.utils;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {

	public static StringBuffer getRequestURL(HttpServletRequest req) {
		StringBuffer url = new StringBuffer();
		String scheme = req.getScheme();
		String contextPath = req.getContextPath();
		int port = req.getServerPort();
		url.append(scheme); // http, https
		url.append("://");
		url.append(req.getServerName());
		if ((scheme.equals("http") && port != 80)
				|| (scheme.equals("https") && port != 443)) {
			url.append(':');
			url.append(req.getServerPort());
		}
		url.append(contextPath);
		return url;
	}
}
