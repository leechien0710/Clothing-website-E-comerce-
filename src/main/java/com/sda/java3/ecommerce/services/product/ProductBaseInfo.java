package com.sda.java3.ecommerce.services.product;

import java.time.LocalDateTime;
import java.util.UUID;

import com.sda.java3.ecommerce.domains.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBaseInfo {
  protected String id;
  protected String name;
  protected String description;
  protected double price = 0;
  protected String image;
  protected boolean sale = false;
  protected double salePrice = 0;
  protected int views = 0;
  protected boolean featured = false;
  protected String featureImage;
  protected UUID categoryId;
  private LocalDateTime createdAt;
  private String color;
  private String size;

  public ProductBaseInfo(Product product){
    this.id = product.getId().toString();
    this.categoryId = product.getCategoryId();
    this.name = product.getName();
    this.description = product.getDescription();
    this.size = product.getSize();
    this.color = product.getColor();
    this.createdAt = product.getCreatedAt();
    this.image = product.getImage();
    this.price = product.getPrice();
    this.featureImage = product.getFeatureImage();
    this.sale = product.isSale();
    this.featured = product.isFeatured();
  }
}
