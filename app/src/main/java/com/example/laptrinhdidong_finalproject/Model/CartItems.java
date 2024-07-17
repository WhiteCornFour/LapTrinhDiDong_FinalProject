package com.example.laptrinhdidong_finalproject.Model;

public class CartItems {
    String cartId;
    String productId;
    String productSize;
    int productQuantity;
    double cartUnitPrice;

    public CartItems(String cartId, String productId, String productSize, int productQuantity, double cartUnitPrice) {
        this.cartId = cartId;
        this.productId = productId;
        this.productSize = productSize;
        this.productQuantity = productQuantity;
        this.cartUnitPrice = cartUnitPrice;
    }


    public double getCartUnitPrice() {
        return cartUnitPrice;
    }

    public void setCartUnitPrice(double cartUnitPrice) {
        this.cartUnitPrice = cartUnitPrice;
    }

    public String getCartId() {
        return cartId;
    }

    public void setCartId(String cartId) {
        this.cartId = cartId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public CartItems() {
    }
}
