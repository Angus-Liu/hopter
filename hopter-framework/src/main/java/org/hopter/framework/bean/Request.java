package org.hopter.framework.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.hopter.framework.enums.RequestMethod;

/**
 * 封装请求信息
 *
 * @author Angus
 * @date 2018/11/29
 */
@Data
@AllArgsConstructor
public class Request {

    /**
     * 请求方法
     */
    private RequestMethod method;

    /**
     * 请求路径
     */
    private String path;
}
