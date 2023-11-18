package com.vti.service;

import com.vti.dto.CartDetail;
import com.vti.entity.*;
import com.vti.repository.ICartRepository;
import com.vti.repository.IProductRepository;
import com.vti.repository.ISizeRepository;
import com.vti.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CartService implements ICartService{
    @Autowired
    private ICartRepository cartRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IProductRepository productRepository;
    @Autowired
    private ISizeRepository sizeRepository;
    @Override
    public void addProductToCart(Integer userId, Integer productId, Integer sizeId, int quantity) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }
        Cart cart = user.getCart();
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            user.setCart(cart);
        }
        Product product = productRepository.findById(productId).orElse(null);
        Size size = sizeRepository.findById(sizeId).orElse(null);

        if (product == null || size == null) {
            return;
        }
        for (CartProduct cartProduct : cart.getCartProducts()) {
            if (cartProduct.getProduct().equals(product) && cartProduct.getSize().equals(size)) {
                cartProduct.setQuantity(cartProduct.getQuantity() + quantity);
                cartRepository.save(cart);
                return;
            }
        }
        CartProduct newCartProduct = new CartProduct(cart, product, size, quantity);
        cart.getCartProducts().add(newCartProduct);
        cartRepository.save(cart);
    }
    @Override
    public void removeProductFromCart(Integer userId, Integer productId, Integer sizeId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return;
        }
        Cart cart = user.getCart();
        if (cart == null) {
            return;
        }

        Product product = productRepository.findById(productId).orElse(null);
        Size size = sizeRepository.findById(sizeId).orElse(null);

        if (product == null || size == null) {
            return;
        }
        cart.getCartProducts().removeIf(cartProduct -> cartProduct.getProduct().equals(product) && cartProduct.getSize().equals(size));

        cartRepository.save(cart);
    }

    @Override
    public List<CartDetail> getCartDetail(Integer userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return Collections.emptyList();
        }

        Cart cart = user.getCart();
        if (cart == null) {
            return Collections.emptyList();
        }

        List<CartDetail> cartDetails = new ArrayList<>();

        for (CartProduct cartProduct : cart.getCartProducts()) {
            Product product = cartProduct.getProduct();
            Size size = cartProduct.getSize();
            int quantity = cartProduct.getQuantity();
            String productName = product.getTitle();
            String productSize = String.valueOf(size.getType());
            double price = product.getPrice();
            String productImage = product.getImage();
            double productTotal = price * quantity;
            CartDetail cartDetail = new CartDetail(productName, productSize, quantity, productTotal, productImage);
            cartDetails.add(cartDetail);
        }
        return cartDetails;
    }

}
