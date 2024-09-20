package net.javatrain.train_backend.controller;

import lombok.AllArgsConstructor;
import net.javatrain.train_backend.dto.ProductDto;
import net.javatrain.train_backend.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/products")

public class ProductController {
    private ProductService productService;

    //Build Add Product REST API
    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto) {
        ProductDto savedProduct = productService.createOrUpdateProduct(productDto);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
    //Build list of all products
    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //Build get detail by ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Integer productId) {
        ProductDto product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    //Build Update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") Integer productId,
                                                    @RequestBody ProductDto productDto) {
        ProductDto updatedProduct = productService.updateProduct(productId, productDto);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    //Build delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable("productId") Integer productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>("Product deleted successfully", HttpStatus.OK);
    }

    //update cate of pro
    @PatchMapping("/{productId}/category/{categoryId}")
    public ResponseEntity<ProductDto> updateProductCategory(
            @PathVariable("productId") Integer productId,
            @PathVariable("categoryId") Integer categoryId) {
        ProductDto updatedProduct = productService.updateProductInCategory(productId, categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }
    //Trả danh sách phân trang của sản phẩm
    @GetMapping("/paginated")
    public ResponseEntity<Page<ProductDto>> getPaginatedProducts(
            @PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<ProductDto> products = productService.getPaginatedProducts(pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

}

