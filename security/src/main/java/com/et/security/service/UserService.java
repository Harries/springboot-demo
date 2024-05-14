package com.et.security.service;

import com.et.security.entity.User;
import com.et.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService implements UserDetailsService {
 
    @Resource
    UserMapper userMapper;

	/**
	 * Define UserService to implement the UserDetailsService interface,
	 * And implement the loadUserByUsername method in this interface. The parameter of this method is the user name entered when the user logs in. Use the user name to search for the user in the database.
	 * If the user is not found, an exception that the account does not exist will be thrown.
	 * If the user is found, continue to search for the role information of the user.
	 * And return the obtained user object,
	 * The DaoAuthenticationProvider class provided by the system will then check whether the password is correct.
	 * <p>
	 * The loadUserByUsername method will be automatically called when the user logs in.
	 */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.loadUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("the account is not exist!");
        }
       // user.setRoles(userMapper.getUserRolesByUid(user.getId()));
        return user;
    }
}