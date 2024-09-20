package net.javatrain.train_backend.service.impl;

import lombok.AllArgsConstructor;
import net.javatrain.train_backend.dto.ProductDto;
import net.javatrain.train_backend.entity.Category;
import net.javatrain.train_backend.entity.Product;
import net.javatrain.train_backend.exception.ResourceNotFoundException;
import net.javatrain.train_backend.mapper.ProductMapper;
import net.javatrain.train_backend.repository.CategoryRepository;
import net.javatrain.train_backend.repository.ProductRepository;
import net.javatrain.train_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public  class ProductServiceImpl implements ProductService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    // Triển khai phương thức createProduct
    @Override
    public ProductDto createOrUpdateProduct(ProductDto productDto) {
        Integer productId = productDto.getId();

        if (productId != null) {
            // Cập nhật sản phẩm
            Product existingProduct = productRepository.findById(productId).orElseThrow(
                    () -> new ResourceNotFoundException("Product with id " + productId + " not found")
            );
            existingProduct.setName(productDto.getName());
            existingProduct.setPrice(productDto.getPrice());
            existingProduct.setDescription(productDto.getDescription());

            // Tìm category theo categoryId trong ProductDto
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category with id " + productDto.getCategoryId() + " not found"));

            existingProduct.setCategory(category);

            Product updatedProduct = productRepository.save(existingProduct);
            return ProductMapper.mapToProductDto(updatedProduct);
        } else {
            // Tạo mới sản phẩm
            Category category = categoryRepository.findById(productDto.getCategoryId())
                    .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + productDto.getCategoryId()));

            Product newProduct = ProductMapper.mapToProduct(productDto, category);  // Đảm bảo ProductMapper có phương thức này
            Product savedProduct = productRepository.save(newProduct);
            return ProductMapper.mapToProductDto(savedProduct);
        }
    }

    // Triển khai phương thức getAllProducts
    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductMapper::mapToProductDto)
                .collect(Collectors.toList());

    }
    // Triển khai phương thức getProductById
    @Override
    public ProductDto getProductById(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return ProductMapper.mapToProductDto(product);
    }
    // Triển khai phương thức updateProduct
    @Override
    public ProductDto updateProduct(Integer productId, ProductDto productDto) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));
        //update
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);

        Product updateProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(updateProduct);
    }
    // Triển khai phương thức deleteProduct
    @Override
    public void deleteProductById(Integer productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    //Triển khai get product by cate
    @Override
    public Page<ProductDto> getProductsByCategory(Integer categoryId, Pageable pageable) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Page<Product> productsPage = productRepository.findByCategory(category, pageable);

        return productsPage.map(ProductMapper::mapToProductDto);
    }
    //add product by cate
    @Override
    public ProductDto createProductInCategory(Integer categoryId, ProductDto productDto) {
        //Find cate by cateid
        Category category = categoryRepository.findById(categoryId) .orElseThrow(() -> new RuntimeException("Category not found"));
        // Chuyển DTO thành entity Product và gán cate cho pro
        Product product = ProductMapper.mapToProduct(productDto, category);
        Product savedProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(savedProduct);
    }

    //Update the category of a product
    @Override
    public ProductDto updateProductInCategory(Integer productId, Integer categoryId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new RuntimeException("Category not found"));

        product.setCategory(category);
        Product updateProduct = productRepository.save(product);
        return ProductMapper.mapToProductDto(updateProduct);
    }
    @Override
    public Page<ProductDto> getPaginatedProducts(Pageable pageable) {
        Page<Product> productsPage = productRepository.findAll(pageable);
        return productsPage.map(ProductMapper::mapToProductDto);
    }

}

