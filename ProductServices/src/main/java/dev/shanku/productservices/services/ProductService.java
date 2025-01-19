package dev.shanku.productservices.services;

import dev.shanku.productservices.dtos.CreateProductRequestDto;
import dev.shanku.productservices.exceptions.ProductNotFoundException;
import dev.shanku.productservices.models.Product;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();

    Product getSingleProduct(long id) throws ProductNotFoundException;

    Product createProduct(String title,
                          String description,
                          double price,
                          String imageUrl,
                          String category);

    public Page<Product> getAllProductsPaginated(long pageNo, long pageSize);
}
