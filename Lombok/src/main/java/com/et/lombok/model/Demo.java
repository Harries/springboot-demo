package com.et.lombok.model;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Demo {
	@NonNull
    private String name;
	@NonNull
    private int age;
}
