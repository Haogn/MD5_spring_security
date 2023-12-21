package com.ra.service;

import com.ra.dto.request.CategoryRequest;
import com.ra.dto.response.CategoryResponse;
import com.ra.exception.CustomException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICategoryService {
    Page<CategoryResponse> getAllCategory(String search, Pageable pageable);

    CategoryResponse getById(Long id ) throws CustomException;
    CategoryResponse save (CategoryRequest categoryRequest) throws CustomException;
    CategoryResponse update(CategoryRequest categoryRequest , Long id) throws CustomException;
    CategoryResponse changeStatusCategory(Long id ) throws CustomException;




}
