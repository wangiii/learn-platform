package com.angiii.learnplatform.domain.dto;

import com.angiii.learnplatform.domain.entity.ResourceTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
public class CourseResourceRequest {

    /**
     * 资源 file
     */
    private MultipartFile file;

    /**
     * 资源类型
     */
    private ResourceTypeEnum type;
}
