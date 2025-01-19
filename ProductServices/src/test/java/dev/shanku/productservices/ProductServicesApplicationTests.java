package dev.shanku.productservices;

import dev.shanku.productservices.models.Product;
import dev.shanku.productservices.projections.ProductProjection;
import dev.shanku.productservices.repositories.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServicesApplicationTests {
    @Autowired
    ProductRepository productRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testingQueries() throws InterruptedException {
                List<Product> products= productRepository.
                findAllByCategory_Title("electronics");
        System.out.println(products);


        List<Product> productList = productRepository.findAllByCategory_Id(1L);

        System.out.println(productList);

        List<ProductProjection> products1 = productRepository.getTitlesAndIdOfAllProductsWithGivenCategoryName("electronics");
        System.out.println(products1);

        List<ProductProjection> productProjections = productRepository.getTitlesAndIdOfAllProductsWithGivenCategoryName("electronics");

        for(ProductProjection productProjection: productProjections){
            System.out.println(productProjection.getId());
            System.out.println(productProjection.getTitle());
        }
        System.out.println();
    }

}
