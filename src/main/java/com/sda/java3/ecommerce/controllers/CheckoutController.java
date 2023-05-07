package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.domains.Cart;
import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.services.cart.CartServiceImpl;
import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.product.ProductService;
import com.sda.java3.ecommerce.utils.Breadcrumb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpSession;

@Controller
public class CheckoutController extends BaseController {
    public CheckoutController(ProductService productService, CategoryService categoryService) {
        super(productService, categoryService);
    }
    @Autowired
    private CartServiceImpl cartServiceImpl;
    @GetMapping("/checkout")
    public String home(ModelMap modelMap,HttpSession session,Model model) {
        initModelMap(modelMap);
        modelMap.addAttribute("breadcrumbs", Arrays.asList(
                Breadcrumb.builder().name("Home").url("/").build(),
                Breadcrumb.builder().name("Checkout").url("/checkout").last(true).build()
        ));
        List<Cart> carts = new ArrayList<>();
        User user = (User) session.getAttribute("user1");
    	carts = cartServiceImpl.findByUserid(user.getId());
    	double tt=0;
    	for (Cart cart:carts) {
    		tt += cart.getProduct().getPrice()* cart.getQuantity();
    	}
    	int tt1 = Double.valueOf(tt).intValue();
    	 session.setAttribute("tt", tt1);
         model.addAttribute("carts",carts);
        return "checkout";
    }
    @GetMapping("/checkout/delete/{cartId}")
    public String deleteCart(@PathVariable UUID cartId,Model model) {
        // Xử lý xóa đối tượng cart theo id truyền vào
        // ...
    	model.addAttribute("cardid", cartId);
    	cartServiceImpl.deleteByid(cartId);
        return "redirect:/checkout"; // Chuyển hướng về trang giỏ hàng
    }
    @GetMapping("/payment")
    public String payment() {
    	return "paymentsuccess";
    }
}
