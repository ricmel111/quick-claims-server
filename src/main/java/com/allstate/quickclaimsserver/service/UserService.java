package com.allstate.quickclaimsserver.service;

import com.allstate.quickclaimsserver.domain.User;
import com.allstate.quickclaimsserver.domain.UserDTO;

import java.util.List;

public interface UserService {

    public void save(User user);

    public User findUser(String username);

    public List<UserDTO> getAllUsers();
}
