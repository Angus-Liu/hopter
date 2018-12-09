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
public final class StreamUtil {

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

    /**
     * 将输入流复制到输出流
     *
     * @param inputStream
     * @param outputStream
     */
    public static void copyStream(InputStream inputStream, OutputStream outputStream) {
        try {
            int length;
            byte[] buffer = new byte[4 * 1024];
            while((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                outputStream.write(buffer, 0, length);
            }
        } catch (Exception e) {
            log.error("copy stream failure", e);
            throw new RuntimeException(e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (Exception e) {
                log.error("close stream failure", e);
            }
        }
    }
}
