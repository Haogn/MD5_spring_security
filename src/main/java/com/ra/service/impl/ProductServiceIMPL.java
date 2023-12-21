package com.ra.service.impl;

import com.ra.dto.request.ProductRequest;
import com.ra.dto.response.ProductResponse;
import com.ra.entity.Product;
import com.ra.exception.CustomException;
import com.ra.mapper.ProductMapper;
import com.ra.repository.IProductRepository;
import com.ra.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;
import java.io.File;
import java.io.IOException;

@Service
public class ProductServiceIMPL implements IProductService {
    @Autowired
    private IProductRepository productRepository ;

    @Autowired
    private ProductMapper productMapper ;

    @Value("${path-upload}")
    private String pathUpload;

    @Value("${server.port}")
    private Long port;

    @Override
    public Page<ProductResponse> getAllProduct(String search, Pageable pageable) {
        Page<Product> list ;
        if (search.isEmpty()) {
            list = productRepository.findAll(pageable) ;
        } else {
            list = productRepository.findAllByProductNameContainingIgnoreCase(search, pageable) ;
        }

        return list.map(item -> productMapper.toMapperProductResponse(item)) ;
    }

    @Override
    public ProductResponse getById(Long id) throws CustomException {
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException("Product not found"));
        return productMapper.toMapperProductResponse(product);
    }

    @Override
    public ProductResponse save(ProductRequest productRequest) throws CustomException {
        if (productRequest.getImage().isEmpty()) {
            throw new CustomException("File image not found");
        }
        String fileName = productRequest.getImage().getOriginalFilename();
        try {
            FileCopyUtils.copy(productRequest.getImage().getBytes(), new File(pathUpload + fileName));
        } catch (IOException e) {
            throw new CustomException("Error saving image file" + e.getMessage());
        }

        productRequest.setImage(productRequest.getImage());
        Product product = productRepository.save(productMapper.toMapperEntity(productRequest)) ;
        return productMapper.toMapperProductResponse(product);
    }

    @Override
    public ProductResponse update(ProductRequest productRequest, Long id) throws CustomException {
        // Kiểm tra xem sản phẩm có tồn tại hay không
        Product productEdit = productRepository.findById(id).orElseThrow(() -> new CustomException("Product not found"));

        // Nếu sản phẩm tồn tại
        if (productEdit != null) {
            // Kiểm tra xem có ảnh mới được cung cấp không
            if (productRequest.getImage() != null && !productRequest.getImage().isEmpty()) {
                try {
                    // Lưu ảnh mới vào đường dẫn
                    String fileName = productRequest.getImage().getOriginalFilename();
                    File newFile = new File(pathUpload + fileName);
                    productRequest.getImage().transferTo(newFile);

                    // Cập nhật đường dẫn ảnh trong đối tượng sản phẩm
                    productEdit.setImage(pathUpload + fileName);
                } catch (IOException e) {
                    throw new CustomException("Error saving Image file " + e.getMessage());
                }
            } else {
                // Nếu không có ảnh mới, giữ nguyên đường dẫn ảnh cũ
                productEdit.setImage(productEdit.getImage());
            }

            // Cập nhật các thông tin khác của sản phẩm
            productEdit.setProductName(productRequest.getProductName());
            productEdit.setDescription(productRequest.getDescription());
            productEdit.setPrice(productRequest.getPrice());

            // Lưu sản phẩm đã cập nhật vào cơ sở dữ liệu
            Product updateProduct = productRepository.save(productEdit);

            // Chuyển đổi và trả về đối tượng ProductResponse
            return productMapper.toMapperProductResponse(updateProduct);
        } else {
            throw new CustomException("Product not found");
        }
    }


    @Override
    public ProductResponse changeStatusProduct(Long id) throws CustomException {
        Product product = productRepository.findById(id).orElseThrow(() -> new CustomException("Product not found")) ;
        product.setStatus(!product.getStatus());
        return productMapper.toMapperProductResponse(product);
    }
}
