package com.et.mapstruct.mapper;

import com.et.mapstruct.entity.Car;
import com.et.mapstruct.entity.CarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * CarMapping
 *
 * @author Felordcn
 * @since 14 :02 2019/10/12
 */

@Mapper(componentModel = "spring",imports = {LocalDateTime.class, Date.class, ZoneId.class})//交给spring管理
public interface CarMapping {
    
    /**
     * 用来调用实例 实际开发中可使用注入Spring  不写
     */
    CarMapping CAR_MAPPING = Mappers.getMapper(CarMapping.class);

    /**
     *  源类型 目标类型 成员变量相同类型 相同变量名 不用写{@link org.mapstruct.Mapping}来映射
     *
     * @param car the car
     * @return the car dto
     */
    @Mapping(target = "type", source = "carType.type")
    @Mapping(target = "seatCount", source = "numberOfSeats")
    CarDTO carToCarDTO(Car car);
}