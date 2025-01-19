package dev.shanku.productservices.controllers;

import dev.shanku.productservices.dtos.CreateProductRequestDto;
import dev.shanku.productservices.dtos.ErrorDto;
import dev.shanku.productservices.exceptions.ProductNotFoundException;
import dev.shanku.productservices.models.Product;
import dev.shanku.productservices.services.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

        public ProductService productService;

    //Injecting the dependency using constructor
    public ProductController(@Qualifier("fakeStoreProductService") ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id){
        Product p= productService.getSingleProduct(id);
        ResponseEntity<Product> responseEntity;
        if(p == null){
            responseEntity=new ResponseEntity<>(p, HttpStatus.NOT_FOUND);
        }
        else{
            responseEntity=new ResponseEntity<>(p, HttpStatus.OK);
        }

        return responseEntity;
    }

    @GetMapping("/products/experiment")
    public List<Product> getProductsPaginated(@RequestParam("pageNo") long page, @RequestParam("pageSize") long size){
        Page<Product> productPage =productService.getAllProductsPaginated(page, size);
        return productPage.getContent();
        //need to convert Page<Product> to List<Product> and then return
    }

    @PostMapping("/products")
    public Product createProduct(@RequestBody CreateProductRequestDto createProductRequestDto){
        return productService.createProduct(createProductRequestDto.getTitle(),
                createProductRequestDto.getDescription(),
                createProductRequestDto.getPrice(),
                createProductRequestDto.getImage(),
                createProductRequestDto.getCategory());
    }

    //we need this method to beautify the response we have received from the controller
    //and send user-friendly response to the client
    //and for that we need DTOs, so we created an ErrorDTO and used here
    //    @ExceptionHandler(ProductNotFoundException.class)
    //    public ResponseEntity<ErrorDto> handleProductNotFoundException(ProductNotFoundException productNotFoundException){
    //        ErrorDto errorDto = new ErrorDto();
    //        errorDto.setMessage(productNotFoundException.getMessage());
    //        ResponseEntity<ErrorDto> responseEntity = new ResponseEntity<>(errorDto, HttpStatus.NOT_FOUND);
    //        return responseEntity;
    //    }
    //--- above is the manual exception handler
}
