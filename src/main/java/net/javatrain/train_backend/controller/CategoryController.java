package net.javatrain.train_backend.controller;

import lombok.AllArgsConstructor;
import net.javatrain.train_backend.dto.CategoryDto;
import net.javatrain.train_backend.dto.ProductDto;
import net.javatrain.train_backend.service.CategoryService;
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
@RequestMapping("/api/categories")
public class CategoryController {
    private CategoryService categoryService;
    private ProductService productService;

    //build Add Category REST API
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.createCategory(categoryDto);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }
    // Build Get All Categories REST API
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<CategoryDto> Categories = categoryService.getAllCategories();
        return new ResponseEntity<>(Categories, HttpStatus.OK);
    }
    //Build Get details category by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Integer categoryId) {
        CategoryDto category = categoryService.getCategoryById(categoryId);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    //Update a category by ID
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") Integer categoryId,
                                                      @RequestBody CategoryDto categoryDto) {
        CategoryDto updatedCategory = categoryService.updateCategory(categoryId, categoryDto);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
    }

    //Delete a category by ID
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    //Get lits product by categoryId
    @GetMapping("/{categoryId}/products")
    public ResponseEntity<Page<ProductDto>> getProductsByCategory(
            @PathVariable("categoryId") Integer categoryId,
            @PageableDefault(page = 0, size = 10) Pageable pageable) {
        //Call method
        Page<ProductDto> products = productService.getProductsByCategory(categoryId, pageable);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    //Add a product to a specific category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto> createProductInCategory(
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody ProductDto productDto) {
        //call method service to add pro
        ProductDto savedProduct = productService.createProductInCategory(categoryId, productDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }


}
