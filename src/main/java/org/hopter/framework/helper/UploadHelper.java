package org.hopter.framework.helper;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.hopter.framework.bean.FileParam;
import org.hopter.framework.bean.FormParam;
import org.hopter.framework.bean.Param;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
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
     * 初始化
     *
     * @param servletContext
     */
    public static void init(ServletContext servletContext) {
        File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否为 multipart 类型
     *
     * @param request
     * @return
     */
    public static boolean isMultipart(HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    public static Param createParam(HttpServletRequest request) {
        List<FormParam> formParamList = new ArrayList<>();
        List<FileParam> fileParamList = new ArrayList<>();
        try {
            Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(request);
            for(Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap.entrySet()) {
                String fieldName = fileItemListEntry.getKey();
                List<FileItem> fileItemList = fileItemListEntry.getValue();
                if (CollectionUtils.isNotEmpty(fileItemList)) {
                    for (FileItem fileItem : fileItemList) {
                        if (fileItem.isFormField()) {
                            String fieldValue = fileItem.getString("UTF-8");
                            formParamList.add(new FormParam(fieldName, fieldValue));
                        } else {
                            String fileName = FileUtil.getRealFileName(new String(fileItem.getName().getBytes(), "UTF-8"));
                            if (StringUtils.isNotEmpty(fileName)) {
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

}
