package utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Acel
 * @since 2017/10/23
 */
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 兼容旧版语言标识
     * @param language
     * @return
     */
    public String getOldLanguage(String language){
        switch (language){
            case "zh-CN":
                language = "zh_CH";
                break;
            default:
                break;
        }
        language = language.replace("-","_");
        return language;
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
            if ("127.0.0.1".equals(ip)) {
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    logger.error(e.getMessage(), e);
                }
                ip = inet.getHostAddress();
            }
        }
        return ip.indexOf(",") > -1 ? ip.substring(0, ip.indexOf(",")) : ip;
    }

}
