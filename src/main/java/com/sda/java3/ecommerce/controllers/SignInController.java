package com.sda.java3.ecommerce.controllers;

import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.product.ProductService;
import com.sda.java3.ecommerce.services.user.UserServiceImpl;
import com.sda.java3.ecommerce.utils.Breadcrumb;

import io.micrometer.core.ipc.http.HttpSender.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class SignInController extends BaseController{
    public SignInController(ProductService productService, CategoryService categoryService) {
        super(productService, categoryService);
    }
    @Autowired
    private UserServiceImpl impl;
    @GetMapping("/sign-in")
    public String home(ModelMap modelMap,Model model) {
        initModelMap(modelMap);
        modelMap.addAttribute("breadcrumbs", Arrays.asList(
                Breadcrumb.builder().name("Home").url("/").build(),
                Breadcrumb.builder().name("Sign-in").url("/sign-in").last(true).build()
        ));
        model.addAttribute("user",new User());
        return "sign-in";
    }
    @PostMapping("/sign-in")
    public String login(ModelMap modelMap, @ModelAttribute("user") User user,
            BindingResult bindingResult, RedirectAttributes attributes,
            HttpServletRequest request,Model model) {
    	initModelMap(modelMap);
        // Kiểm tra dữ liệu nhập vào
        if (bindingResult.hasErrors()) {
            return "sign-in";
        }
        model.addAttribute("email", user.getEmail());
        model.addAttribute("password", user.getPassword());
    	HttpSession session = request.getSession();
    	User user1 = impl.checkEmailAndPass(user.getEmail(), user.getPassword());
    	if (user1==null) {
            bindingResult.rejectValue("email", "error.user", "Incorrect account or password");
            return "sign-in";
        }
    	if(user1!=null) {
    		session.setAttribute("user1", user1);
    	}
        return "redirect:/";
        	
//        // Tìm kiếm user trong database
//        if(!impl.checkEmailAndPass(user.getEmail(), user.getPassword())) {
//        	attributes.addFlashAttribute("error", "Email or password is incorrect");
//            return "redirect:/sign-in";
//        }
//        else {
//        	HttpSession session = request.getSession();
//            
//        // Lưu thông tin user vào session
//        session.setAttribute("firstName", user.getFirstName());
//
//        // Chuyển hướng về trang home
//        return "home";
//        }
    }
    
}
