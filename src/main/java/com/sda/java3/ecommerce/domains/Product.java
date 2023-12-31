package com.sda.java3.ecommerce.domains;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sda.java3.ecommerce.services.product.SaveProductRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ec_product")
public class Product {
  @Id
  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "id", updatable = false, nullable = false)
  @Type(type = "org.hibernate.type.UUIDCharType")
  protected UUID id;

  @Column(name = "name")
  protected String name;

  @Column(name = "description")
  protected String description;

  @Column(name = "price")
  protected double price = 0;

  @Column(name = "image")
  protected String image;

  @Column(name = "sale")
  protected boolean sale = false;

  @Column(name = "sale_price")
  protected double salePrice = 0;

  @Column(name = "views")
  protected int views = 0;

  @Column(name = "featured")
  protected boolean featured = false;

  @Column(name = "featured_image")
  protected String featureImage;

  @Column(name = "category_id")
  @Type(type = "org.hibernate.type.UUIDCharType")
  protected UUID categoryId;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(insertable = false, updatable = false, name = "category_id")
  protected Category category;

  @JsonIgnore
  @OneToMany(mappedBy = "product")
  protected List<Cart> items;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "color")
  private String color;

  @Column(name = "size")
  private String size;

  public Product(SaveProductRequest request) {
    this.name = request.getName();
    this.description = request.getDescription();
    this.price = request.getPrice();
    this.sale = request.isSale();
    this.salePrice = request.getSalePrice();
    this.color = request.getColor();
    this.size = request.getSize();
    this.categoryId = UUID.fromString(request.getCategoryId());
    this.image = request.getImage();
    this.createdAt = LocalDateTime.now();
  }
}
