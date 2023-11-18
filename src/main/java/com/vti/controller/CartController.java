package com.vti.controller;

import com.vti.dto.CartDetail;
import com.vti.security.UserPrincipal;
import com.vti.service.ICartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin("*")
public class CartController {
    @Autowired
    private ICartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<String> addProductToCart(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam("productId") Integer productId,
            @RequestParam("sizeId") Integer sizeId,
            @RequestParam("quantity") Integer quantity
    ){
        cartService.addProductToCart(userPrincipal.getId()
                , productId, sizeId, quantity);
        return ResponseEntity.ok("Add product to cart successfully");
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<String> removeFromProductToCart(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestParam("productId") Integer productId,
            @RequestParam("sizeId") Integer sizeId
    ) {
        cartService.removeProductFromCart(userPrincipal.getId(), productId, sizeId);
        return ResponseEntity.ok("Remove product from cart successfully");
    }

    @GetMapping("/getCart")
    public ResponseEntity<List<CartDetail>> getCartDetail(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return new ResponseEntity<>(cartService.getCartDetail(userPrincipal.getId()), HttpStatus.OK);
    }
}

