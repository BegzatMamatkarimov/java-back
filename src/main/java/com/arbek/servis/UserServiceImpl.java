package com.arbek.servis;

import com.arbek.auth.entities.LocalUser;
import com.arbek.auth.repository.UserRepository;
import com.arbek.dto.UpdateUserRequest;
import com.arbek.dto.UserDto;
import com.arbek.auth.services.JwtService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public UpdateUserRequest updateCurrentUser(String token, UpdateUserRequest updateUserRequest) {
        // Извлекаем имя пользователя из токена
        String username = jwtService.extractUsername(token);

        // Находим пользователя в базе данных
        LocalUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

//        UpdateUserRequest updateUser = new UpdateUserRequest(
//                user.getUsername(),
//                user.getPassword()
//        );

        // Обновляем логин пользователя, если предоставлен новый
        if (updateUserRequest.getNewUsername() != null && !updateUserRequest.getNewUsername().isEmpty()) {
            System.out.println("Updating username from: " + user.getUsername() + " to: " + updateUserRequest.getNewUsername());
            user.setUsername(updateUserRequest.getNewUsername());
        }

        // Обновляем пароль пользователя, если предоставлен новый
        if (updateUserRequest.getNewPassword() != null && !updateUserRequest.getNewPassword().isEmpty()) {
            System.out.println("Updating password for user: " + user.getUsername());
            // Кодируем новый пароль перед сохранением
            String encodedPassword = passwordEncoder.encode(updateUserRequest.getNewPassword());
            user.setPassword(encodedPassword);
        }

        // Сохраняем обновленного пользователя в базе данных
        LocalUser save = userRepository.save(user);
        return new UpdateUserRequest(
                save.getUsername(),
                save.getPassword()
        );
    }

    @Override
    public UserDto getCurrentUser(String token) {
        String username = jwtService.extractUsername(token);
        LocalUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Проверяем валидность токена
        if (!jwtService.isTokenValid(token, user)) {
            throw new RuntimeException("Invalid token");
        }

        return mapToDto(user);
    }

    private UserDto mapToDto(LocalUser user) {
        return new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getUserType(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
