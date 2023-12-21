package com.ra.dto.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProductResponse {

    private Long id ;

    private String productName ;

    private String description ;

    private String image ;

    private Double price ;

    private Boolean status ;

    private Integer stock ;

    private String categoryName ;

}
