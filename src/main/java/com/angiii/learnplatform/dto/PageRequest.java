package com.angiii.learnplatform.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageRequest {

    private Integer pageNum;
    private Integer pageSize;
}
