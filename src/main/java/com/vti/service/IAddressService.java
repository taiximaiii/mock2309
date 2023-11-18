package com.vti.service;

import com.vti.entity.Address;

import java.util.List;

public interface IAddressService {
    void addAddress(Integer userId,Address address);
    boolean doesAddressExistForUser(Integer userId, Address newAddress);
    List<Address> getAllAddressByUserId(Integer userId);
    void updateAddress(Integer userId, Integer addressId, Address updatedAddress);
}
