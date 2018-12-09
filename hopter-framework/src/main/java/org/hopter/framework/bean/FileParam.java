package org.hopter.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.InputStream;

/**
 * 封装上传文件参数
 *
 * @author Angus
 * @date 2018/12/6
 */
@Data
@AllArgsConstructor
public class FileParam {
    /**
     * 文件表单的字段名
     */
    private String fieldName;
    /**
     * 上传文件的文件名
     */
    private String fileName;
    /**
     * 上传文件的文件大小
     */
    private long fileSize;
    /**
     * 上传文件的 Content-Type，可判断文件类型
     */
    private String contentType;
    /**
     * 上传文件的字节输入流
     */
    private InputStream inputStream;
}
