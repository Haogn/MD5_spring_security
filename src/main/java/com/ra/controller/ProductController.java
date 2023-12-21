package com.ra.controller;

import com.ra.dto.request.ProductRequest;
import com.ra.exception.CustomException;
import com.ra.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private IProductService productService ;

    @GetMapping
    public ResponseEntity<?> getAllProduct(@RequestParam(defaultValue = "") String search ,
                                           @PageableDefault(size = 2, page = 0, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return new ResponseEntity<>(productService.getAllProduct(search,pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) throws CustomException {
        return new ResponseEntity<>(productService.getById(id), HttpStatus.OK) ;
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@ModelAttribute ProductRequest productRequest) throws CustomException {
        return new ResponseEntity<>(productService.save(productRequest), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id ,
                                           @ModelAttribute ProductRequest productRequest) throws CustomException {
        return new ResponseEntity<>(productService.update(productRequest,id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeStatusProduct(@PathVariable Long id) throws CustomException {
        return  new ResponseEntity<>(productService.changeStatusProduct(id), HttpStatus.OK);
    }
}
