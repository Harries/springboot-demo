package com.et.security.service;

import com.et.security.entity.User;
import com.et.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
 
    @Autowired
    UserMapper userMapper;
 
    /**
     * 定义UserService实现UserDetailsService接口，
     * 并实现该接口中的loadUserByUsername方法，该方法的参数就是用户登录时输入的用户名，通过用户名去数据库中查找用户，
     * 如果没有查找到用户，就抛出一个账户不存在的异常，
     * 如果查找到了用户，就继续查找该用户所具有的角色信息，
     * 并将获取到的user对象返回，
     * 再由系统提供的DaoAuthenticationProvider类去比对密码是否正确。
     * <p>
     * loadUserByUsername方法将在用户登录时自动调用。
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("账户不存在!");
        }
       // user.setRoles(userMapper.getUserRolesByUid(user.getId()));
        return user;
    }
}