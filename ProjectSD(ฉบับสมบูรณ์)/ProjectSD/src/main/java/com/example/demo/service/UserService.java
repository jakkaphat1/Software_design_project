package com.example.demo.service;

import com.example.demo.User.UserDto;
import com.example.demo.model.User;

public interface UserService {
    User save(UserDto userDto);
}
