package com.ra.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductRequest {
    private String  productName ;

    private String description ;

    private Double price ;

    private Boolean status ;

    private Integer stock ;

    private Long categoryId ;

    private MultipartFile image;

}
