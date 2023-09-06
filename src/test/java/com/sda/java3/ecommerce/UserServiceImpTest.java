package com.sda.java3.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;

import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.repositories.UserRepository;
import com.sda.java3.ecommerce.services.user.UserServiceImpl;
@ExtendWith(MockitoExtension.class)
public class UserServiceImpTest {
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private UserServiceImpl userServiceImpl;
	private User user1;
	private User user2;
	private List<User> users; 
	@BeforeEach
	void Setup() {
		users = new ArrayList<>();
		user1 = User.builder().firstName("chien").lastName("le").email("chien@gmail.com").password("123").role(0).created_at(LocalDateTime.now()).build();
		user2 = User.builder().firstName("anh").lastName("le").email("anh@gmail.com").password("123").role(0).created_at(LocalDateTime.now()).build();
		users.add(user1);
		users.add(user2);
	}
	@Test
	void TestSaveUser() {
		when(userRepository.save(any(User.class))).thenReturn(user1);
		User saveuser = userServiceImpl.saveUser(user1);
		verify(userRepository,times(1)).save(any(User.class));
		assertNotNull(saveuser);
	}
	@Test
	void TestCheckEmailExitTrue(){
		String email = "chien@gmail.com";
		when(userRepository.findByEmail(email)).thenReturn(user1);
		assertTrue(userServiceImpl.checkEmailExist(email));
		verify(userRepository,times(1)).findByEmail(email);
	}
	void TestCheckEmailExitFalse() {
		String email="agshdh@gmail.com";
		when(userRepository.findByEmail(email)).thenReturn(null);
		assertFalse(userServiceImpl.checkEmailExist(email));
		verify(userRepository,times(1)).findByEmail(email);
	}
	@Test
	void TestCheckEmailAndPass() {
		String email="chien@gmail.com";
		String pass = "123";
		when(userRepository.findByEmail(email)).thenReturn(user1);
		User testuser = userServiceImpl.checkEmailAndPass(email, pass);
		verify(userRepository,times(1)).findByEmail(email);
		assertNotNull(testuser);
	}
	@Test
	void TestCheckEmailAndPassReturnFalseByPass() {
		String email = "chien@gmail.com";
		String pass = "ahdhs";
		when(userRepository.findByEmail(email)).thenReturn(user1);
		User testUser = userServiceImpl.checkEmailAndPass(email, pass);
		verify(userRepository,times(1)).findByEmail(email);
		assertNull(testUser);
	}
	@Test
	void TestCheckEmailAndPassReturnFalseByEmail(){
		String email = "nghsi@gmail.com";
		String pass = "ahdhs";
		when(userRepository.findByEmail(email)).thenReturn(null);
		User testUser = userServiceImpl.checkEmailAndPass(email, pass);
		verify(userRepository,times(1)).findByEmail(email);
		assertNull(testUser);
	}
}
