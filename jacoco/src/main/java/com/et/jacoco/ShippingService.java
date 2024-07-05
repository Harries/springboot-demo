package com.et.jacoco;

public class ShippingService {
    public int calculateShippingFee(int weight) {
        if (weight < 0) {
            throw new IllegalStateException("Please provide correct weight");
        }
        if (weight <= 2) {
            return 5;
        } else if (weight <= 5) {
            return 10;
        }
        return 15;
    }
}
