package com.et.webflux;


import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public String getOneResult(String methodName) {

        return String.format("%s invoker success", methodName);
    }

    @Override
    public List<String> getMultiResult(String methodName) {
        List<String> list = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            list.add(String.format("%s invoker success， %d ", methodName, i + 1));
        }
        return list;
    }

    @Override
    public User addUser(User user) {
        user.setId(1L);
        return user;
    }

    @Override
    public List<User> findAllUser() {

        List<User> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int no = i + 1;
            list.add(new User((long) no, "USER_" + no, "PWD_" + no, 18 + no));
        }
        return list;
    }

    @Override
    public User findUserById(Long id) {

        return new User(id, "USER_" + id, "PWD_" + id, 18);
    }
}
