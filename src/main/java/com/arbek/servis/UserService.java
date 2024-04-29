package com.arbek.servis;

import com.arbek.dto.UserDto;

public interface UserService {

    UserDto getUser(String username);
}
