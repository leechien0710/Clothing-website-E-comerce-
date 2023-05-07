package com.sda.java3.ecommerce.services.cart;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sda.java3.ecommerce.domains.Cart;
import com.sda.java3.ecommerce.repositories.CartRepository;

@Service
public class CartServiceImpl {
	@Autowired
	private CartRepository cartRepository;
	public Cart saveCart(Cart cart) {
		return cartRepository.save(cart);
	}
	public List<Cart> findByUserid(UUID id) {
		List<Cart> carts = new ArrayList<>();
		carts = cartRepository.findByUser_id(id);
		return carts;
	}
	public void deleteByid(UUID id) {
		cartRepository.deleteById(id);
	}
}
