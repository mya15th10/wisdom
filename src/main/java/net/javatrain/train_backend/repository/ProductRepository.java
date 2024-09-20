package net.javatrain.train_backend.repository;

import net.javatrain.train_backend.entity.Category;
import net.javatrain.train_backend.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository <Product, Integer>{
    // Tìm danh sách sản phẩm theo Category và hỗ trợ phân trang
    Page<Product> findByCategory(Category category, Pageable pageable);
}
