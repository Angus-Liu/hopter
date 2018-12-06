package org.hopter.framework.bean;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hopter.framework.util.CastUtil;
import org.hopter.framework.util.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 请求参数对象
 *
 * @author Angus
 * @date 2018/11/30
 */
public class Param {

    /**
     * 封装表单参数
     */
    private List<FormParam> formParamList;

    /**
     * 封装文件参数
     */
    private List<FileParam> fileParamList;

    public Param(List<FormParam> formParamList) {
        this.formParamList = formParamList;
    }

    public Param(List<FormParam> formParamList, List<FileParam> fileParamList) {
        this.formParamList = formParamList;
        this.fileParamList = fileParamList;
    }

    /**
     * 获取请求参数映射
     *
     * @return
     */
    public Map<String, Object> getFieldMap() {
        Map<String, Object> fieldMap = new HashMap<>();
        formParamList.forEach(formParam -> {
            String fieldName = formParam.getFieldName();
            Object fieldValue = formParam.getFieldValue();
            if (fieldMap.containsKey(fieldName)) {
                fieldValue = fieldMap.get(fieldName) + StringUtil.SEPARATOR + fieldValue;
            }
            fieldMap.put(fieldName, fieldValue);
        });
        return fieldMap;
    }

    /**
     * 获取上传文件映射
     *
     * @return
     */
    public Map<String, List<FileParam>> getFileMap() {
        Map<String, List<FileParam>> fileMap = new HashMap<>();
        fileParamList.forEach(fileParam -> {
            String fieldName = fileParam.getFieldName();
            List<FileParam> fileParamList = fileMap.getOrDefault(fieldName, new ArrayList<>());
            fileParamList.add(fileParam);
            fileMap.put(fieldName, fileParamList);
        });
        return fileMap;
    }

    /**
     * 获取所有上传文件
     *
     * @param fieldName
     * @return
     */
    public List<FileParam> getFileList(String fieldName) {
        return getFileMap().get(fieldName);
    }

    /**
     * 获取唯一上传文件
     *
     * @param fieldName
     * @return
     */
    public FileParam getFile(String fieldName) {
        List<FileParam> fileParamList = getFileList(fieldName);
        if (CollectionUtils.isNotEmpty(fileParamList) && fileParamList.size() == 1) {
            return fileParamList.get(0);
        }
        return null;
    }

    /**
     * 验证参数是否为空
     *
     * @return
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(formParamList) && CollectionUtils.isEmpty(fileParamList);
    }

    /**
     * 根据参数名获取 String 型参数值
     *
     * @param name
     * @return
     */
    public String getString(String name) {
        return CastUtil.castString(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 double 型参数值
     *
     * @param name
     * @return
     */
    public double getDouble(String name) {
        return CastUtil.castDouble(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 long 型参数值
     *
     * @param name
     * @return
     */
    public long getLong(String name) {
        return CastUtil.castLong(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 int 型参数值
     *
     * @param name
     * @return
     */
    public int getInt(String name) {
        return CastUtil.castInt(getFieldMap().get(name));
    }

    /**
     * 根据参数名获取 Boolean 型参数值
     *
     * @param name
     * @return
     */
    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(getFieldMap().get(name));
    }
}
