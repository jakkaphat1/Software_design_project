package com.example.demo.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

@Service  // เพิ่ม @Service เพื่อให้ Spring Boot รู้จักคลาสนี้
public class UserServiceImpl implements UserService {
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
    @Autowired  // ใช้ @Autowired เพื่อ inject UserRepository
    private UserRepository userRepository;


    @Override
    public User save(UserDto userDto) {
        String role = userDto.getRole(); // The role is now selected from the form
        User user = new User(userDto.getEmail(), 
                             passwordEncoder.encode(userDto.getPassword()), 
                             role,  // The role selected by the user
                             userDto.getFullname());
        return userRepository.save(user);
    }

    
}
