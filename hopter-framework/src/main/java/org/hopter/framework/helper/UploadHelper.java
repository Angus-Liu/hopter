package org.hopter.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.hopter.framework.bean.FileParam;
import org.hopter.framework.bean.FormParam;
import org.hopter.framework.bean.Param;
import org.hopter.framework.util.FileUtil;
import org.hopter.framework.util.StreamUtil;
import org.hopter.framework.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传助手类
 *
 * @author Angus
 * @date 2018/12/6
 */
@Slf4j
public final class UploadHelper {

    /**
     * Apache Commons FileUpload 提供的 Servlet 文件上传对象
     */
    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化 ServletFileUpload 对象，设置上传文件的临时目录和上传文件的最大限制
     *
     * @param servletContext
     */
    public static void init(ServletContext servletContext) {
        // 上传文件的临时目录设置为应用服务器的临时目录
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        log.debug("Repository: {}", repository);
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        // 上传文件的最大限制由用户提供
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否为 multipart 类型（只有上传文件时对应的请求类型才是 multipart 类型）
     *
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 从当前请求中创建 Param 对象
     *
     * @param request
     * @return
     */
    public static Param createParam(HttpServletRequest request) {
        log.debug("Upload file request...");
        List<FormParam> formParamList = new ArrayList<>();
        List<FileParam> fileParamList = new ArrayList<>();
        try {
            // 使用 ServletFileUpload 对象解析请求参数
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            for (Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap.entrySet()) {
                String fieldName = fileItemListEntry.getKey();
                List<FileItem> fileItemList = fileItemListEntry.getValue();
                if (CollectionUtils.isNotEmpty(fileItemList)) {
                    for (FileItem fileItem : fileItemList) {
                        if (fileItem.isFormField()) {
                            String fieldValue = fileItem.getString("UTF-8");
                            formParamList.add(new FormParam(fieldName, fieldValue));
                        } else {
                            String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                            if (StringUtil.isNotEmpty(fileName)) {
                                long fileSize = fileItem.getSize();
                                String contentType = fileItem.getContentType();
                                InputStream inputStream = fileItem.getInputStream();
                                fileParamList.add(new FileParam(fieldName, fileName, fileSize, contentType, inputStream));
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("create param failure", e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);
    }

    /**
     * 上传文件
     *
     * @param basePath
     * @param fileParam
     */
    public static void uploadFile(String basePath, FileParam fileParam) {
        try {
            if (fileParam != null) {
                String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (Exception e) {
            log.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量上传文件
     *
     * @param basePath
     * @param fileParamList
     */
    public static void uploadFile(String basePath, List<FileParam> fileParamList) {
        fileParamList.forEach(fileParam -> uploadFile(basePath, fileParam));
    }
}
