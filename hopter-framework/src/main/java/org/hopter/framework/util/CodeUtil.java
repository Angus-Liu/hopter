package org.hopter.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * 编码与解码操作工具类
 *
 * @author Angus
 * @date 2018/12/1
 */
@Slf4j
public final class CodeUtil {

    /**
     * 将 URL 编码
     *
     * @param source
     * @return
     */
    public static String encodeURL(String source) {
        String target;
        try {
            target = URLEncoder.encode(source, "UTF-8");
        } catch (Exception e) {
            log.error("encode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }

    /**
     * 将 URL 解码
     *
     * @param source
     * @return
     */
    public static  String decodeURL(String source) {
        String target;
        try {
            target = URLDecoder.decode(source, "UTF-8");
        } catch (Exception e) {
            log.error("decode url failure", e);
            throw new RuntimeException(e);
        }
        return target;
    }
}
