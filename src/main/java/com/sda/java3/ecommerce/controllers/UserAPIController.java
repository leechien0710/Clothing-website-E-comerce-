package com.sda.java3.ecommerce.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.services.user.SaveUserRequest;
import com.sda.java3.ecommerce.services.user.UserServiceImpl;
import com.sda.java3.ecommerce.utils.CustomException;

@RestController
@RequestMapping("/api/user")
public class UserAPIController {
	private UserServiceImpl userServiceImpl;
	@GetMapping
	public List<User> GetAllUser(){
		return userServiceImpl.getAllUsers();
	}
	@PostMapping()
	public User save(@RequestBody User user){
		return userServiceImpl.saveUser(user);
	}
}
