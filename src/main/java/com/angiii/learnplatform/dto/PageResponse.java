package com.angiii.learnplatform.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageResponse {

    private Integer pageSize; // 每页多少条
    private Integer pageNum; // 查询的页号
    private Integer total; // 总共多少条
    private Integer pages; // 总页数
    private Integer size; // 该页多少条
    private Object list; // 该页内容
}
