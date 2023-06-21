package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.services.product.ProductService;
import com.sda.java3.ecommerce.services.product.SaveProductRequest;
import com.sda.java3.ecommerce.utils.CustomException;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductApiController {

  private final ProductService productService;

  @GetMapping("/{id}")
  public ResponseEntity<Product> getProductById(@PathVariable String id) {
    return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
  }

  @GetMapping("/base-info/{id}")
  public ResponseEntity<Object> getProductBaseInfoById(@PathVariable String id) {
    try {
      return new ResponseEntity<>(
          productService.getProductInfoById(UUID.fromString(id)), HttpStatus.OK);
    } catch (CustomException e) {
      return new ResponseEntity<>(e.getMessageDetail(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping
  public List<Product> getAllProducts() {
    return productService.findAll();
  }

  @PostMapping
  public ResponseEntity<Object> save(@RequestBody @Valid SaveProductRequest request) {
    try {
      productService.save(request);
    } catch (CustomException e) {
      return new ResponseEntity<>(e.getMessageDetail(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{id}")
  public ResponseEntity<Object> deleteProduct(@PathVariable String id) {
    productService.delete(UUID.fromString(id));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PutMapping(value = "/{id}")
  public ResponseEntity<Object> updateProduct(
      @PathVariable UUID id, @RequestBody SaveProductRequest request) {
    try {
      productService.update(id, request);
    } catch (CustomException e) {
      return new ResponseEntity<>(e.getMessageDetail(), HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    return new ResponseEntity<>(HttpStatus.ACCEPTED);
  }
}
