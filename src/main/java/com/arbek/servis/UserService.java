package com.arbek.servis;

import com.arbek.dto.UpdateUserRequest;
import com.arbek.dto.UserDto;

public interface UserService {

    UserDto getCurrentUser(String token);
    UpdateUserRequest updateCurrentUser(String token, UpdateUserRequest updateUserRequest);


}
