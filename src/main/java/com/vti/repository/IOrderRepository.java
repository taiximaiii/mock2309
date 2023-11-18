package com.vti.repository;

import com.vti.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IOrderRepository extends JpaRepository<Order,Integer> {
    List<Order> findAllByUserId(Integer userId);
}
