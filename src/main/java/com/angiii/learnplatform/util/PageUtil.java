package com.angiii.learnplatform.util;

import com.angiii.learnplatform.domain.dto.PageRequest;
import com.angiii.learnplatform.domain.dto.PageResponse;

public class PageUtil {

    /**
     * 当前的页数
     */
    private Integer pageNum = 1;

    /**
     * 每页的条数
     */
    private Integer pageSize = 5;

    /**
     * 分页的总条数
     */
    private Integer total = 0;

    public PageUtil(PageRequest pageRequest, Integer total) {
        if (pageRequest != null
                && pageRequest.getPageSize() > 0
                && pageRequest.getPageNum() > 0) {
            this.pageNum = pageRequest.getPageNum();
            this.pageSize = pageRequest.getPageSize();
        }
        if (total != null) {
            this.total = total;
        }
    }

    /**
     * 获取分页初始位置
     */
    public Integer getStart() {
        return (pageNum - 1) * pageSize;
    }

    /**
     * 获取总页数
     */
    public Integer getPages() {
        return total / pageSize + 1;
    }

    /**
     * 每页的条数
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 获取 PageResponse
     */
    public PageResponse getPageResponse() {
        return PageResponse.builder()
                .pageNum(pageNum).pageSize(pageSize).total(total)
                .pages(this.getPages()).build();
    }
}
