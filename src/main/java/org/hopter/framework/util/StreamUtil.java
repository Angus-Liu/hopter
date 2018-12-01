package org.hopter.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.io.*;

/**
 * 流操作工具类
 *
 * @author Angus
 * @date 2018/12/1
 */
@Slf4j
public class StreamUtil {

    /**
     * 从输入流中获取字符串
     *
     * @param is
     * @return
     */
    public static String getString(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            log.error("get string failure", e);
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
