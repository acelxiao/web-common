package utils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 */
public class MessageUtil {

    private final static Logger logger =  LoggerFactory.getLogger(MessageUtil.class);
    public static final String DEFAULT_LANG = "zh_HK";
    private static final Map<String, Properties> resourceMap = new HashMap<String, Properties>();

    public static String getMessage(String key) {
        HttpServletRequest request = getHttpRequest();
        String language = request.getParameter("language");
        if (language == null){
            language = request.getLocale().toLanguageTag();
        }
        if (StringUtils.isBlank(language) || StringUtils.length(language) != 5 || StringUtils.equals(language, "zh_TW")) {
            language = DEFAULT_LANG;
        }
        language = language.replace("-","_");
        String filename = "config/message/message_" + language + ".properties";
        return get(key, filename);
    }

    /**
     * 获取配置信息
     *
     * @param key
     * @param filename
     * @return
     */
    public static String get(String key, String filename) {
        try {
            Properties props = resourceMap.get(filename);
            String value = null;
            if (props != null && (value = props.getProperty(key)) != null) {
                return value;
            }
            props = PropertiesLoaderUtils.loadAllProperties(filename);
            if (props != null) {
                resourceMap.put(filename, props);
            }
            return props.getProperty(key, key);
        } catch (Exception e) {
            logger.error("can't find the resource in the file: {}, key={}", filename, key);
            return "";
        }
    }

    public static HttpServletRequest getHttpRequest() {
        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs == null) {
            return null;
        }
        return attrs.getRequest();
    }
}