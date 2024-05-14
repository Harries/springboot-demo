package com.et.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements UserDetails {
 
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
    private Boolean locked;
    private List<Role> roles;

	/*
	 * The user entity class needs to implement the UserDetails interface and implement the 7 methods in this interface
	 * Users set the return values ​​of these 7 methods according to actual conditions. Because by default, developers are not required to compare passwords, roles and other information themselves, developers only need to provide relevant information.
	 * For example, if the password returned by the getPassword() method does not match the login password entered by the user, a BadCredentialsException will be automatically thrown.
	 * The isAccountNonExpired() method returns false and will automatically throw an AccountExpiredException exception.
	 * Therefore, developers only need to return the corresponding configuration here according to the data in the database. In this case, because there are only enabled and locked fields in the database, both the account not expired and password not expired methods return true.
	 */


	/**
	 * The getAuthorities() method is used to obtain the role information of the current user.
	 * In this case, the roles owned by the user are stored in the roles attribute, so this method directly traverses the roles attribute, then constructs the SimpleGrantedAuthority collection and returns it.
	 */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return authorities;
    }
 
    @Override
    public String getPassword() {
        return password;
    }
 
    @Override
    public String getUsername() {
        return username;
    }
 
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
 
    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }
 
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
 
    @Override
    public boolean isEnabled() {
        return enabled;
    }
 
    public Integer getId() {
        return id;
    }
 
    public void setId(Integer id) {
        this.id = id;
    }
 
    public void setUsername(String username) {
        this.username = username;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
 
//    public Boolean getEnabled() {
//        return enabled;
//    }
 
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
 
    public Boolean getLocked() {
        return locked;
    }
 
    public void setLocked(Boolean locked) {
        this.locked = locked;
    }
 
    public List<Role> getRoles() {
        return roles;
    }
 
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
}