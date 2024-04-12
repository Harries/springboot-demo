package com.et.webflux;

import java.util.List;

public interface DemoService {


    String getOneResult(String methodName);


    List<String> getMultiResult(String methodName);

    User addUser(User user);

    List<User> findAllUser();

    User findUserById(Long id);

}
