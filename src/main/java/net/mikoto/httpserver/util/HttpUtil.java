package net.mikoto.httpserver.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mikoto
 * @date 2021/12/4 9:43
 */
public class HttpUtil {
    public static final Integer TWO = 2;

    /**
     * Get data by from data.
     *
     * @param formData From data string.
     * @return A dic.
     */
    public static Map<String, String> formData2Dic(String formData) {
        Map<String, String> result = new HashMap<>(10);
        if (formData == null || formData.trim().length() == 0) {
            return result;
        }
        final String[] items = formData.split("&");
        Arrays.stream(items).forEach(item -> {
            final String[] keyAndVal = item.split("=");
            if (keyAndVal.length == TWO) {
                final String key = URLDecoder.decode(keyAndVal[0], StandardCharsets.UTF_8);
                final String val = URLDecoder.decode(keyAndVal[1], StandardCharsets.UTF_8);
                result.put(key, val);
            }
        });
        return result;
    }
}
