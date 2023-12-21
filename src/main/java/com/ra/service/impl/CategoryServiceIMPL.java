package com.ra.service.impl;

import com.ra.dto.request.CategoryRequest;
import com.ra.dto.response.CategoryResponse;
import com.ra.entity.Category;
import com.ra.exception.CustomException;
import com.ra.mapper.CategoryMapper;
import com.ra.repository.ICategoryRepository;
import com.ra.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CategoryServiceIMPL implements ICategoryService {

    @Autowired
    private ICategoryRepository categoryRepository ;
    @Autowired
    private CategoryMapper categoryMapper ;
    @Override
    public Page<CategoryResponse> getAllCategory( String search, Pageable pageable) {
        Page<Category> list ;
        if (search.isEmpty()) {
            list = categoryRepository.findAll(pageable);
        } else {
            list = categoryRepository.findAllByCategoryNameContainingIgnoreCase(search, pageable) ;
        }
        return list.map(item -> categoryMapper.toMapperCategoryResponse(item));
    }

    @Override
    public CategoryResponse getById(Long id) throws CustomException {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new CustomException(" Category not found")) ;
        return categoryMapper.toMapperCategoryResponse(category);
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) throws CustomException {
        if (categoryRepository.existsByCategoryNameIgnoreCase(categoryRequest.getCategoryName())) {
            throw  new CustomException("exists CategoryName") ;
        }
        Category category = categoryRepository.save(categoryMapper.toMapperEntity(categoryRequest)) ;
        return categoryMapper.toMapperCategoryResponse(category);
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest, Long id) throws CustomException {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CustomException("Không tìm thấy danh mục"));

        // Kiểm tra nếu tên danh mục đã thay đổi và đã tồn tại
        if (!categoryRequest.getCategoryName().equalsIgnoreCase(category.getCategoryName()) &&
                categoryRepository.existsByCategoryNameIgnoreCase(categoryRequest.getCategoryName())) {
            throw new CustomException("Tên danh mục đã tồn tại");
        }

        // Cập nhật các trường của danh mục dựa trên categoryRequest
        category.setId(id);
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setStatus(categoryRequest.getStatus());
        // Thêm các cập nhật trường khác nếu cần

        // Lưu và trả về danh mục đã cập nhật
        return categoryMapper.toMapperCategoryResponse(categoryRepository.save(category));
    }


    @Override
    public CategoryResponse changeStatusCategory(Long id) throws CustomException {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new CustomException("Category not found"));
        category.setStatus(!category.getStatus());
        return categoryMapper.toMapperCategoryResponse(category);
    }
}
