package net.javatrain.train_backend.mapper;

import net.javatrain.train_backend.dto.CategoryDto;
import net.javatrain.train_backend.entity.Category;

public class CategoryMapper {
    // Ánh xạ từ CategoryDto sang Category (Entity)
    public static CategoryDto mapToCategoryDto(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName(),
                category.getDescription()
        );
    }
    // Ánh xạ từ Category (Entity) sang CategoryDto
    public static Category mapToCategory(CategoryDto categoryDto) {
        return new Category(
                categoryDto.getId(),
                categoryDto.getName(),
                categoryDto.getDescription()
        );
    }
}
