package dev.shanku.productservices.repositories;

import dev.shanku.productservices.models.Category;
import dev.shanku.productservices.models.Product;
import dev.shanku.productservices.projections.ProductProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product save(Product p);

    @Override
    List<Product> findAll();

    @Override
    Optional<Product> findById(Long id); //why this method id returning the Optional<Product> datatype
    //because there could be chances that the searched id is not present in the system

    List<Product> findByCategory(Category category);

    List<Product> findAllByCategory_Title(String title);

    List<Product> findAllByCategory_Id(Long categoryId);

    List<Product> findByTitleStartingWithAndIdEqualsAndPriceLessThan(String title, Long id, double price);
    //--- above all are declared queries

    @Query("select p.title as title, p.id as id from Product p where p.category.title = :categoryName")
    List<ProductProjection> getTitlesAndIdOfAllProductsWithGivenCategoryName(@Param("categoryName") String categoryName);
    //---above is HQL query

    @Query(value = "select * from products p where p.id = 1 and p.title = :productTitle", nativeQuery = true)
    List<ProductProjection> getTitlesAndIdOfAllProductsWithCategoryNameEquals(@Param("productTitle") String productTitle);
    //---above is native sql query

}
