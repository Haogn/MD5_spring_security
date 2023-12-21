package com.ra.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
    private String categoryName;
    private Boolean status ;
    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
    Set<Product> products ;
}
