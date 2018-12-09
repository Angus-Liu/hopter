package org.hopter.framework.bean;

/**
 * 返回数据对象
 *
 * @author Angus
 * @date 2018/11/30
 */
public class Data {

    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
