package com.sda.java3.ecommerce.domains;

import com.sda.java3.ecommerce.utils.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ec_user")
public class User {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false)
    @Type(type = "org.hibernate.type.UUIDCharType")
    protected UUID id;
    @Column(name="first_name")
    @NotEmpty(message = "Please provide a first name")

    protected String firstName;
    @Column(name="last_name")
    @NotEmpty(message = "Please provide a last name")

    protected String lastName;
    @Column(name="email")
    @Email(message = "Please provide a first name")

    protected String email;
    @Column(name="passwo")
    @NotEmpty(message = "Please provide a password")
    protected String password;
    @Column(name="vitri")

    protected int role;
    @Column(name="created_at")

    private LocalDateTime created_at;
    @OneToMany(mappedBy = "user")
    protected List<Cart> items;
}
