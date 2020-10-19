package net.iccbank.openapi.sdk.model.page;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageBO<E> implements java.io.Serializable {
    //返回的数据
    private List<E> rows;
    //当前页
    private int pageNo;
    //每页几条
    private int pageSize;
    //共几页
    private int totalPage;
    //总条数
    private int totalRow;

}
