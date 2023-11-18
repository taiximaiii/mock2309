package com.vti.service;

import com.vti.entity.Address;
import com.vti.entity.User;
import com.vti.repository.IAddressRepository;
import com.vti.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AddressService implements IAddressService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IAddressRepository addressRepository;


    @Override
    public void addAddress(Integer userId, Address newAddress) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }
        if (!doesAddressExistForUser(userId, newAddress)) {
            newAddress.setUser(user);
            user.getAddresses().add(newAddress);
            userRepository.save(user);
        }
    }


    @Override
    public boolean doesAddressExistForUser(Integer userId, Address newAddress) {
        User user = userRepository.findById(userId).orElse(null);

        if (user == null) {
            return false;
        }

        for (Address existingAddress : user.getAddresses()) {
            if (existingAddress.getAddress().equals(newAddress.getAddress())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public List<Address> getAllAddressByUserId(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            return user.getAddresses();
        }

        return null;
    }

    @Override
    public void updateAddress(Integer userId, Integer addressId, Address updatedAddress) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            List<Address> addresses = user.getAddresses();

            if (addresses != null) {
                Optional<Address> addressToUpdate = addresses.stream()
                        .filter(address -> address.getId().equals(addressId))
                        .findFirst();

                addressToUpdate.ifPresent(existingAddress -> {
                    existingAddress.setAddress(updatedAddress.getAddress());
                    addressRepository.save(existingAddress);
                });
            }

            userRepository.save(user);
        }
    }

}

