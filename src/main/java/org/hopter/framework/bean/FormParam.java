package org.hopter.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 封装表单参数
 *
 * @author Angus
 * @date 2018/12/6
 */
@Data
@AllArgsConstructor
public class FormParam {
    private String fieldName;
    private Object fieldValue;
}
