package com.et.mapstruct.entity;

import lombok.Data;

@Data
public class Car {
    private String make;
    private int numberOfSeats;
    private CarType carType;

}