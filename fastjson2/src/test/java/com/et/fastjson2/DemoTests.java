package com.et.fastjson2;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.et.fastjson2.entity.UserDTO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoTests {
    private Logger log = LoggerFactory.getLogger(getClass());


    @Before
    public void before()  {
        log.info("init some data");
    }
    @After
    public void after(){
        log.info("clean some data");
    }
    @Test
    public void parseArray()  {
        String json="[{\"create_time\":\"2024-07-03 09:03:26.968\",\"money\":-40090.0700}]";
        System.out.println(json);
        List<UserDTO> list1 = JSON.parseArray(json, UserDTO.class,JSONReader.Feature.SupportSmartMatch);
        System.out.println();
    }
    @Test
    public void toJSONString() throws ParseException {
        UserDTO  user =  new UserDTO();
        BigDecimal money =new BigDecimal(-40090.07d);
        money = money.setScale(4, RoundingMode.HALF_UP);
        user.setMoney(money);

        String createtime ="2024-07-03 09:03:26.968";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        Date date = format.parse(createtime);
        user.setCreateTime(date);

        List<UserDTO> list = new ArrayList<>();
        list.add(user);
        String json=JSON.toJSONString(list);
        //String json=JSON.toJSONString(list,  JSONWriter.Feature.WriteBigDecimalAsPlain);
        System.out.println(json);
    }

}

