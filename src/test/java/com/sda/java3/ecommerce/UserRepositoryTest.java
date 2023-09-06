package com.sda.java3.ecommerce;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import com.sda.java3.ecommerce.ECommerceApplication;
import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.repositories.UserRepository;
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserRepositoryTest {

	@Autowired UserRepository userRepository;
	@Autowired TestEntityManager entityManager;
	@BeforeEach
	public void Setup() {
		List<User> listUsers = new ArrayList<>();
		User user1 = new User();
		user1.setFirstName("Long");
		user1.setLastName("Le");
		user1.setEmail("long@gmail.com");
		user1.setPassword("123");
		user1.setRole(0);
		user1.setCreated_at(LocalDateTime.now());
		entityManager.persist(user1);
		User user2 = new User();
		user2.setFirstName("nhung");
		user2.setLastName("le");
		user2.setEmail("nhung@gmail.com");
		user2.setPassword("234");
		user2.setRole(0);
		user2.setCreated_at(LocalDateTime.now());
		listUsers.add(user2);
		entityManager.persist(user2);
	}
	@Test
	public void TestSaveUser() {
		User user = User.builder().email("chien@gmail.com").firstName("chien").lastName("le")
				.password("1234").role(0).build();
		User saveUser = userRepository.save(user);
		assertNotNull(user.getId());
		assertEquals(saveUser.getPassword(), "1234");
	}
	@Test
	public void TestFindByEmail() {
		User user = userRepository.findByEmail("long@gmail.com");
		assertNotNull(user);
		assertEquals(user.getFirstName(), "Long");
		assertEquals(user.getPassword(), "123");
	}
}