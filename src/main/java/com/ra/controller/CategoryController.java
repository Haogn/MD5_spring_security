package com.ra.controller;

import com.ra.dto.request.CategoryRequest;
import com.ra.exception.CustomException;
import com.ra.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private ICategoryService categoryService ;

    @GetMapping
    public ResponseEntity<?> getAllCategory(@RequestParam(defaultValue = "") String search,
                                            @PageableDefault(size = 2, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(categoryService.getAllCategory(search,pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(categoryService.getById(id), HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest categoryRequest) throws CustomException {
        return new ResponseEntity<>(categoryService.save(categoryRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryRequest categoryRequest ) throws CustomException {
        return new ResponseEntity<>(categoryService.update(categoryRequest,id), HttpStatus.OK) ;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeStatusCategory(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(categoryService.changeStatusCategory(id), HttpStatus.OK);
    }
}
