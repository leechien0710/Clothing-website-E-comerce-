package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.domains.Cart;
import com.sda.java3.ecommerce.domains.Product;
import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.services.cart.CartServiceImpl;
import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.product.ProductService;
import com.sda.java3.ecommerce.utils.Breadcrumb;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CartController extends BaseController {
  @Autowired private CartServiceImpl cartServiceImpl;

  public CartController(ProductService productService, CategoryService categoryService) {
    super(productService, categoryService);
  }

  @GetMapping("/cart")
  public String home(ModelMap modelMap, HttpSession session, Model model) {
    initModelMap(modelMap);
    modelMap.addAttribute(
        "breadcrumbs",
        Arrays.asList(
            Breadcrumb.builder().name("Home").url("/").build(),
            Breadcrumb.builder().name("Cart").url("/cart").last(true).build()));

    if (session == null || session.getAttribute("user1") == null) {
      return "redirect:/sign-in";
    }
    List<Cart> carts = new ArrayList<>();
    User user = (User) session.getAttribute("user1");
    carts = cartServiceImpl.findByUserid(user.getId());
    double tt = 0;
    for (Cart cart : carts) {
      tt += cart.getProduct().getPrice() * cart.getQuantity();
    }
    session.setAttribute("tt", tt);
    model.addAttribute("carts", carts);
    return "cart";
  }

  @PostMapping("/cart")
  public String cart(
      ModelMap modelMap,
      @RequestParam("productId") String productId,
      @RequestParam("quantity") Integer quantity,
      HttpSession session,
      Model model) {
    initModelMap(modelMap);
    modelMap.addAttribute(
        "breadcrumbs",
        Arrays.asList(
            Breadcrumb.builder().name("Home").url("/").build(),
            Breadcrumb.builder().name("Cart").url("/cart").last(true).build()));
    List<Cart> carts = new ArrayList<>();

    if (session == null || session.getAttribute("user1") == null) {
      return "redirect:/sign-in";
    }

    User user = (User) session.getAttribute("user1");
    carts = cartServiceImpl.findByUserid(user.getId());
    if (carts == null || carts.isEmpty()) {
      Product product = productService.getProductById(productId);
      Cart cart = Cart.builder().user(user).product(product).quantity(quantity).build();
      //        		modelMap.addAttribute("cart", cart);
      //        		return"test";
      //        	}
      cartServiceImpl.saveCart(cart);
    }
    boolean isFound = false;
    carts = cartServiceImpl.findByUserid(user.getId());
    for (Cart cart : carts) {
      if (cart.getProduct().getId().toString().equals(productId)) {
        cart.setQuantity(quantity);
        cartServiceImpl.saveCart(cart);
        isFound = true;
        break;
      }
    }
    if (!isFound) {
      Product product = productService.getProductById(productId);
      Cart cart = Cart.builder().user(user).product(product).quantity(quantity).build();
      cartServiceImpl.saveCart(cart);
    }
    carts = cartServiceImpl.findByUserid(user.getId());

    double tt = 0;
    for (Cart cart : carts) {
      tt += cart.getProduct().getPrice() * cart.getQuantity();
    }
    session.setAttribute("tt", tt);
    model.addAttribute("carts", carts);

    return "cart";
    //        	model.addAttribute("user",user);
    //        	return "test";
    //        }
    //
  }

  @GetMapping("/delete/{cartId}")
  public String deleteCart(@PathVariable UUID cartId, Model model) {
    // Xử lý xóa đối tượng cart theo id truyền vào
    // ...
    model.addAttribute("cardid", cartId);
    cartServiceImpl.deleteByid(cartId);
    return "redirect:/cart"; // Chuyển hướng về trang giỏ hàng
  }
}
