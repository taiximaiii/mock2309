package com.vti.repository;

import com.vti.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAddressRepository extends JpaRepository<Address,Integer> {

    Address findByUser_IdAndAddress(Integer userId,String address);


}
