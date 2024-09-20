package net.javatrain.train_backend.mapper;

import net.javatrain.train_backend.dto.ProductDto;
import net.javatrain.train_backend.entity.Category;
import net.javatrain.train_backend.entity.Product;

public class ProductMapper {
    public static ProductDto mapToProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory().getId() //Product có quan hệ với Category
        );
    }
    // Ánh xạ từ ProductDto sang Product entity
    public static Product mapToProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setCategory(category); // Gán Category cho Product
        return product;
    }
}
