package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.product.ProductService;
import com.sda.java3.ecommerce.services.user.UserServiceImpl;
import java.time.LocalDateTime;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FacebookController extends BaseController {
  @Autowired private UserServiceImpl impl;

  public FacebookController(ProductService productService, CategoryService categoryService) {
    super(productService, categoryService);
    // TODO Auto-generated constructor stub
  }

  @GetMapping("/sign-in/success")
  public String signinsucces(
      Authentication authentication, Model model, HttpServletRequest request) {
    if (authentication == null || authentication.getPrincipal() == null) {
      return "test";
    }
    DefaultOAuth2User user = (DefaultOAuth2User) authentication.getPrincipal();
    Map<String, Object> attributes = user.getAttributes();

    String name = "";
    String email = "";
    Object emailObj = attributes.get("email");
    Object nameObj = attributes.get("name");
    Object loginObj = attributes.get("login");
    if (nameObj != null) {
      name = nameObj.toString();
    } else if (loginObj != null) {
      name = loginObj.toString();
    }
    if (emailObj != null) {
      email = emailObj.toString();
    }
    //	        String lastname = attributes.getOrDefault("localizedLastName",
    // attributes.get("family_name")).toString();
    Object idObj = attributes.get("id");
    if (idObj != null) {
      String id = idObj.toString();
      // sử dụng id ở đây
    }

    model.addAttribute("name", name);
    model.addAttribute("email", email);
    OAuth2AuthenticationToken oauthToken =
        (OAuth2AuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
    String clientName = oauthToken.getAuthorizedClientRegistrationId();
    //	        model.addAttribute("client",clientName);
    //	        String phoneNumber = user.getAttribute("phone_number");
    //	        if (phoneNumber == null) {
    //	            phoneNumber = user.getAttribute("phone");
    //	        }
    HttpSession session = request.getSession();
    User user1 = impl.checkEmailAndPass(email, clientName);
    if (user1 == null) {
      LocalDateTime currentTime = LocalDateTime.now();
      //	            User user2 = new User(null, name, lastname, email, clientName, 0, currentTime,
      // null);
      User user2 =
          User.builder()
              .firstName(name)
              .lastName("lastName")
              .email(email)
              .password(clientName)
              .created_at(currentTime)
              .role(0)
              .build();
      impl.saveUser(user2);
      session.setAttribute("user2", user1);
    }
    if (user1 != null) {
      session.setAttribute("user1", user1);
    }
    return "redirect:/";
    //	        return("test");
  }

  @GetMapping("/sign-out")
  public String signout(HttpSession session) {
    if (session != null) {
      session.removeAttribute("user1");
    }
    return "redirect:/";
  }
}
