package com.example.sweetnswirls.models;

public class CartItem {
        private IceCream iceCream;
        private int quantity;

        public CartItem(IceCream iceCream, int quantity) {
            this.iceCream = iceCream;
            this.quantity = quantity;
        }

        public IceCream getIceCream() { return iceCream; }
        public int getQuantity() { return quantity; }
        public void setQuantity(int quantity) { this.quantity = quantity; }
    }


