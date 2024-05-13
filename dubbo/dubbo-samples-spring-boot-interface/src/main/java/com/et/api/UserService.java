package com.et.api;

import com.et.api.entity.User;

public interface UserService {
    String getUserInfo();

    User getUserById(String userId);
}
