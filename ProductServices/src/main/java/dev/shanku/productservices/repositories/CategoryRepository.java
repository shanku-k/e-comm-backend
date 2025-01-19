package dev.shanku.productservices.repositories;

import dev.shanku.productservices.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByTitle(String title); //JPA method, it will fetch the given title by using
    // select query and return it in Category object datatype
    Optional<Category> findById(Long id);


}
