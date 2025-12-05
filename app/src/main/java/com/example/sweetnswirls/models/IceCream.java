package com.example.sweetnswirls.models;

public class IceCream {

        private String name;
        private String description;
        private int price;
        private int image;

        public IceCream(String name, String description, int price, int image) {
            this.name = name;
            this.description = description;
            this.price = price;
            this.image = image;
        }

        public String getName() { return name; }
        public String getDescription() { return description; }
        public int getPrice() { return price; }
        public int getImage() { return image; }
    }

