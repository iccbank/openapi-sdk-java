package net.iccbank.openapi.sdk.model;

import java.io.Serializable;
import java.util.List;

/**
 * @Author kevin
 * @Description
 * @Date Created on 2020/8/31 15:49
 * @since 1.1.0
 */
public class ApiProxyScanningAddress implements Serializable {

    private static final long serialVersionUID = 1;
    //来源 1-新创建 2-导入地址
    public static final Integer SOURCE_IS_NEW = 1;
    public static final Integer SOURCE_IS_LOAD = 2;

    private List<String> addressLists;

    private String linkType;

    private Integer source;

    public ApiProxyScanningAddress(){}

    public List<String> getAddressLists() {
        return addressLists;
    }

    public void setAddressLists(List<String> addressLists) {
        this.addressLists = addressLists;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}
