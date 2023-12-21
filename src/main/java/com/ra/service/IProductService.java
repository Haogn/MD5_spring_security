package com.ra.service;

import com.ra.dto.request.ProductRequest;
import com.ra.dto.response.ProductResponse;
import com.ra.entity.Product;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductService {
    Page<ProductResponse> getAllProduct(String search, Pageable pageable) ;
    ProductResponse getById(Long id) throws CustomException;
    ProductResponse save(ProductRequest productRequest) throws CustomException ;
    ProductResponse update(ProductRequest productRequest,Long id) throws CustomException ;

    ProductResponse changeStatusProduct(Long id) throws CustomException ;

}
