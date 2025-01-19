/**
 * 
 */
package rw.mangatek.ebm2.core.security.util;

import jakarta.servlet.http.HttpServletRequest;


/**
 * @author pfniy
 *
 */
public class MangatekInternalSecurityUtil {
	private MangatekInternalSecurityUtil() {
		throw new IllegalStateException("MangatekInternalSecurityUtil Utility class");
	}

	public static String getClientIp(HttpServletRequest request) {

		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (remoteAddr == null || "".equals(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}
}
