package com.grace.bootmall.controller;


import com.grace.bootmall.constant.ProductCategory;
import com.grace.bootmall.dto.ProductRequest;
import com.grace.bootmall.dto.ProductRequestParam;
import com.grace.bootmall.dto.ProductUpdateRequest;
import com.grace.bootmall.model.Product;
import com.grace.bootmall.service.ProductService;
import com.grace.bootmall.util.Page;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Page<Product>> getProducts(@RequestParam(required = false) ProductCategory catagory,
                                                     @RequestParam(required = false) String search,
                                                     @RequestParam(defaultValue = "created_date") String orderBy,
                                                     @RequestParam(defaultValue = "DESC") String sort,
                                                     @RequestParam(defaultValue = "10") @Max(500) @Min(0) Integer limit,
                                                     @RequestParam(defaultValue = "0") @Min(0) Integer offset) {
        ProductRequestParam productRequestParam = new ProductRequestParam();
        productRequestParam.setCategory(catagory);
        productRequestParam.setSearch(search);
        productRequestParam.setOrderBy(orderBy);
        productRequestParam.setSort(sort);
        productRequestParam.setLimit(limit);
        productRequestParam.setOffset(offset);

        List<Product> productList = productService.getProducts(productRequestParam);
        Integer count = productService.countProduct(productRequestParam);
        Page<Product> page = new Page<>() ;
        page.setTotal(count);
        page.setResults(productList);
        page.setLimit(limit);
        page.setOffset(offset);

        return ResponseEntity.status(HttpStatus.OK).body(page);
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductRequest productRequest) {
        Integer productId = productService.createProduct(productRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer productId,
                                                 @RequestBody @Valid ProductUpdateRequest productUpdateRequest) {
        productUpdateRequest.setProductId(productId);
        productService.updateProduct(productUpdateRequest);
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK).body(product);
    }

}
