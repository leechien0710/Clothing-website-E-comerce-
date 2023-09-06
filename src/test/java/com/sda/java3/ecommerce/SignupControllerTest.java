package com.sda.java3.ecommerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;

import com.sda.java3.ecommerce.controllers.SignUpController;
import com.sda.java3.ecommerce.domains.User;
import com.sda.java3.ecommerce.services.category.CategoryService;
import com.sda.java3.ecommerce.services.user.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class SignupControllerTest {

    private MockMvc mockMvc;
    @Mock
    private CategoryService categoryService;
    @Mock
    private UserServiceImpl userService;
    
    @InjectMocks
    private SignUpController signupController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(signupController).build();
    }

    @Test
    void testProcessSignupForm_WithValidUser() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");
        BindingResult bindingResult = mock(BindingResult.class);
        ModelMap modelMap = new ModelMap();
        Model model = mock(Model.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        when(userService.checkEmailExist(user.getEmail())).thenReturn(false);

        String viewName = signupController.processSignupForm(user, bindingResult, modelMap, model, request);

        verify(userService, times(1)).saveUser(user);
        verifyNoMoreInteractions(userService);

        verify(bindingResult, never()).rejectValue(anyString(), anyString(), anyString());

        ModelAndView modelAndView = (ModelAndView) modelMap.get("modelAndView");
        assertNotNull(modelAndView);
        assertEquals("sign-in", modelAndView.getViewName());
    }

    @Test
    void testProcessSignupForm_WithInvalidUser() throws Exception {
        ModelMap modelMap = new ModelMap();
        User user = new User();
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);

        String viewName = signupController.processSignupForm(user, bindingResult, modelMap, null, null);

        assertEquals("sign-up", viewName);
        verifyZeroInteractions(userService);
        verify(bindingResult, never()).rejectValue(anyString(), anyString(), anyString());
    }

    @Test
    void testProcessSignupForm_WithExistingEmail() throws Exception {
        User user = new User();
        user.setEmail("existing@example.com");
        BindingResult bindingResult = mock(BindingResult.class);
        ModelMap modelMap = new ModelMap();
        Model model = mock(Model.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(bindingResult.hasErrors()).thenReturn(false);

        when(userService.checkEmailExist(user.getEmail())).thenReturn(true);

        String viewName = signupController.processSignupForm(user, bindingResult, modelMap, model, request);

        verify(userService, never()).saveUser(user);

        verify(bindingResult, times(1))
            .rejectValue("email", "error.user", "An account already exists for this email.");

        ModelAndView modelAndView = (ModelAndView) modelMap.get("modelAndView");
        assertNotNull(modelAndView);
        assertEquals("sign-up", modelAndView.getViewName());
    }
}
