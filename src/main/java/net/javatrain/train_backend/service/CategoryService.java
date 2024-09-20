package net.javatrain.train_backend.service;

import net.javatrain.train_backend.dto.CategoryDto;
import net.javatrain.train_backend.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoryService {
    //phương thức tạo Category
    CategoryDto createCategory(CategoryDto categoryDto);

    //phương thức lấy danh sách tất cả các Category
    List<CategoryDto> getAllCategories();

    //Phương thức lấy Category bằng ID
    CategoryDto getCategoryById(Integer categoryId);

    //Update a category by ID
    CategoryDto updateCategory(Integer categoryId, CategoryDto categoryDto);

    //Phương thức xo cayegory
    void deleteCategory(Integer categoryId);


}
