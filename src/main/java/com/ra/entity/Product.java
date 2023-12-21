package com.ra.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "product")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    private String productName ;

    private String description ;

    private String image ;

    private Double price ;

    private Boolean status ;

    private Integer stock ;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category ;
}
