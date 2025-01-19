package dev.shanku.productservices.dtos;

import dev.shanku.productservices.models.Category;
import dev.shanku.productservices.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FakeStoreProductDto {
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;

    public Product toProduct() {
        Product product = new Product();
        product.setTitle(title);
        product.setDescription(description);
        product.setPrice(price);
        product.setImageUrl(image);

        Category category1 = new Category();
        category1.setTitle(category);

       // product.setCategory(category1);
        product.setImageUrl(image);

        return product;
    }

}
