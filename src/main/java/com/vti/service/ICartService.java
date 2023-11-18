package com.vti.service;

import com.vti.dto.CartDetail;

import java.util.List;

public interface ICartService {
    void addProductToCart(Integer userId, Integer productId, Integer sizeId, int quantity);
    void removeProductFromCart(Integer userId, Integer productId, Integer sizeId);
    List<CartDetail> getCartDetail(Integer userId);
}
