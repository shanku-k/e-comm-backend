package dev.shanku.productservices.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category extends BaseModel {
    private String title;

    @OneToMany(fetch = jakarta.persistence.FetchType.EAGER, mappedBy = "category", cascade = CascadeType.REMOVE)
    @JsonIgnore
    List<Product> products;
}
