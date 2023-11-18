package com.vti.service;
import com.vti.dto.UserDto;
import com.vti.entity.Payment;
import com.vti.entity.Role;
import com.vti.repository.IPaymentRepository;
import com.vti.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.vti.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IPaymentRepository paymentRepository;

    @Override
    public User saveUser(User user, MultipartFile imageFile) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user.setRole(Role.USER);

        if (imageFile != null && !imageFile.isEmpty()) {
            String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
            String filePath = "uploads/" + fileName;
            user.setImage(fileName);
            try {
                Files.copy(imageFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            user.setImage(null);
        }

        User savedUser = userRepository.save(user);

        List<Payment> userPayments = savedUser.getPayments();
        if (userPayments == null) {
            userPayments = new ArrayList<>();
            savedUser.setPayments(userPayments);
        }
        Payment defaultPayment = paymentRepository.findByType("Tiền mặt");
        if (defaultPayment == null) {
            defaultPayment = new Payment();
            defaultPayment.setType("Tiền mặt");
            defaultPayment.getUsers().add(savedUser);
            userPayments.add(defaultPayment);
            paymentRepository.save(defaultPayment);
        } else {
            userPayments.add(defaultPayment);
            defaultPayment.getUsers().add(savedUser);
        }
        userRepository.save(savedUser);

        return savedUser;
    }

    @Override
    public Optional<User> findByUsername(String username) {

        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<UserDto> getListUser() {
        List<User> users = userRepository.findByRole(Role.USER);

        List<UserDto> userDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return userDtos;
    }
    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setImage(user.getImage());
        return userDto;
    }
    @Override
    public Optional<User> updateUser(Integer userId, User updatedUser, MultipartFile imageFile) {
        return userRepository.findById(userId).map(existingUser -> {
            existingUser.setUsername(updatedUser.getUsername() != null ? updatedUser.getUsername() : existingUser.getUsername());
            existingUser.setEmail(updatedUser.getEmail() != null ? updatedUser.getEmail() : existingUser.getEmail());
            existingUser.setPhoneNumber(updatedUser.getPhoneNumber() != null ? updatedUser.getPhoneNumber() : existingUser.getPhoneNumber());

            if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            if (imageFile != null && !imageFile.isEmpty()) {
                String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
                String filePath = "uploads/" + fileName;
                existingUser.setImage(fileName);
                try {
                    Files.copy(imageFile.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return userRepository.save(existingUser);
        });
    }


    @Override
    @Transactional
    public void deleteUser(Integer userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getPayments() != null && !user.getPayments().isEmpty()) {
                for (Payment payment : user.getPayments()) {
                    payment.getUsers().remove(user);
                }
                user.getPayments().clear();
            }

            userRepository.deleteById(userId);
        }
    }

}
