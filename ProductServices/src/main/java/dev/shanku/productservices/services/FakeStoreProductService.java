package dev.shanku.productservices.services;

import dev.shanku.productservices.dtos.FakeStoreProductDto;
import dev.shanku.productservices.exceptions.ProductNotFoundException;
import dev.shanku.productservices.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service("fakeStoreProductService")
public class FakeStoreProductService implements ProductService {
    private RestTemplate restTemplate;//using this we can call 3rd party apis
    private RedisTemplate redisTemplate;

    public FakeStoreProductService(RestTemplate restTemplate, RedisTemplate redisTemplate) {
        this.restTemplate = restTemplate;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Product> getAllProducts() {
        FakeStoreProductDto[] fakeStoreProductDtos =
                restTemplate.getForObject("https://fakestoreapi.com/products", FakeStoreProductDto[].class);
        List<Product> products = new ArrayList<>();
        for (FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            Product product = fakeStoreProductDto.toProduct();
            products.add(product);
        }
        return products;
    }

    @Override
    public Product getSingleProduct(long id) {

        //FakeStoreProductDto fakeStoreProductDto= restTemplate.getForObject("https://fakestoreapi.com/products/1" + id, FakeStoreProductDto.class);
        //if(fakeStoreProductDto==null) return null;
        //else return fakeStoreProductDto.toProduct();


        //check for the data in cache
        Product product = (Product) redisTemplate.opsForHash().get("PRODUCTS", "product_" + id);
        if (product != null) {
            //cache hit
            return product;
        }
        //if data is not found in cache
        //-----getForEntity is used to receive the other entities of the response
        //-----like status code, headers etc. and not just the data
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity = restTemplate.getForEntity("https://fakestoreapi.com/products/" + id,
                FakeStoreProductDto.class);

        if (fakeStoreProductDtoResponseEntity.getStatusCode() != HttpStatusCode.valueOf(200)) {
            //handle this exception
        }
        fakeStoreProductDtoResponseEntity.getHeaders();
        FakeStoreProductDto fakeStoreProductDto = fakeStoreProductDtoResponseEntity.getBody();
        if (fakeStoreProductDto == null) {
            throw new ProductNotFoundException("Product with id " + id + " is not present with the service. It is an invalid id.");
        }
        product = fakeStoreProductDto.toProduct();
        redisTemplate.opsForHash().put("PRODUCTS", "product_" + id, product);
        return product;
    }

    @Override

    public Product createProduct(String title,
                                 String description,
                                 double price,
                                 String imageUrl,
                                 String category) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();

        fakeStoreProductDto.setCategory(category);
        fakeStoreProductDto.setImage(imageUrl);
        fakeStoreProductDto.setTitle(title);
        fakeStoreProductDto.setPrice(price);
        fakeStoreProductDto.setDescription(description);

        FakeStoreProductDto fakeStoreProductDto1 = restTemplate.postForObject("https://fakestoreapi.com/products",
                fakeStoreProductDto,
                FakeStoreProductDto.class);

        return fakeStoreProductDto1.toProduct();
    }

    @Override
    public Page<Product> getAllProductsPaginated(long pageNo, long pageSize) {
        return null;
    }

}
