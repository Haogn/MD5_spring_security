package com.ra.mapper;

import com.ra.dto.request.CategoryRequest;
import com.ra.dto.request.ProductRequest;
import com.ra.dto.response.CategoryResponse;
import com.ra.dto.response.ProductResponse;
import com.ra.entity.Category;
import com.ra.entity.Product;
import com.ra.exception.CustomException;
import com.ra.repository.ICategoryRepository;
import com.ra.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    @Autowired
    private ICategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper ;
    public ProductResponse toMapperProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .productName(product.getProductName())
                .description(product.getDescription())
                .image(product.getImage())
                .price(product.getPrice())
                .status(product.getStatus())
                .stock(product.getStock())
                .categoryName(product.getCategory().getCategoryName())
                .build();
    }

    public Product toMapperEntity(ProductRequest productRequest) throws CustomException {
        Category category = categoryRepository.findById(productRequest.getCategoryId()).orElseThrow(() -> new CustomException("Category not found")) ;
        return Product.builder()
                .productName(productRequest.getProductName())
                .description(productRequest.getDescription())
                .image(productRequest.getImage().getOriginalFilename())
                .price(productRequest.getPrice())
                .status(productRequest.getStatus())
                .stock(productRequest.getStock())
                .category(category)
                .build();
    }
}
