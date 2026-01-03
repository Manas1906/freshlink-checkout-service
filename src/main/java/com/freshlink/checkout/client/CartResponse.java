package com.freshlink.checkout.client;

import java.util.List;

public class CartResponse {
    public Long id;
    public String customerEmail;
    public List<CartItemResponse> items;
}
