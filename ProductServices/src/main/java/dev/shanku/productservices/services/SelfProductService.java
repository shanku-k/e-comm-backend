package dev.shanku.productservices.services;

import dev.shanku.productservices.exceptions.ProductNotFoundException;
import dev.shanku.productservices.models.Category;
import dev.shanku.productservices.models.Product;
import dev.shanku.productservices.repositories.CategoryRepository;
import dev.shanku.productservices.repositories.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service("selfProductService")
public class SelfProductService implements ProductService {

    private CategoryRepository categoryRepository;
    private ProductRepository productRepository;

    public SelfProductService(CategoryRepository categoryRepository,
                              ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> getAllProductsPaginated(long pageNo, long pageSize) {
        return productRepository.findAll(
                PageRequest.of((int) pageNo, (int) pageSize));
    }

    @Override
    public Product getSingleProduct(long id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);
        if (product.isEmpty()) throw new ProductNotFoundException("Product not found");
        return product.get();
    }


    @Override
    public Product createProduct(String title,
                                 String description,
                                 double price,
                                 String imageUrl,
                                 String category) {
        Product p = new Product();

        p.setTitle(title);
        p.setDescription(description);
        p.setPrice(price);
        p.setImageUrl(imageUrl);
        //---Cannot set the category here because the datatype of this param is 'Category' in Product class
        //---and what we are receiving from controller, is 'String' in CreateProductRequestDto class
        //product.setCategory(category); this throws error

        //---before adding a category check if the current category type is already present in the system
        //---if not present add the category and product both

        Category categoryFromDB = categoryRepository.findByTitle(category);
        if (categoryFromDB == null) {
            Category newCategory = new Category();
            newCategory.setTitle(category);
            p.setCategory(newCategory);
        } else {
            p.setCategory(categoryFromDB);
        }
        //now save the new product in DB
        Product createdProduct = productRepository.save(p);
        return createdProduct;
    }
}
