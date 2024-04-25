package com.arbek.dto;

import com.arbek.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
  private Integer userId;

  @NotBlank(message = "User's name couldn't be empty!")
  private String username;

  @NotBlank(message = "User's password couldn't be empty!")
  private String password;

  private String firstName;

  private String lastName;

  @NotBlank(message = "User's type couldn't be empty!")
  private UserType userType;
}
