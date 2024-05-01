package com.arbek.controllers;

import com.arbek.dto.UpdateUserRequest;
import com.arbek.dto.UserDto;
import com.arbek.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users/")
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUserHandler(@RequestHeader("Authorization") String authorizationHeader) {
        // Извлекаем токен из заголовка авторизации
        String token = authorizationHeader.replace("Bearer ", "");

        // Получаем текущего пользователя по токену
        UserDto currentUser = userService.getCurrentUser(token);

        // Возвращаем информацию о текущем пользователе
        return ResponseEntity.ok(currentUser);
    }

    @PutMapping("/me")
    public ResponseEntity<UserDto> updateUserHandler(@RequestHeader("Authorization") String authorizationHeader,
                                                     @RequestBody UpdateUserRequest updateUserRequest) {
        // Извлекаем токен из заголовка авторизации
        String token = authorizationHeader.replace("Bearer ", "");

        // Обновляем данные пользователя
        UserDto updatedUser = userService.updateUser(token, updateUserRequest);

        // Возвращаем обновленного пользователя
        return ResponseEntity.ok(updatedUser);
    }
}
