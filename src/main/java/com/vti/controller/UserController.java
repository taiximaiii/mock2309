package com.vti.controller;

import com.vti.dto.UserProfile;
import com.vti.entity.Address;
import com.vti.entity.User;
import com.vti.security.UserPrincipal;
import com.vti.service.IAddressService;
import com.vti.service.IPaymentService;
import com.vti.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private IAddressService addressService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IPaymentService paymentService;

    @PostMapping("/addAddress")
    public ResponseEntity<?> addAddress(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                        @RequestBody Address address) {

        if (addressService.doesAddressExistForUser(userPrincipal.getId(), address)) {
            return new ResponseEntity<>("Address already exists for the user", HttpStatus.CONFLICT);
        }

        addressService.addAddress(userPrincipal.getId(), address);
        return new ResponseEntity<>("Address added successfully", HttpStatus.OK);
    }
    @GetMapping("/allAddress")
    public ResponseEntity<?> getAddress(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(addressService.getAllAddressByUserId(userPrincipal.getId()), HttpStatus.OK);
    }
    @GetMapping("/profile")
    public ResponseEntity<?> viewProfile(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            User user = userService.getUserById(userPrincipal.getId()).orElse(null);

            if (user != null) {
                UserProfile userProfileDto = new UserProfile(
                        user.getUsername(),
                        user.getEmail(),
                        user.getPhoneNumber(),
                        user.getImage(),
                        user.getRole().name(),
                        user.getAddresses(),
                        user.getPayments()
                );

                return new ResponseEntity<>(userProfileDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while fetching user profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/addPayment")
    public ResponseEntity<String> addPayment(@AuthenticationPrincipal UserPrincipal userPrincipal){
        paymentService.addBankPaymentToUser(userPrincipal.getId());
        return ResponseEntity.ok("Add new payment method successfully.");
    }

    @PutMapping("/updateAddress/{addressId}")
    public ResponseEntity<?> updateAddress(@AuthenticationPrincipal UserPrincipal userPrincipal,@PathVariable Integer addressId,@RequestBody Address updateAddress){
        addressService.updateAddress(userPrincipal.getId(), addressId,updateAddress);
        return ResponseEntity.ok("Update address successfully.");
    }
    @GetMapping("/listUser")
    public ResponseEntity<?> getAllUsers(){
        return new ResponseEntity<>(userService.getListUser(), HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phoneNumber,
            @RequestParam(required = false) String password,
            @RequestParam(required = false) MultipartFile file
    ) {
        User updatedUser = new User();
        updatedUser.setUsername(username);
        updatedUser.setEmail(email);
        updatedUser.setPhoneNumber(phoneNumber);
        updatedUser.setPassword(password);
        userService.updateUser(userPrincipal.getId(), updatedUser, file);
        return ResponseEntity.ok("Update user successfully");
    }
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("Delete user successfully");
    }
    @DeleteMapping("/deleteAccount")
    public ResponseEntity<?> deleteAccount(@AuthenticationPrincipal UserPrincipal userPrincipal){
        userService.deleteUser(userPrincipal.getId());
        return ResponseEntity.ok("Delete account successfully");
    }
}
