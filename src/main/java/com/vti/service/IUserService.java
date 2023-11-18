package com.vti.service;

import com.vti.dto.UserDto;
import com.vti.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    User saveUser(User user, MultipartFile imageFile);
    List<UserDto> getListUser();
    Optional<User> findByUsername(String username);
    Optional<User> getUserById(Integer userId);
    Optional<User> updateUser(Integer userId, User updatedUser, MultipartFile imageFile);
    void deleteUser(Integer userId);
}
