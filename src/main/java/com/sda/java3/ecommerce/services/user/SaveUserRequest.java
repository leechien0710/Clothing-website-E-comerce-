package com.sda.java3.ecommerce.services.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class SaveUserRequest {
	@NotEmpty(message = "Please provide a first name")
    protected String firstName;
    @NotEmpty(message = "Please provide a last name")
    protected String lastName;
    @Email(message = "Please provide a first name")
    protected String email;
    @NotEmpty(message = "Please provide a password")
    protected String password;
    protected int role=0;
    private LocalDateTime created_at=LocalDateTime.now();
}
