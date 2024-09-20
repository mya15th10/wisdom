package net.javatrain.train_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ProductDto {
    private Integer id;
    private String name;
    private double price;
    private String description;
    private Integer categoryId;
}
