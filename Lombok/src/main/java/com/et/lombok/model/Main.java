package com.et.lombok.model;

import com.et.lombok.model.Demo4;

public class Main {

    public static void main(String[] args) {
        Demo4 demo = Demo4.builder().name("zss").age(20).build();
        System.out.println(demo);
    }
}
