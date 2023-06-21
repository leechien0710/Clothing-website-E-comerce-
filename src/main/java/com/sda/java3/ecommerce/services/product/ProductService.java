package com.sda.java3.ecommerce.services.product;

import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.utils.CustomException;
import com.sda.java3.ecommerce.utils.ProductListFilter;
import java.util.List;
import java.util.UUID;

public interface ProductService {
  List<Product> getFeaturedProducts();

  List<Product> getRecentProducts();

  List<Product> getProducts(ProductListFilter filter);

  void createDummyProducts();

  Product getProductById(String id);

  List<Product> findAll();

  void save(SaveProductRequest request) throws CustomException;

  void delete(UUID uuid);

  void update(UUID id, SaveProductRequest request) throws CustomException;

  ProductBaseInfo getProductInfoById(UUID uuid) throws CustomException;
}
