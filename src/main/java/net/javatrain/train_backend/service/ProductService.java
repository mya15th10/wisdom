package net.javatrain.train_backend.service;

import net.javatrain.train_backend.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto createOrUpdateProduct(ProductDto productDto);
    List<ProductDto> getAllProducts();
    ProductDto getProductById(Integer productId);
    ProductDto updateProduct(Integer productId, ProductDto productDto);
    void deleteProductById(Integer productId);
    Page<ProductDto> getProductsByCategory(Integer categoryId, Pageable pageable);
    ProductDto createProductInCategory(Integer categoryId, ProductDto productDto);
    ProductDto updateProductInCategory(Integer productId, Integer categoryId);
    Page<ProductDto> getPaginatedProducts(Pageable pageable);

}
