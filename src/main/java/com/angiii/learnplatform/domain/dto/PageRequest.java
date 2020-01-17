package com.angiii.learnplatform.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageRequest {

    private Integer pageNum;
    private Integer pageSize;
}
