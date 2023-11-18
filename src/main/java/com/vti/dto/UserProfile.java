package com.vti.dto;

import com.vti.entity.Address;
import com.vti.entity.Payment;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class UserProfile {
    private String username;
    private String email;
    private String phoneNumber;
    private String image;
    private String role;
    private List<String> addresses;
    private List<String> paymentMethods;

    public UserProfile(String username, String email, String phoneNumber, String image, String role, List<Address> addresses,List<Payment> payments) {
        this.username = username;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.role = role;
        this.addresses = addresses.stream()
                .map(Address::getAddress)
                .collect(Collectors.toList());
        this.paymentMethods = payments.stream()
                .map(Payment::getType)
                .collect(Collectors.toList());
    }
}
