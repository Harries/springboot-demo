package com.et.mapstruct;

import com.alibaba.fastjson2.JSONObject;
import com.et.mapstruct.entity.Car;
import com.et.mapstruct.entity.CarDTO;
import com.et.mapstruct.entity.CarType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author simon
 * @date 2019/12/12 16:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CarMappingTest {

    private Car car;
    private CarDTO carDTO;

    @Autowired
    private com.et.mapstruct.mapper.CarMapping CarMapping;

    @Before
    public void setUp() throws Exception {
        car = new Car();
        car.setMake("make");
        CarType type =  new CarType();
        type.setType("type");
        car.setCarType(type);
        car.setNumberOfSeats(1);
    }

    @Test
    public void testcarToCarDTO() {
        carDTO = CarMapping.carToCarDTO(car);
        System.out.println(JSONObject.toJSONString(carDTO));
    }


}