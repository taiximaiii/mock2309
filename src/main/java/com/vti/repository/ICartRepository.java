package com.vti.repository;

import com.vti.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICartRepository extends JpaRepository<Cart,Integer> {
    Cart findCartByUserId(Integer userId);
}
