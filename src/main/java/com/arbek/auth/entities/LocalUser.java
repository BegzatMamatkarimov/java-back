package com.arbek.auth.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class LocalUser implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer userId;

  @NotBlank(message = "The username field can't be blank!")
  @Column(nullable = false, unique = true)
  private String username;

  @NotBlank(message = "The password field can't be blank!")
  @Column(nullable = false)
  @Size(min = 8, message = "The password must have at least 8 characters")
  private String password;

  @OneToOne(mappedBy = "localUser")
  private RefreshToken refreshToken;

  private String firstName;

  private String lastName;

  @Enumerated(EnumType.STRING)
  private UserRole userRole;

  private Boolean isEnabled = true;

  private Boolean isAccountNonExpired = true;

  private Boolean isAccountNonLocked = true;

  private Boolean isCredentialsNonExpired = true;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isAccountNonExpired;
  }

  @Override
  public boolean isAccountNonLocked() {
    return isAccountNonLocked;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return isCredentialsNonExpired;
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
