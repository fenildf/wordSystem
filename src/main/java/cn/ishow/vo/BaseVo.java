package cn.ishow.vo;

import java.io.Serializable;

public class BaseVo implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -5142189012310232629L;
    //��ڼ�ҳ
    private Integer pageNum;
    //��ǰ��ʾ����������
    private Integer pageSize;

    private String search;

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }


}
