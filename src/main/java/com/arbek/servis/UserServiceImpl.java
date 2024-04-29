package com.arbek.servis;

import com.arbek.auth.entities.LocalUser;
import com.arbek.auth.repository.UserRepository;
import com.arbek.dto.UserDto;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto getUser(String username) {
        LocalUser user = userRepository.findByUsername(String.valueOf(username))
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        UserDto response = new UserDto(
                user.getUserId(),
                user.getUsername(),
                user.getPassword(),
                user.getFirstName(),
                user.getLastName()
        );

        return response;
    }
}
