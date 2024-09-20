package net.javatrain.train_backend.service.impl;

import lombok.AllArgsConstructor;
import net.javatrain.train_backend.dto.CategoryDto;
import net.javatrain.train_backend.entity.Category;
import net.javatrain.train_backend.mapper.CategoryMapper;
import net.javatrain.train_backend.repository.CategoryRepository;
import net.javatrain.train_backend.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    // Triển khai phương thức createCategory
    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {

        Category category = CategoryMapper.mapToCategory(categoryDto);
        Category saveCategory = categoryRepository.save(category);
        return CategoryMapper.mapToCategoryDto(saveCategory);
    }

    // Triển khai phương thức getAllCategories
    @Override
    public List<CategoryDto> getAllCategories() {
        // Lấy tất cả các Category từ database
        List<Category> categories = categoryRepository.findAll();

        // Ánh xạ từ Category entity sang CategoryDto
        return categories.stream()
                .map(CategoryMapper::mapToCategoryDto)
                .collect(Collectors.toList());
    }

    //Triển khai phương thức getCategoryId
    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));
        return CategoryMapper.mapToCategoryDto(category);
    }

    //Triển khai phương thức updateCategory
    @Override
    public CategoryDto updateCategory (Integer categoryId, CategoryDto categoryDto) {
        // Kiểm tra xem danh mục có tồn tại không
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        // Cập nhật thông tin danh mục
        category.setName(categoryDto.getName());
        category.setDescription(categoryDto.getDescription());
        // Lưu danh mục đã cập nhật vào cơ sở dữ liệu
        Category updateCategory = categoryRepository.save(category);
        // Chuyển đổi thành CategoryDto và trả về
        return CategoryMapper.mapToCategoryDto(updateCategory);
    }

    //Triển khai phương thức deleteCategory
    @Override
    public void deleteCategory(Integer categoryId) {
        Category category =categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        categoryRepository.delete(category);
    }




}


