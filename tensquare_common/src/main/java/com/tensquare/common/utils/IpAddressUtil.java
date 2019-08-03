package com.tensquare.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客户端真实ip
 *
 * @auther alan.chen
 * @time 2019/8/3 11:19 AM
 */
public class IpAddressUtil {


	public String getIpAddress(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.contains(",")) {
			return ip.split(",")[0];
		} else {
			return ip;
		}
	}

}
