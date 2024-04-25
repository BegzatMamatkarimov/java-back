package com.arbek.entities;

import com.arbek.enums.UserType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LocalUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @Column(nullable = false, unique = true)
  @NotBlank(message = "User's name couldn't be empty!")
  private String username;

  @Column(nullable = false)
  @NotBlank(message = "User's password couldn't be empty!")
  private String password;

  @Column
  private String firstName;

  @Column
  private String lastName;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  @NotNull(message = "User's type couldn't be empty!")
  private UserType userType;
}
