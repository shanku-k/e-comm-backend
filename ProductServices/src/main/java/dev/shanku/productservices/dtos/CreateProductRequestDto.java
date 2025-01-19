package dev.shanku.productservices.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//this is our request body/payload for POST calls
public class CreateProductRequestDto {
    private String title;
    private String description;
    private String image;
    private String category;
    private double price;
}
